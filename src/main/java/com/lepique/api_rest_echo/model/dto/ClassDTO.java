package com.lepique.api_rest_echo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class ClassDTO {

    private Long classId;

    @NotNull(message = "The teacher ID must not be null.")
    private Long teacherId;
    private String teacherName;
    private String teacherEmail;

    @NotNull(message = "The ID of the course must not be null.")
    private Long courseId;
    private String courseName;

    @NotNull(message = "The ID of the grade must not be null.")
    private Long gradeId;
    private String gradeName;



}
