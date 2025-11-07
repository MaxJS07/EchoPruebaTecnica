package com.lepique.api_rest_echo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class CourseDTO {

    private Long courseId;

    @NotBlank(message = "The name of the course must not be empty.")
    private String courseName;
}
