package com.practicalexam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practicalexam.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

	Page<Course> findByTitleContaining(String title, Pageable pagingSort);

	@Query(value = "select * from course where course_id=:courseId",nativeQuery = true)
	Course getCourseById(@Param("courseId") Long courseId);

}
