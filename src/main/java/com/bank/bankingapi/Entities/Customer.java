package com.bank.bankingapi.Entities;

import java.util.List;

import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Status.Status;

public class Customer{
	private List<BankAccount> accounts;
	private String panNumber;
	
	public Customer(List<BankAccount> accounts, String panNumber) {
		super();
		this.accounts = accounts;
		this.panNumber = panNumber;
	}
	public String getPanNumber()
	{
		return panNumber;
	}
	public Status addBankAccount(BankAccount account)
	{
		accounts.add(account);
		System.out.println("bank account with ID:"+account.getAccountnumber()+" added to customer:"+panNumber);
		return Status.SUCCESS;
	}
	public void loadAccounts(List<BankAccount> accs)
	{
		this.accounts=accs;
	}
	@Override
	public String toString() {
		return "Customer [accounts=" + accounts + ", panNumber=" + panNumber + "]";
	}
}
