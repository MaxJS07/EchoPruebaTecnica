package com.lepique.api_rest_echo.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class InscriptionDTO {

    private Long inscriptionId;

    @NotNull(message = "The student ID must not be null.")
    private Long studentId;
    private String studentName;
    private String studentGrade;

    @NotNull(message = "The ID of the course must not be null.")
    private Long courseId;
    private String courseName;
}
