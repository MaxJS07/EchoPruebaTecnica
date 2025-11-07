package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
}
