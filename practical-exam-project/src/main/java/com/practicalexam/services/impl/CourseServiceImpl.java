package com.practicalexam.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.practicalexam.dto.CourseDTO;
import com.practicalexam.dto.GlobalPageResponseFormat;
import com.practicalexam.exception.EntityNotExistException;
import com.practicalexam.exception.InvalidInputException;
import com.practicalexam.exception.RecordNotFoundException;
import com.practicalexam.model.Course;
import com.practicalexam.repository.CourseRepository;
import com.practicalexam.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CourseDTO addCourse(CourseDTO courseDTO) {
		Course course = convertDTOToEntity(courseDTO);
		try {
			course = this.courseRepository.save(course);
			return convertEntityToDTO(course);
		} catch (Exception e) {
			throw new InvalidInputException("course should not be null");
		}
	}

	@Override
	public CourseDTO getCourse(Long id) {
		try {
			if (id > 0) {
				Course course = this.courseRepository.getCourseById(id);
				return convertEntityToDTO(course);
			}
			throw new InvalidInputException("course id can not be negative or 0");

		} catch (EntityNotExistException e) {
			throw new EntityNotExistException("No Course Exists with ID : " + id);
		}
	}

	@Override
	public CourseDTO updateCourse(CourseDTO courseDTO) {
		try {
			Course course = this.courseRepository.getCourseById(courseDTO.getCourseId());
			courseDTO.setCourseId(course.getCourseId());
			course = convertDTOToEntity(courseDTO);
			return convertEntityToDTO(this.courseRepository.save(course));
		} catch (Exception e) {
			throw new EntityNotExistException("No Course Exists with ID : " + courseDTO.getCourseId());
		}
	}

	@Override
	public GlobalPageResponseFormat<List<CourseDTO>> getCourseByPagination(String title, int page, int size,
			String[] sort) {
		List<Order> orders = new ArrayList<Order>();
		if (sort[0].contains(",")) {
			// will sort more than 2 fields
			// sortOrder="field, direction"
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
//				Direction.ASC,Direction.DESC
				orders.add(new Order(Direction.valueOf(Direction.class, _sort[1].toUpperCase()), _sort[0]));
			}
		} else {
			// sort=[field, direction]
			orders.add(new Order(Direction.valueOf(Direction.class, sort[1].toUpperCase()), sort[0]));
		}
		Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
		try {
			Page<Course> pages;
			if (title == null || title.trim().isEmpty()) {
				pages = this.courseRepository.findAll(pagingSort);
			} else {
				pages = this.courseRepository.findByTitleContaining(title, pagingSort);
			}
			List<Course> listCourse = (pages.getContent().isEmpty()) ? null : pages.getContent();
			List<CourseDTO> listCourseDTO = listCourse.stream().map(this::convertEntityToDTO)
					.collect(Collectors.toList());
			GlobalPageResponseFormat<List<CourseDTO>> response = new GlobalPageResponseFormat<>();
			response.setError(false);
			response.setMessage(null);
			response.setCurrentPage(page);
			response.setTotalPages(pages.getTotalPages());
			response.setTotalRecords(pages.getTotalElements());
			response.setData(listCourseDTO);
			return response;
		} catch (NullPointerException e) {
			throw new RecordNotFoundException("No Course Exists in page: " + page);
		}
	}

	private CourseDTO convertEntityToDTO(Course course) {
		CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
		return courseDTO;
	}

	private Course convertDTOToEntity(CourseDTO courseDTO) {
		Course course = modelMapper.map(courseDTO, Course.class);
		return course;
	}

}
