
package com.fintech.banking_app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "transactions")
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private double amount;
    private String description;


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    private LocalDateTime timestamp;


    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;


    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;


    public Transaction() {
    }


    public Transaction(
            Double amount,
            TransactionType transactionType,
            TransactionDirection direction,
            String description,
            Account account
            
    ) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.direction = direction;
        this.description = description;
        this.account = account;
        this.timestamp = LocalDateTime.now();
    }
    
  


	public Long getId() {
        return id;
    }


    public double getAmount() {
        return amount;
    }


    public TransactionType getType() {
        return transactionType;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionDirection getDirection(){
    	return direction;
    }

    public String getDescription() {
		return description;
	}


	


	public Account getAccount() {
        return account;
    }
}