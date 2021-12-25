package com.practicalexam.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practicalexam.dto.GlobalPageResponseFormat;
import com.practicalexam.dto.GlobalResponseFormat;
import com.practicalexam.dto.StudentDTO;
import com.practicalexam.exception.InvalidInputException;
import com.practicalexam.services.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping("/addStudent")
	public GlobalResponseFormat<StudentDTO> addStudent(@Valid @RequestBody StudentDTO studentDTO) {
		GlobalResponseFormat<StudentDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.studentService.addStudent(studentDTO));
		return response;
	}

	@GetMapping("/retrieveStudent/{id}")
	public GlobalResponseFormat<StudentDTO> getStudent(@Valid @PathVariable @Positive long id ) {
		GlobalResponseFormat<StudentDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.studentService.getStudent(id));
		return response;
	}

	@PutMapping("/updateStudent")
	public GlobalResponseFormat<StudentDTO> updateStudent(@Valid @RequestBody StudentDTO studentDTO) {
		GlobalResponseFormat<StudentDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.studentService.updateStudent(studentDTO));
		return response;
	}

	@GetMapping("/search")
	public GlobalPageResponseFormat<List<StudentDTO>> listAllStudentWithPagination(
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "studentId,asc") String[] sort)
			throws InvalidInputException {
		return this.studentService.getStudentByPagination(name, page, size, sort);

	}
}
