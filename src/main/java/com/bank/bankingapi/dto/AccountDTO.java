package com.bank.bankingapi.dto;

public class AccountDTO {
	private String name;
	private String pan;
	private String branchId;
	private double initilAmmount;
	private String accountType;
	private String accountNumber;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public double getInitilAmmount() {
		return initilAmmount;
	}
	public void setInitilAmmount(double initilAmmount) {
		this.initilAmmount = initilAmmount;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	

}
