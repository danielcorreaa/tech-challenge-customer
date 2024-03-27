package com.techchallenge.infrastructure.persistence.repository;

import com.techchallenge.infrastructure.persistence.entity.SolicitationExcludeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SolicitationExcludeResository extends JpaRepository<SolicitationExcludeEntity, String> {


    @Query("Select s from SolicitationExcludeEntity s where s.executed = true")
    Page<SolicitationExcludeEntity> findAllSolicitationExcluded(Pageable pageable);

    @Query("Select s from SolicitationExcludeEntity s where s.executed = false")
    Page<SolicitationExcludeEntity> findAllSolicitationNotExcluded(Pageable pageable);
}
