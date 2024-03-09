package com.techchallenge.infrastructure.persistence.repository;

import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.persistence.entity.NotifyCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotifyCustomerRespository extends JpaRepository<NotifyCustomerEntity, String> {


    @Query("Select n from NotifyCustomerEntity n  JOIN FETCH n.customerEntity where n.notifySent = false")
    List<NotifyCustomerEntity> findNotSent();
}
