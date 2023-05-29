package com.student.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Student {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String place;
	
	
	@ManyToMany(mappedBy = "enrolledStudents")
	private Set<Subject> subjects = new HashSet<>();
	
	public Set<Subject> getSubjects() {
		return subjects;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Student(Integer id, String name, String place) {
		super();
		this.id = id;
		this.name = name;
		this.place = place;
	}

	public Student() {

	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", place=" + place + ", subjects=" + subjects + "]";
	}

}
