package com.practicalexam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practicalexam.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query(value = "select * from student where student_id=:studentid", nativeQuery = true)
	Student getStudentById(@Param("studentid") Long studentId);

	Page<Student> findByNameContaining(String name, Pageable pagingSort);

	@Query(value = "select * from student where email=:email", nativeQuery = true)
	Student getStudentByEmail(@Param("email") String email);

}
