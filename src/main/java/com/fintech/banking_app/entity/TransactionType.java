package com.fintech.banking_app.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TransactionType {

    DEPOSIT,
    WITHDRAW,
    TRANSFER;
    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;

}