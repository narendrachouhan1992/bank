package com.bank.bankingapi.Entities.accounts;

import java.util.ArrayList;

import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Entities.TransactionType;
import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.Exceptions.NegativeAmmountException;
import com.bank.bankingapi.Exceptions.NoSufficientAmmountException;
import com.bank.bankingapi.Status.Status;
import com.bank.bankingapi.utils.BankUtils;

public final class CurrentAccount extends BankAccount {
	public CurrentAccount(String accountnumber, double currentBalance, String holderName) throws BankingException {
		super(accountnumber, 20000, currentBalance, 0, new ArrayList<Transaction>(),holderName);
		if (currentBalance < 10000)
			throw new NoSufficientAmmountException("Ammount passed to create account is not sufficient. ammount:"
					+ currentBalance + " minimumRequired:20000");
		System.out.println("Bank Current Account created with account Number:" + accountnumber);
	}

	@Override
	public Status withdraw(double ammount) throws BankingException {
		if (ammount < 0)
			throw new NegativeAmmountException("ammount to deposit is not correct:" + ammount);
		if (currentBalance - ammount > minimumBalance) {
			synchronized (this) { 
				Transaction t = new Transaction(BankUtils.getInstance().generateUniqueTransactionNum(), ammount,
						TransactionType.withdraw, transactions.size());
				this.transactions.add(t);
				currentBalance -= ammount;
				System.out.println("amount withdrawn:" + ammount + " current Balance:" + this.currentBalance);
				return Status.SUCCESS;

			}
		} else
			throw new NoSufficientAmmountException("sufficient balance is not available, current Balance:"
					+ this.currentBalance + " ,minimum balance required:" + this.minimumBalance);
	}

	@Override
	public String toString() {
		return "CurrentAccount [accountnumber=" + accountnumber + ", minimumBalance=" + minimumBalance
				+ ", currentBalance=" + currentBalance + ", intrestRate=" + intrestRate + ", transactions="
				+ transactions + ", getType()=" + getType() + "]";
	}

	@Override
	public Status deposit(double ammount) throws BankingException {
		if (ammount > 0) {
			synchronized (this) {
				Transaction t = new Transaction(BankUtils.getInstance().generateUniqueTransactionNum(), ammount,
						TransactionType.deposit, transactions.size());
				this.transactions.add(t);
				currentBalance += ammount;
				System.out.println("amount deposited:" + ammount + " current Balance:" + this.currentBalance);
				return Status.SUCCESS;

			}
		} else
			throw new NegativeAmmountException("ammount to deposit is not correct:" + ammount);
	}

	@Override
	public String getType() {

		return "Current";
	}
}
