package com.lepique.api_rest_echo.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class ScoreDTO {

    private Long scoreId;

    @NotNull(message = "The student ID must not be null.")
    private Long studentId;
    private String studentName;
    private String studentGrade;

    @NotNull(message = "The ID of the course must not be null.")
    private Long courseId;
    private String courseName;

    @NotNull(message = "The score must not be null.")
    @PositiveOrZero(message = "The score must be greater than or equal to zero.")
    private Float score;
}
