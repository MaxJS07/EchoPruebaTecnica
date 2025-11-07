package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.GradeEntity;
import com.lepique.api_rest_echo.model.dto.GradeDTO;
import com.lepique.api_rest_echo.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    //Inyectamos el repo
    @Autowired
    private GradeRepository repo;

    /**
     * REGRESA TODAS LAS ENTIDADES DE GRADO EN LISTA
     */
    public List<GradeDTO> getAllGrades(){
        List<GradeEntity> grades = repo.findAll();
        return grades.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }


    //Metodos de conversión
    //De entity a dto
    private GradeDTO fromEntityToDto(GradeEntity entity){
        GradeDTO dto = new GradeDTO();

        dto.setGradeId(entity.getGradeId());
        dto.setGradeName(entity.getGradeName());

        return dto;
    }

    //De dto a entity
    private GradeEntity fromDtoToEntity(GradeDTO dto){
        GradeEntity entity = new GradeEntity();

        entity.setGradeName(dto.getGradeName());

        return entity;
    }
}
