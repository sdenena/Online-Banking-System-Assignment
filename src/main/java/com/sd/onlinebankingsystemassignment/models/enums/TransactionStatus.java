package com.sd.onlinebankingsystemassignment.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TransactionStatus fromValue(String value) {
        for (TransactionStatus transactionStatus : TransactionStatus.values()) {
            if (transactionStatus.value.equalsIgnoreCase(value)) {
                return transactionStatus;
            }
        }
        throw new IllegalArgumentException("Invalid Transaction Status: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
