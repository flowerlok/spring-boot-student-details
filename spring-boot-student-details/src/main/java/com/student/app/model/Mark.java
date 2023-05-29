package com.student.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Mark {

	@Id
	@GeneratedValue
	private Integer id;
	private String grade;
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "grade")
	private Set<Subject> subjects = new HashSet<>();
	
	
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Set<Subject> getSubjects() {
		return subjects;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Mark() {
		
	}
	
}
