package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.CourseEntity;
import com.lepique.api_rest_echo.domain.GradeEntity;
import com.lepique.api_rest_echo.model.dto.CourseDTO;
import com.lepique.api_rest_echo.model.dto.GradeDTO;
import com.lepique.api_rest_echo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    //Inyectamos el repositorio
    @Autowired
    private CourseRepository repo;

    /**
     * REGRESA TODOS LOS CURSOS DE LA TABLA DE CURSOS
     * @return
     */
    public List<CourseDTO> GetAllCourses(){
        List<CourseEntity> courses = repo.findAll();
        return courses.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }


    //Métodos de conversion
    //De entity a dto
    private CourseDTO fromEntityToDto(CourseEntity entity){
        CourseDTO dto = new CourseDTO();

        dto.setCourseId(entity.getCourseId());
        dto.setCourseName(entity.getCourseName());

        return dto;
    }

    //De dto a entity
    private CourseEntity fromDtoToEntity(CourseDTO dto){
        CourseEntity entity = new CourseEntity();

        entity.setCourseName(dto.getCourseName());

        return entity;
    }
}
