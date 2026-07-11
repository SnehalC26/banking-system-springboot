package com.fintech.banking_app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	
	@OneToMany(
	        mappedBy = "account",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	)
	private List<Transaction> transactions = new ArrayList<>();
	
	@Column(name="account_holder_name")
    @NotBlank(message = "Account holder name cannot be empty")
private String accountHolderName;
	 @PositiveOrZero(message = "Balance cannot be negative")
private double balance;
	 @ManyToOne
	 @JoinColumn(name = "user_id")
	 private User user;
	 public Account(Long id, String accountHolderName, double balance) {

		    this.id = id;
		    this.accountHolderName = accountHolderName;
		    this.balance = balance;

		}

}
