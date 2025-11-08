package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.*;
import com.lepique.api_rest_echo.model.dto.ClassDTO;
import com.lepique.api_rest_echo.repository.ClassRepository;
import com.lepique.api_rest_echo.repository.CourseRepository;
import com.lepique.api_rest_echo.repository.GradeRepository;
import com.lepique.api_rest_echo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    @Autowired
    private ClassRepository repo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private GradeRepository gradeRepo;

    //GET GENERAL
    public List<ClassDTO> GetAllClasses(){
        List<ClassEntity> classes = repo.findAll();
        return classes.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    //GET BY TEACHER
    public List<ClassDTO> GetClassesByTeacher(Long teacherId){
        List<ClassEntity> classes = repo.classesByTeacher(teacherId);
        return classes.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    //POST
    public ClassDTO InsertClass(ClassDTO classDto){
        //Verificamos que todos los campos vengan llenos
        if(classDto.getTeacherId() == null || classDto.getCourseId() == null || classDto.getGradeId() == null){
            throw new IllegalArgumentException("An atribute is empty.");
        }
        else{
            //De no venir vacíos pasamos a insertar
            try{
                ClassEntity classEntity = fromDtoToEntity(classDto);
                ClassEntity savedClass = repo.save(classEntity);

                return fromEntityToDto(savedClass);
            }
            catch (Exception e){
                throw new RuntimeException("There was an error trying to insert the class: " + e.getMessage());
            }
        }
    }


    //CONVERSION METHODS
    private ClassDTO fromEntityToDto(ClassEntity entity) {
        ClassDTO dto = new ClassDTO();

        dto.setClassId(entity.getClassId());
        if(entity.getTeacher() != null){
            dto.setTeacherId(entity.getTeacher().getTeacherId());
            dto.setTeacherName(entity.getTeacher().getTeacherName() + " " + entity.getTeacher().getTeacherLastName());
            dto.setTeacherEmail(entity.getTeacher().getTeacherEmail());
        }
        else{
            dto.setTeacherId(null);
            dto.setTeacherName("Without teacher");
            dto.setTeacherEmail(null);
        }

        if(entity.getCourse() != null){
            dto.setCourseId(entity.getCourse().getCourseId());
            dto.setCourseName(entity.getCourse().getCourseName());
        }
        else{
            dto.setCourseId(null);
            dto.setCourseName("Without course");
        }

        if(entity.getGrade() != null){
            dto.setGradeId(entity.getGrade().getGradeId());
            dto.setGradeName(entity.getGrade().getGradeName());
        }
        else{
            dto.setGradeId(null);
            dto.setGradeName("Without grade");
        }

        return dto;
    }

    private ClassEntity fromDtoToEntity(ClassDTO dto){
        ClassEntity entity = new ClassEntity();

        if(dto.getTeacherId() != null){
            TeacherEntity teacher = teacherRepo.findById(dto.getTeacherId())
                    .orElseThrow(() -> new IllegalArgumentException("The teacher with the given ID was not found"));
            entity.setTeacher(teacher);
        }

        if(dto.getCourseId() != null){
            CourseEntity course = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("The course with the given ID was not found"));
            entity.setCourse(course);
        }

        if(dto.getGradeId() != null){
            GradeEntity grade = gradeRepo.findById(dto.getGradeId())
                    .orElseThrow(() -> new IllegalArgumentException("The grade with the given ID was not found"));
            entity.setGrade(grade);
        }

        return entity;
    }
}
