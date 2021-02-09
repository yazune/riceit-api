package com.agh.riceitapi.repository;

import com.agh.riceitapi.model.ManualParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManualParametersRepository extends JpaRepository<ManualParameters, Long> {

    Optional<ManualParameters> findByUserId(Long userId);
}
