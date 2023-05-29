package com.student.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.logging.EntryExitLogger;
import com.student.app.model.Student;
import com.student.app.model.Subject;
import com.student.app.repository.StudentRepository;
import com.student.app.repository.SubjectRepository;

@RestController
@RequestMapping("/api/v1")
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	Logger logger = LoggerFactory.getLogger(SubjectController.class);
	
	@PostMapping("/create/subject")
	@EntryExitLogger
	public Subject createSubject(@RequestBody Subject subject) {
		return subjectRepository.save(subject);
	}
	
	@GetMapping("/listAllSubjects")
	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('PRINCIPAL','TEACHER')")
	@GetMapping("/subject/subjectId/{id}")
	public ResponseEntity<Subject> getSubjectById(@PathVariable(value = "id") Integer subjectId)
			throws ResourceNotFoundException {
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));
		logger.info("getSubject details for ID: "+subjectId); 
		return ResponseEntity.ok().body(subject);
	}
	
	@PreAuthorize("hasRole('PRINCIPAL')")
	@PutMapping("/update/subject/subjectId/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable(value = "id") Integer subjectId,
        @RequestBody Subject subjectDetails) throws ResourceNotFoundException {
		Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));

        subject.setSubjectName(subjectDetails.getSubjectName());
        final Subject updatedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(updatedSubject);
    }
	
	@PreAuthorize("hasRole('PRINCIPAL')")
	@DeleteMapping("/delete/subject/subjectId/{id}")
    public Map<String, Boolean> deleteSubject(@PathVariable(value = "id") Integer subjectId)
    throws ResourceNotFoundException {
		Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + subjectId));

		subjectRepository.delete(subject);
        Map < String, Boolean > response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
	@PreAuthorize("hasRole('PRINCIPAL')")
	@PutMapping("subjectId/{subjectId}/student/studentId/{studentId}")
	public Subject enrollStudentToSubject(
			@PathVariable Integer subjectId,
			@PathVariable Integer studentId) {
		Subject subject = subjectRepository.findById(subjectId).get();
		Student student = studentRepository.findById(studentId).get();
		subject.enrollStudent(student);
		return subjectRepository.save(subject);
	}
}
