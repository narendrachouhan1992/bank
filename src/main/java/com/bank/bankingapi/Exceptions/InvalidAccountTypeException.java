package com.bank.bankingapi.Exceptions;

public class InvalidAccountTypeException extends BankingException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1847527500460748935L;

	public InvalidAccountTypeException(String e) {
		super("Account type is not valid:"+e);
	}

}
