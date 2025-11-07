package com.lepique.api_rest_echo.repository;

import com.lepique.api_rest_echo.domain.InscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionEntity, Long> {

}
