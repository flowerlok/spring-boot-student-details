package com.student.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Subject {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String subjectName;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
		name = "student_enrolled",
		joinColumns = @JoinColumn(name="subject_id"),
		inverseJoinColumns = @JoinColumn(name="student_id")
	)
	private Set<Student> enrolledStudents = new HashSet<>();
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mark_id",referencedColumnName = "id")
	private Mark grade; 
	
	
	public Mark getGrade() {
		return grade;
	}
	public void setGrade(Mark grade) {
		this.grade = grade;
	}
	public Set<Student> getEnrolledStudents() {
		return enrolledStudents;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public void enrollStudent(Student student) {
		enrolledStudents.add(student);
	}
	public void assignMarkToSubject(Mark grade) {
		this.grade = grade;
	}

	
}
