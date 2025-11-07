package com.lepique.api_rest_echo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class StudentDTO {

    private Long studentId;

    @NotBlank(message = "The student name must not be empty.")
    private String studentName;

    @NotBlank(message = "The student last name must not be empty")
    private String studentLastName;

    @NotNull(message = "The student age must not be null")
    private Long studentAge;

    private Long gradeId;
    private String grade;

}
