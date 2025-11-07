package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.GradeDTO;
import com.lepique.api_rest_echo.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * En este controller se encuentran los endpoints para:
 * - Obtener todos los grados
 */
@RestController
@RequestMapping("/api/grades")
public class GradeController {

    //Inyectamos el service
    @Autowired
    private GradeService service;

    //GET METHOD
    @GetMapping("/obtainGrades")
    public List<GradeDTO> grades(){
        return service.getAllGrades();
    }
}
