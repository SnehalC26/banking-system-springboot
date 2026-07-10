package com.fintech.banking_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequestDto(

        @NotNull
        Long fromAccountId,

        @NotNull
        Long toAccountId,

        @Positive(message = "Transfer amount must be greater than zero")
        double amount

) {}