package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    //Query para obtener estudiantes por grado
    @Query("""
            SELECT s
            FROM StudentEntity s
            WHERE s.grade.gradeId = :idGrade
            """)
    List<StudentEntity> studentsByGrade(@Param("idGrade") Long idGrade);
}
