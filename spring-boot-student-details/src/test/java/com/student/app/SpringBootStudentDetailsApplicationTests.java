package com.student.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.student.app.model.Student;
import com.student.app.repository.StudentRepository;

@SpringBootTest
class SpringBootStudentDetailsApplicationTests {

	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateStudent() {
		Student student = new Student();
		student.setName("Shiju");
		student.setPlace("Trivandrum");
		studentRepository.save(student);
		assertNotNull(studentRepository.findById(1).get());
	}
	
	@Test
	public void testListAllStudents() {		
		assertThat(studentRepository.findAll().size()).isGreaterThan(0);
	}
}
