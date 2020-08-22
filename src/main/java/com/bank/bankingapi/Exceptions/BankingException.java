package com.bank.bankingapi.Exceptions;

public class BankingException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2674745528753948096L;

	public BankingException(String e) {
		super("Banking exception:"+e);
	}

}
