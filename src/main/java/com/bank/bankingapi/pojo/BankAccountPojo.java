package com.bank.bankingapi.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BankAccountPojo {
	@Id
	private String accountNumber;
	private String holderName;
	private double minimumBalance;
	private BankAccountType type;
	private double currentBalance;
	private  double intrestRate;
	@ManyToOne
	private BranchPojo branch;
	@ManyToOne
	private CustomerPojo customer;
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public BranchPojo getBranch() {
		return branch;
	}
	public void setBranch(BranchPojo branch) {
		this.branch = branch;
	}
	public CustomerPojo getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerPojo customer) {
		this.customer = customer;
	}
	public String getAccountnumber() {
		return accountNumber;
	}
	public String getHolderName() {
		return holderName;
	}
	public double getMinimumBalance() {
		return minimumBalance;
	}
	public double getIntrestRate() {
		return intrestRate;
	}
	public BankAccountType getType() {
		return type;
	}
	public void setType(BankAccountType type) {
		this.type = type;
	}

}
