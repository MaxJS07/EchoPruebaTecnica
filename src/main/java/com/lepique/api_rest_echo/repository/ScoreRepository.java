package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

    //Query para obtener las calificaciones de los cursos de un estudiante específico
    @Query("""
            SELECT s
            FROM ScoreEntity s
            WHERE s.student.studentId = :idStudent
            """)
    List<ScoreEntity> scoresByStudent(@Param("idStudent") Long idStudent);
}
