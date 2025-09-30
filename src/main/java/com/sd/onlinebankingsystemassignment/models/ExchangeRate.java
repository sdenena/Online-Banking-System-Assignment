package com.sd.onlinebankingsystemassignment.models;

import com.sd.onlinebankingsystemassignment.base.entity.BaseEntity;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.models.enums.Calculation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "adm_exchange_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal exchangeRate;
    private String fromCurrency;
    private String toCurrency;

    private Calculation method;
    private Boolean defaultExchangeRate = false;

    // Convert Entity to Response DTO
    public ExchangeRateResponseDto toResponseDto() {
        return new ExchangeRateResponseDto(id, exchangeRate, fromCurrency, toCurrency, method);
    }
}
