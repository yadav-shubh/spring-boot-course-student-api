package com.practicalexam.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import com.practicalexam.dto.GlobalPageResponseFormat;
import com.practicalexam.dto.StudentDTO;
import com.practicalexam.exception.AlreadyExistException;
import com.practicalexam.exception.EntityNotExistException;
import com.practicalexam.exception.InvalidInputException;
import com.practicalexam.exception.RecordNotFoundException;
import com.practicalexam.model.Student;
import com.practicalexam.repository.StudentRepository;
import com.practicalexam.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StudentDTO addStudent(StudentDTO studentDTO) {
		Student student = convertDTOToEntity(studentDTO);
		try {
			student = this.studentRepository.save(student);
			return convertEntityToDTO(student);
		} catch (JpaObjectRetrievalFailureException ex) {
			throw new EntityNotExistException("No Course Exists with ID : " + studentDTO.getCourse().getCourseId());
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistException("Student with Email : " + studentDTO.getEmail() + " already exist !");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidInputException("student should not be null");
		}

	}

	@Override
	public StudentDTO getStudent(Long studentId) {
		try {
			if (studentId > 0) {
				Student student = this.studentRepository.getStudentById(studentId);
				return convertEntityToDTO(student);
			}
			throw new InvalidInputException("Student id can not be negative or 0");

		} catch (EntityNotExistException e) {
			throw new EntityNotExistException("No Student Exists with ID : " + studentId);
		}
	}

	@Override
	public StudentDTO updateStudent(StudentDTO studentDTO) {
		try {
			Student studentInDb = this.studentRepository.getStudentById(studentDTO.getStudentId());
			studentDTO.setStudentId(studentInDb.getStudentId());
			studentDTO.setEmail(studentInDb.getEmail());
			Student student = convertDTOToEntity(studentDTO);
			return convertEntityToDTO(this.studentRepository.save(student));
		} catch (Exception e) {
			throw new EntityNotExistException("No Student Exists with ID : " + studentDTO.getStudentId());
		}
	}

	@Override
	public GlobalPageResponseFormat<List<StudentDTO>> getStudentByPagination(String name, int page, int size,
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
			Page<Student> pages;
			if (name == null || name.trim().isEmpty()) {
				pages = this.studentRepository.findAll(pagingSort);
			} else {
				pages = this.studentRepository.findByNameContaining(name, pagingSort);
			}
			List<Student> listStudent = (pages.getContent().isEmpty()) ? null : pages.getContent();
			List<StudentDTO> listStudentDTO = listStudent.stream().map(this::convertEntityToDTO)
					.collect(Collectors.toList());
			GlobalPageResponseFormat<List<StudentDTO>> response = new GlobalPageResponseFormat<>();
			response.setError(false);
			response.setMessage(null);
			response.setCurrentPage(page);
			response.setTotalPages(pages.getTotalPages());
			response.setTotalRecords(pages.getTotalElements());
			response.setData(listStudentDTO);
			return response;
		} catch (NullPointerException e) {
			throw new RecordNotFoundException("No Student Exists in page: " + page);
		}
	}

	private StudentDTO convertEntityToDTO(Student student) {
		StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
		return studentDTO;
	}

	private Student convertDTOToEntity(StudentDTO studentDTO) {
		Student student = modelMapper.map(studentDTO, Student.class);
		return student;
	}

}
