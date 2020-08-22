package com.bank.bankingapi.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.bank.bankingapi.Entities.TransactionType;

@Entity
public class TransactionPojo {
	@Id
	private String transactionId;
	private double transactionAmmount;
	private TransactionType type;
	@ManyToOne
	private BankAccountPojo account;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public double getTransactionAmmount() {
		return transactionAmmount;
	}
	public void setTransactionAmmount(double transactionAmmount) {
		this.transactionAmmount = transactionAmmount;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public BankAccountPojo getAccount() {
		return account;
	}
	public void setAccount(BankAccountPojo account) {
		this.account = account;
	}
}
