package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.StudentDTO;
import com.lepique.api_rest_echo.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * En este controller se encuentran los endpoints para:
 * - 1.1 Obtener todos los estudiantes
 * - 1.1 Insertar un estudiante
 * - 1.1 Actualizar un estudiante
 * - 1.1 Eliminar un estudiante
 * - 5.1 Listar estudiantes por grado (get por id de grado)
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    //Inyectamos service
    @Autowired
    private StudentService service;

    //GET METHOD
    @GetMapping("/obtainStudents")
    public List<StudentDTO> GetAllStudents(){
        return service.GetAllStudents();
    }

    //GET BY GRADE METHOD
    @GetMapping("/studentsByGrade/{gradeId}")
    public List<StudentDTO> GetStudentsByGrade(@PathVariable Long gradeId){
        return service.GetStudentsByGrade(gradeId);
    }

    //POST METHOD
    @PostMapping("/insertStudent")
    public ResponseEntity<Map<String, Object>> InsertStudent(
            @Valid @RequestBody StudentDTO student,
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
            StudentDTO reply = service.InsertStudent(student);

            if(reply == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status","Wrong insert",
                        "errorType","VALIDATION_ERROR",
                        "message","The student data is not valid"
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
                    "message", "There was an error trying to insert the student.",
                    "detail", e.getMessage()
            ));
        }
    }

    //PUT METHOD
    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<?> UpdateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO student,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try{
            StudentDTO studentUpdated = service.UpdateStudent(id, student);
            return ResponseEntity.ok(studentUpdated);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE METHOD
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<?> DeleteStudent(@PathVariable Long id){
        try{
            if(!service.DeleteStudent(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Mensaje-Error", "Student Not Found")
                        .body(Map.of(
                                "error","Not found",
                                "mensaje", "The student wasn't found",
                                "timestamp", Instant.now().toString()
                        ));
            }

            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "The student was deleted."
            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "There was an error trying to delete the student.",
                    "detail", e.getMessage()
            ));
        }
    }

}
