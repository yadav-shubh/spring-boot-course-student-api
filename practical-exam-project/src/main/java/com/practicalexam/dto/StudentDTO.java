package com.practicalexam.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.practicalexam.model.Course;

public class StudentDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long studentId;
	@NotNull
	@Size(min = 3, max = 150, message = "name should be in range of 3 to 150 charecter")
	private String name;
	@Email
	private String email;
	@NotNull
	@Size(min = 3, max = 150, message = "name should be in range of 3 to 150 charecter")
	private String address;
	@NotNull
	private Course course;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "StudentDTO [studentId=" + studentId + ", name=" + name + ", email=" + email + ", address=" + address
				+ ", course=" + course + "]";
	}

}
