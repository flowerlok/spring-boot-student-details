package com.student.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.app.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>  {
	
}
