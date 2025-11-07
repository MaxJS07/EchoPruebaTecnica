package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.CourseEntity;
import com.lepique.api_rest_echo.domain.InscriptionEntity;
import com.lepique.api_rest_echo.domain.StudentEntity;
import com.lepique.api_rest_echo.model.dto.InscriptionDTO;
import com.lepique.api_rest_echo.repository.CourseRepository;
import com.lepique.api_rest_echo.repository.InscriptionRepository;
import com.lepique.api_rest_echo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscriptionService {

    //Inyectamos los repos
    @Autowired
    private InscriptionRepository repo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;


    /**
     * RETURNS ALL THE INSCRIPTIONS IN A LIST
     */
    public List<InscriptionDTO> GetAllInscriptions(){
        List<InscriptionEntity> inscriptions = repo.findAll();
        return inscriptions.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * RETURNS ALL THE INSCRIPTIONS FROM A SPECIFIC COURSE
     * @param idCourse
     * @return
     */
    public List<InscriptionDTO> GetInscriptionsByCourse(Long idCourse){
        List<InscriptionEntity> inscriptions = repo.inscriptionsByCourse(idCourse);
        return inscriptions.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * INSERTS THE INSCRIPTION AND RETURNS THE INSERTED DATA
     * @param inscription
     * @return
     */
    public InscriptionDTO InsertInscription(InscriptionDTO inscription){

        if(inscription.getStudentId() == null || inscription.getCourseId() == null){
            throw new IllegalArgumentException("An atribute is empty.");
        }
        else{
            try{
                InscriptionEntity inscriptionEntity = fromDtoToEntity(inscription);
                InscriptionEntity savedInscription = repo.save(inscriptionEntity);

                return fromEntityToDto(savedInscription);
            }
            catch (Exception e){
                throw new RuntimeException("There was an error trying to insert the inscription: " + e.getMessage());
            }
        }
    }


    /**
     * DELETES AN INSCRIPTION BY ID
     * @param inscriptionId
     * @return
     */
    public boolean DeleteInscription(Long inscriptionId){
        try{
            InscriptionEntity inscExis = repo.findById(inscriptionId).orElse(null);
            if(inscExis != null){
                repo.deleteById(inscriptionId);
                return true;
            }
            else{
                throw new IllegalArgumentException("Inscription not found.");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("There was an error deleting the inscription.");
        }
    }


    //CONVERSION METHODS
    private InscriptionDTO fromEntityToDto(InscriptionEntity entity){

        InscriptionDTO dto = new InscriptionDTO();

        dto.setInscriptionId(entity.getInscriptionId());
        if(entity.getStudent() != null){
            dto.setStudentId(entity.getStudent().getStudentId());
            dto.setStudentName(entity.getStudent().getStudentName() + " " + entity.getStudent().getStudentLastName());
            dto.setStudentGrade(entity.getStudent().getGrade().getGradeName());
        }
        else{
            dto.setStudentId(null);
            dto.setStudentName("Without student");
            dto.setStudentGrade("Whithout student grade");
        }

        if(entity.getCourse() != null){
            dto.setCourseId(entity.getCourse().getCourseId());
            dto.setCourseName(entity.getCourse().getCourseName());
        }
        else{
            dto.setCourseId(null);
            dto.setCourseName("Without course");
        }

        return dto;
    }

    private InscriptionEntity fromDtoToEntity(InscriptionDTO dto){

        InscriptionEntity entity = new InscriptionEntity();

        if(dto.getStudentId() != null){
            StudentEntity student = studentRepo.findById(dto.getStudentId())
                    .orElseThrow(()-> new IllegalArgumentException("The student with the given ID was not found."));
            entity.setStudent(student);
        }

        if(dto.getCourseId() != null){
            CourseEntity course = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("The course with the given ID was not found."));
            entity.setCourse(course);
        }

        return entity;
    }
}
