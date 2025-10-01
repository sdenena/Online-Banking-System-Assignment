package com.sd.onlinebankingsystemassignment.models;

import com.sd.onlinebankingsystemassignment.base.entity.BaseEntity;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction_limits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLimit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;
    private Double limitAmount;
}
