package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.GradeEntity;
import com.lepique.api_rest_echo.domain.StudentEntity;
import com.lepique.api_rest_echo.model.dto.StudentDTO;
import com.lepique.api_rest_echo.repository.GradeRepository;
import com.lepique.api_rest_echo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    //Inyectamos repositorios
    @Autowired
    private StudentRepository repo;

    @Autowired
    private GradeRepository gradeRepo;

    /**
     * RETURNS ALL THE STUDENT IN A LIST
     * @return
     */
    public List<StudentDTO> GetAllStudents(){
        List<StudentEntity> students = repo.findAll();
        return students.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }


    /**
     * INSERTS A STUDENT IN THE DATABASE AND RETURNS THE INSERTED DATA
     * @param student
     * @return
     */
    public StudentDTO InsertStudent(StudentDTO student){
        //Verificamos que todos los campos vengan llenos
        if(student.getStudentName().isBlank() || student.getStudentLastName().isBlank() || student.getStudentAge() == null || student.getGradeId() == null){
            throw new IllegalArgumentException("An atribute is empty.");
        }
        else{
            //De no venir vacíos pasamos a insertar
            try{
                StudentEntity studentEntity = fromDtoToEntity(student);
                StudentEntity savedStudent = repo.save(studentEntity);

                return fromEntityToDto(savedStudent);
            }
            catch (Exception e){
                throw new RuntimeException("There was an error trying to insert the student: " + e.getMessage());
            }
        }
    }


    /**
     * UPDATES AN STUDENT, AND RETURNS THE UPDATED DATA
     * @param studentId
     * @param student
     * @return
     */
    public StudentDTO UpdateStudent(Long studentId, StudentDTO student){
        //Verificamos existencia
        StudentEntity studentExis = repo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("The student that was pretended to update wasn't found."));

        try{
            studentExis.setStudentName(student.getStudentName());
            studentExis.setStudentLastName(student.getStudentLastName());
            studentExis.setStudentAge(student.getStudentAge());

            if(student.getGradeId() != null){
                GradeEntity grade = gradeRepo.findById(student.getGradeId())
                        .orElseThrow(() -> new IllegalArgumentException("The grade with the given ID wasn't found."));
                studentExis.setGrade(grade);
            }
            else{
                studentExis.setGrade(null);
            }

            StudentEntity studentUpdated = repo.save(studentExis);

            return fromEntityToDto(studentUpdated);
        }
        catch (Exception e){
            throw new RuntimeException("There was an error trying to update the student: " + e.getMessage());
        }

    }


    public Boolean DeleteStudent(Long studentId){
        try{
            StudentEntity studentExis = repo.findById(studentId).orElse(null);
            if(studentExis != null){
                repo.deleteById(studentId);
                return true;
            }
            else{
                throw new IllegalArgumentException("Student not found.");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("There was an error deleting the student.");
        }
    }




    //METODOS DE CONVERSION
    //De entity a dto
    private StudentDTO fromEntityToDto(StudentEntity entity){

        StudentDTO dto = new StudentDTO();

        dto.setStudentId(entity.getStudentId());
        dto.setStudentName(entity.getStudentName());
        dto.setStudentLastName(entity.getStudentLastName());
        dto.setStudentAge(entity.getStudentAge());

        if(entity.getGrade() != null){
            dto.setGradeId(entity.getGrade().getGradeId());
            dto.setGrade(entity.getGrade().getGradeName());
        }
        else{
            dto.setGradeId(null);
            dto.setGrade("Without grade");
        }

        return dto;
    }

    //De dto a entity
    private StudentEntity fromDtoToEntity(StudentDTO dto){

        StudentEntity entity = new StudentEntity();

        entity.setStudentName(dto.getStudentName());
        entity.setStudentLastName(dto.getStudentLastName());
        entity.setStudentAge(dto.getStudentAge());

        if(dto.getGradeId() != null){
            GradeEntity grade = gradeRepo.findById(dto.getGradeId())
                    .orElseThrow(() -> new IllegalArgumentException("The grade whit the ID " + dto.getGradeId() + " was not found."));
            entity.setGrade(grade);
        }

        return entity;
    }

}
