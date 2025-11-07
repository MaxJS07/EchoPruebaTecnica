package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

}
