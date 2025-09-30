package com.sd.onlinebankingsystemassignment.models.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Calculation {
    DIVIDE {
        @Override
        public BigDecimal cal(BigDecimal amount, BigDecimal exchangeRate) {
            System.out.println("Calculation: DIVIDE");
            // Example: scale = 6, RoundingMode = HALF_UP
            return amount.divide(exchangeRate, 6, RoundingMode.HALF_UP);
        }
    },
    MULTIPLY {
        @Override
        public BigDecimal cal(BigDecimal amount, BigDecimal exchangeRate) {
            System.out.println("Calculation: MULTIPLY");
            return amount.multiply(exchangeRate);
        }
    };

    public abstract BigDecimal cal(BigDecimal amount, BigDecimal exchangeRate);
}
