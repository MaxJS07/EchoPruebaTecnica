package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.ScoreDTO;
import com.lepique.api_rest_echo.model.dto.StudentDTO;
import com.lepique.api_rest_echo.service.ScoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.UserTransaction;
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
 * - Obtener todas las calificaciones
 * - 3.1 Registrar calificaciones de estudiantes de cada curso (post)
 * - 3.1 Actualizar calificaciones de estudiantes en cada curso (put)
 * - 3.2 Listar las calificaciones de todos los cursos de un estudiante (get por ID de estudiante)
 */
@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    //service
    @Autowired
    private ScoreService service;

    //GET METHOD
    @GetMapping("/obtainScores")
    public List<ScoreDTO> GetAllScores(){
        return service.GetAllScores();
    }

    //GET BY STUDENT METHOD
    @GetMapping("/scoresByStudent/{studentId}")
    public List<ScoreDTO> GetScoresByStudent(@PathVariable Long studentId){
        return service.GetScoresByStudent(studentId);
    }

    //POST METHOD
    @PostMapping("/insertScore")
    public ResponseEntity<Map<String, Object>> insertScore(
            @Valid @RequestBody ScoreDTO score,
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
            ScoreDTO reply = service.insertScore(score);

            if(reply == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status","Wrong insert",
                        "errorType","VALIDATION_ERROR",
                        "message","The score data is not valid"
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
                    "message", "There was an error trying to insert the score.",
                    "detail", e.getMessage()
            ));
        }
    }

    //PUT METHOD
    @PutMapping("/updateScore/{id}")
    public ResponseEntity<?> UpdateScore(
            @PathVariable Long id,
            @Valid @RequestBody ScoreDTO score,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try{
            ScoreDTO scoreUpdated = service.updateScore(id, score);
            return ResponseEntity.ok(scoreUpdated);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
