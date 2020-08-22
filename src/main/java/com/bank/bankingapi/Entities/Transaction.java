package com.bank.bankingapi.Entities;

public class Transaction {
	private String transactionID;
	private double ammount;
	private TransactionType type;
	
	public Transaction(String transactionID, Double ammount, TransactionType type) {
		super();
		this.transactionID = transactionID;
		this.ammount = ammount;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", ammount=" + ammount + ", type=" + type + "]";
	}

	public String getTransactionID() {
		return transactionID;
	}
	public Double getAmmount() {
		return ammount;
	}
	public TransactionType getType() {
		return type;
	}
 
}
