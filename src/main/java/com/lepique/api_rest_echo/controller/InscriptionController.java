package com.lepique.api_rest_echo.controller;

import com.lepique.api_rest_echo.model.dto.InscriptionDTO;
import com.lepique.api_rest_echo.model.dto.StudentDTO;
import com.lepique.api_rest_echo.service.InscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * En este controller se encuentran los endpoints para:
 * - Obtener todas las inscripciones
 * - 2.2 | 5.1 Obtener todos los estudiantes de un curso (inscripciones por id de curso)
 * - 2.1 Inscribir un estudiante a un curso (post)
 * - 2.1 Desinscribir un estudiante de un curso (delete)
 */
@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    //Service
    @Autowired
    private InscriptionService service;

    //GET METHOD
    @GetMapping("/obatainInscriptions")
    public List<InscriptionDTO> GetAllInscriptions(){
        return service.GetAllInscriptions();
    }

    //GET BY COURSE METHOD
    @GetMapping("/inscriptionsByCourse/{courseId}")
    public List<InscriptionDTO> GetInscriptionsByCourse(@PathVariable Long courseId){
        return service.GetInscriptionsByCourse(courseId);
    }

    //POST METHOD
    @PostMapping("/insertInscription")
    public ResponseEntity<Map<String,Object>> InsertInscription(
            @Valid @RequestBody InscriptionDTO inscription,
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
            InscriptionDTO reply = service.InsertInscription(inscription);

            if(reply == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status","Wrong insert",
                        "errorType","VALIDATION_ERROR",
                        "message","The inscription data is not valid"
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
                    "message", "There was an error trying to insert the inscription.",
                    "detail", e.getMessage()
            ));
        }
    }

    //DELETE METHOD
    @DeleteMapping("/deleteInscription/{id}")
    public ResponseEntity<?> DeleteInscription(@PathVariable Long id){
        try{
            if(!service.DeleteInscription(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Mensaje-Error", "Inscription Not Found")
                        .body(Map.of(
                                "error","Not found",
                                "mensaje", "The inscription wasn't found",
                                "timestamp", Instant.now().toString()
                        ));
            }

            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "The inscription was deleted."
            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "There was an error trying to delete the inscription.",
                    "detail", e.getMessage()
            ));
        }
    }

}
