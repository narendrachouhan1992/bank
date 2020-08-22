package com.bank.bankingapi.Entities.accounts;

import java.util.List;

import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.Status.Status;

public abstract class BankAccount {
	final String accountnumber;
	final String holderName;
	protected final double minimumBalance;
	protected double currentBalance;
	protected final double intrestRate;
	List<Transaction> transactions;
	BankAccount(String accountnumber, double minimumBalance, double currentBalance, double intrestRate,
			List<Transaction> transactions, String holderName) {
		super();
		this.accountnumber = accountnumber;
		this.minimumBalance = minimumBalance;
		this.currentBalance = currentBalance;
		this.intrestRate = intrestRate;
		this.transactions = transactions;
		this.holderName = holderName;
	}
	public List<Transaction> getTransactionHistory()
	{
		return this.transactions;
	}
	public List<Transaction> getMiniStatement()
	{
		return transactions.subList(transactions.size()<=10? 0:transactions.size()-10, transactions.size());
	}
	public void loadTransactions(List<Transaction> trans)
	{
		this.transactions = trans;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public Double getMinimumBalance() {
		return minimumBalance;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public double getIntrestRate() {
		return intrestRate;
	}

	abstract public Status withdraw(double ammount) throws BankingException;
	abstract public Status deposit(double ammount) throws BankingException;
	abstract public String getType();
}
