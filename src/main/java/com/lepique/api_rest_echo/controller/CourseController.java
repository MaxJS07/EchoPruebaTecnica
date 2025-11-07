package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.CourseDTO;
import com.lepique.api_rest_echo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * En este controller se encuentran los endpoints para:
 * -Obtener todos los cursos
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    //Inyectamos el service
    @Autowired
    private CourseService service;

    //GET METHOD
    @GetMapping("/obtainCourses")
    public List<CourseDTO> GetAllCourses(){
        return service.GetAllCourses();
    }
}
