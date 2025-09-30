package com.sd.onlinebankingsystemassignment.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionDirection {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String value;

    TransactionDirection(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TransactionDirection fromValue(String value) {
        for (TransactionDirection transactionStatus : TransactionDirection.values()) {
            if (transactionStatus.value.equalsIgnoreCase(value)) {
                return transactionStatus;
            }
        }
        throw new IllegalArgumentException("Invalid Transaction Direction: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
