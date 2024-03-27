package com.techchallenge.infrastructure.persistence.entity;

import com.techchallenge.domain.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notify-customer")
public class NotifyCustomerEntity {

    @Id
    @Column(name = "orderId", nullable = false)
    private String orderId;
    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    private CustomerEntity customerEntity;
    private String description;
    private Boolean notifySent;

}
