package com.sd.onlinebankingsystemassignment.models;

import com.sd.onlinebankingsystemassignment.base.entity.BaseEntity;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionStatus;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "account_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 20)
    private TransactionType transactionType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "from_account_number")
    private String fromAccountNumber;

    @Column(name = "to_account_number")
    private String toAccountNumber;

    @Column(name = "transaction_status", length = 20)
    private TransactionStatus transactionStatus;

    @Column(name = "tran_id", length = 50)
    private String tranId;

    @Column(name = "tran_date")
    private Date tranDate;

    private String currency;
}
