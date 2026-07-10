package com.fintech.banking_app.dto;

import jakarta.validation.constraints.Positive;

public record TransactionRequest(

        @Positive(message = "Amount must be greater than zero")
        double amount

) {}