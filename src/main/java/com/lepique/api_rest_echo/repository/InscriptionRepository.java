package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.InscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionEntity, Long> {

    //Query para obtener las inscripciones de un curso en especifico
    @Query("""
            SELECT i
            FROM InscriptionEntity i
            WHERE i.course.courseId = :idCourse
            """)
    List<InscriptionEntity> inscriptionsByCourse(@Param("idCourse") Long idCourse);


}
