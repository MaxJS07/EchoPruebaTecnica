package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.StudentEntity;
import com.lepique.api_rest_echo.domain.TeacherEntity;
import com.lepique.api_rest_echo.model.dto.TeacherDTO;
import com.lepique.api_rest_echo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repo;

    //GET GENERAL
    public List<TeacherDTO> GetAllTeachers(){
        List<TeacherEntity> teachers = repo.findAll();
        return teachers.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    //POST
    public TeacherDTO InsertTeacher(TeacherDTO teacher){
        if(teacher.getTeacherName().isBlank() || teacher.getTeacherLastName().isBlank() || teacher.getTeacherEmail().isBlank()){
            throw new IllegalArgumentException("An atribute is empty.");
        }
        else{
            try{
                TeacherEntity teacherEntity = fromDtoToEntity(teacher);
                TeacherEntity savedTeacher = repo.save(teacherEntity);

                return fromEntityToDto(savedTeacher);
            }
            catch (Exception e){
                throw new RuntimeException("There was an error trying to insert the teacher: " + e.getMessage());
            }
        }
    }


    //CONVERSION METHODS
    private TeacherDTO fromEntityToDto(TeacherEntity entity){
        TeacherDTO dto = new TeacherDTO();

        dto.setTeacherId(entity.getTeacherId());
        dto.setTeacherName(entity.getTeacherName());
        dto.setTeacherLastName(entity.getTeacherLastName());
        dto.setTeacherEmail(entity.getTeacherEmail());

        return dto;
    }

    private TeacherEntity fromDtoToEntity(TeacherDTO dto){
        TeacherEntity entity = new TeacherEntity();

        entity.setTeacherName(dto.getTeacherName());
        entity.setTeacherLastName(dto.getTeacherLastName());
        entity.setTeacherEmail(dto.getTeacherEmail());

        return entity;
    }
}
