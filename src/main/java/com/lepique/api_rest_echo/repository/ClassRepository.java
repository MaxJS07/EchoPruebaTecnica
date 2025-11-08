package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    //Query para traer los cursos que imparte un profesor en especifico
    @Query("""
            SELECT c
            FROM ClassEntity c
            WHERE c.teacher.teacherId = :idTeacher
            """)
    List<ClassEntity> classesByTeacher(@Param("idTeacher") Long idTeacher);
}
