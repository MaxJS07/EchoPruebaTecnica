package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.StudentDTO;
import com.lepique.api_rest_echo.model.dto.TeacherDTO;
import com.lepique.api_rest_echo.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * En este controller se encuentran los endpoints para:
 * - Obtener todos los profesores
 * - Añadir un profesor
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    //Service
    @Autowired
    private TeacherService service;

    @GetMapping("/obtainTeachers")
    public List<TeacherDTO> GetAllTeachers(){
        return service.GetAllTeachers();
    }

    @PostMapping("/postTeacher")
    public ResponseEntity<?> InsertTeacher(
            @Valid @RequestBody TeacherDTO teacher,
            BindingResult bindingResult,
            HttpServletRequest request
    ){
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );
            // Aca se crea la respuesta HTTP con código(400 BAD REQUEST) con información del error
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "VALIDATION_ERROR",
                    "errors", validationErrors
            ));
        }

        try{
            TeacherDTO reply = service.InsertTeacher(teacher);

            if(reply == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status","Wrong insert",
                        "errorType","VALIDATION_ERROR",
                        "message","The teacher data is not valid"
                ));
            }
            else{
                return  ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                        "status", "success",
                        "data", reply
                ));
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "There was an error trying to insert the teacher.",
                    "detail", e.getMessage()
            ));
        }
    }
}
