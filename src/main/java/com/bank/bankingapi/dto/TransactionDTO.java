package com.bank.bankingapi.dto;

public class TransactionDTO {
	private String accountNumber;
	private String branchNuber;
	private double tranAmmount;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBranchNuber() {
		return branchNuber;
	}
	public void setBranchNuber(String branchNuber) {
		this.branchNuber = branchNuber;
	}
	public double getTranAmmount() {
		return tranAmmount;
	}
	public void setTranAmmount(double tranAmmount) {
		this.tranAmmount = tranAmmount;
	}

}
