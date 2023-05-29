package com.student.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.app.model.Mark;
import com.student.app.model.Subject;
import com.student.app.repository.MarkRepository;
import com.student.app.repository.SubjectRepository;

@RestController
@RequestMapping("/api/v1")
public class MarkController {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private MarkRepository markRepository;
	
	Logger logger = LoggerFactory.getLogger(MarkController.class);
	
	@PreAuthorize("hasRole('TEACHER')")
	@PostMapping("/create/grade")
	public Mark createGrade(@RequestBody Mark mark) {
		return markRepository.save(mark);
	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@PutMapping("subjectId/{subjectId}/grade/gradeId/{gradeId}")
	public Subject assignMarkToSubject(
			@PathVariable Integer subjectId,
			@PathVariable Integer gradeId) {
		Subject subject = subjectRepository.findById(subjectId).get();
		Mark grade = markRepository.findById(gradeId).get();
		subject.assignMarkToSubject(grade);
		markRepository.save(grade);
		return subjectRepository.save(subject);
	}

}
