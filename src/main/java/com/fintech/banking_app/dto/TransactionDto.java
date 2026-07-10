

package com.fintech.banking_app.dto;

import java.time.LocalDateTime;

import com.fintech.banking_app.entity.TransactionDirection;
import com.fintech.banking_app.entity.TransactionType;

public record TransactionDto(

        Long id,

        double amount,

        TransactionType type,

        TransactionDirection direction,

        LocalDateTime timestamp

) {

}