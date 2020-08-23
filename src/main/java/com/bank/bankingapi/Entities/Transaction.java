package com.bank.bankingapi.Entities;

public class Transaction {
	private String transactionID;
	private double ammount;
	private TransactionType type;
	private int sequence;
	public Transaction(String transactionID, Double ammount, TransactionType type, int sequence) {
		super();
		this.transactionID = transactionID;
		this.ammount = ammount;
		this.type = type;
		this.sequence = sequence;
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

	public int getSequence() {
		return sequence;
	}
 
}
