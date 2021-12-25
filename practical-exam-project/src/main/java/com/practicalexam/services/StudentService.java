package com.practicalexam.services;

import java.util.List;

import com.practicalexam.dto.GlobalPageResponseFormat;
import com.practicalexam.dto.StudentDTO;

public interface StudentService {
	public StudentDTO addStudent(StudentDTO studentDTO);

	public StudentDTO updateStudent(StudentDTO studentDTO);
	
	public StudentDTO getStudent(Long studentId);

	public GlobalPageResponseFormat<List<StudentDTO>> getStudentByPagination(String name, int page, int size,
			String[] sort);
}
