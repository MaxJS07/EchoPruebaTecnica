package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.ClassDTO;
import com.lepique.api_rest_echo.model.dto.StudentDTO;
import com.lepique.api_rest_echo.service.ClassService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
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
 * - Obtener todas las asignaciones de cursos de los profesores.
 * - 4.1 Asignar un curso a un profesor (post)
 * - 4.2 Consultar todos los cursos asignados a un profesor especifico.
 */
@RestController
@RequestMapping("api/classes")
public class ClassController {

    //Service
    @Autowired
    private ClassService service;

    //GET METHOD
    @GetMapping("/obtainClasses")
    public List<ClassDTO> GetAllClasses(){
        return service.GetAllClasses();
    }

    //GET BY TEACHER
    @GetMapping("/classesByTeacher/{teacherId}")
    public List<ClassDTO> GetClassesByTeacher(@PathVariable Long teacherId){
        return service.GetClassesByTeacher(teacherId);
    }

    //POST METHOD
    @PostMapping("/postClass")
    public ResponseEntity<?> PostClass(
            @Valid @RequestBody ClassDTO classDto,
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
            ClassDTO reply = service.InsertClass(classDto);

            if(reply == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status","Wrong insert",
                        "errorType","VALIDATION_ERROR",
                        "message","The class data is not valid"
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
                    "message", "There was an error trying to insert the class.",
                    "detail", e.getMessage()
            ));
        }

    }
}
