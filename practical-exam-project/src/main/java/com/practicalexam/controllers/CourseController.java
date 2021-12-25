package com.practicalexam.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practicalexam.dto.CourseDTO;
import com.practicalexam.dto.GlobalPageResponseFormat;
import com.practicalexam.dto.GlobalResponseFormat;
import com.practicalexam.exception.InvalidInputException;
import com.practicalexam.services.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping("/addCourse")
	public GlobalResponseFormat<CourseDTO> addCourse(@Valid @RequestBody CourseDTO courseDTO) {
		GlobalResponseFormat<CourseDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.courseService.addCourse(courseDTO));
		return response;
	}
	
	@GetMapping("/retrieveCourse/{id}")
	public GlobalResponseFormat<CourseDTO> getCourse(@PathVariable("id") Long id) {
		GlobalResponseFormat<CourseDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.courseService.getCourse(id));
		return response;
	}

	@PutMapping("/updateCourse")
	public GlobalResponseFormat<CourseDTO> updateCourse(@Valid @RequestBody CourseDTO courseDTO) {
		GlobalResponseFormat<CourseDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.courseService.updateCourse(courseDTO));
		return response;
	}

	@GetMapping("/search")
	public GlobalPageResponseFormat<List<CourseDTO>> listAllCourseWithPagination(
			@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "courseId,asc") String[] sort)
			throws InvalidInputException {
		return this.courseService.getCourseByPagination(title, page, size, sort);

	}
}
