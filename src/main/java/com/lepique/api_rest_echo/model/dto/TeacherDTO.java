package com.lepique.api_rest_echo.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class TeacherDTO {

    private Long teacherId;

    @NotBlank(message = "The teacher name must not be empty")
    private String teacherName;

    @NotBlank(message = "The teacher last name must not be empty")
    private String teacherLastName;

    @Email(message = "The email must have a correct email format")
    @NotBlank(message = "The email must not be empty")
    private String teacherEmail;
}
