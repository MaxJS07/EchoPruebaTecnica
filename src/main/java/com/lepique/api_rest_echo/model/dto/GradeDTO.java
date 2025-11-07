package com.lepique.api_rest_echo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter @Setter
@EqualsAndHashCode @ToString
public class GradeDTO {

    private long gradeId;

    @NotBlank(message = "The name of the grade must not be empty.")
    private String gradeName;
}
