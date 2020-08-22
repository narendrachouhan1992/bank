package com.bank.bankingapi.Status;

public class TransactionStatus {
	private String message;
	private Status status;
	public String getMessage() {
		return message;
	}
	public Status getStatus() {
		return status;
	}
	public TransactionStatus(String message, Status status) {
		super();
		this.message = message;
		this.status = status;
	}
}
