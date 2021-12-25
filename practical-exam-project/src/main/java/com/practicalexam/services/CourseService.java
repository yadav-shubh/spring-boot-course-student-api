package com.practicalexam.services;

import java.util.List;

import com.practicalexam.dto.CourseDTO;
import com.practicalexam.dto.GlobalPageResponseFormat;

public interface CourseService {
	public CourseDTO addCourse(CourseDTO courseDTO);

	public CourseDTO updateCourse(CourseDTO courseDTO);

	public CourseDTO getCourse(Long id);

	public GlobalPageResponseFormat<List<CourseDTO>> getCourseByPagination(String title, int page, int size,
			String[] sort);
}
