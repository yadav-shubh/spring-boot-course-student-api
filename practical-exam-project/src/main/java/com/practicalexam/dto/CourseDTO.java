package com.practicalexam.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CourseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long courseId;
	@NotNull
	@Size(max = 200, min = 3, message = "title should be in range of 3 to 200 charecter")
	private String title;
	@Positive(message = "duration can not be 0")
	private Integer duration;
	@NotNull
	@Size(max = 200, min = 3, message = "description should be in range of 3 to 200 charecter")
	private String description;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
