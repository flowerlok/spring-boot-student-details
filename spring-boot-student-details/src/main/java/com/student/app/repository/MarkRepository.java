package com.student.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.app.model.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer>{

}
