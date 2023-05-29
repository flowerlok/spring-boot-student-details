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
import com.student.app.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

	Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentRepository studentRepository;

	@EntryExitLogger
	@PreAuthorize("hasAnyRole('PRINCIPAL','TEACHER')")
	@GetMapping("/listAllStudents")
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	
	@PreAuthorize("hasAnyRole('PRINCIPAL','TEACHER')")
	@GetMapping("/student/studentId/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer studentId)
			throws ResourceNotFoundException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));
		logger.debug("getStudent details for ID: "+studentId); 
		return ResponseEntity.ok().body(student);
	}

	@PreAuthorize("hasRole('PRINCIPAL')")
	@PostMapping("/create/student")	
	@EntryExitLogger
	public Student createStudent(@RequestBody Student student) {
		
		Student studentCreated = null;
		try {
			studentCreated = studentRepository.save(student);
		} catch (Exception e) {
			logger.error("Something went wrong during save student", e);
			e.printStackTrace();
		}
		logger.debug("New student created: "+student);
		return studentCreated;
	}
	
	@PreAuthorize("hasRole('PRINCIPAL')")
	@PutMapping("/update/student/studentId/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Integer studentId,
        @RequestBody Student studentDetails) throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));

        student.setName(studentDetails.getName());
        student.setPlace(studentDetails.getPlace());
        final Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }
	
	@PreAuthorize("hasRole('PRINCIPAL')")
	@DeleteMapping("/delete/student/studentId/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Integer studentId)
    throws ResourceNotFoundException {
		Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + studentId));

		studentRepository.delete(student);
        Map < String, Boolean > response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
