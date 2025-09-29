package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransferDto {
    @NotBlank(message = "Sender's account number is required")
    private String fromAccountNumber = null;

    @NotBlank(message = "Receiver's account number is required")
    private String toAccountNumber = null;

    @Positive
    @NotNull
    private BigDecimal amount = null;
}
