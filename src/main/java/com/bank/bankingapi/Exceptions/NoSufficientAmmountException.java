package com.bank.bankingapi.Exceptions;

public class NoSufficientAmmountException extends BankingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6741489259109367302L;

	public NoSufficientAmmountException(String e) {
		super("Transaction not successful:"+e);
	}
}
