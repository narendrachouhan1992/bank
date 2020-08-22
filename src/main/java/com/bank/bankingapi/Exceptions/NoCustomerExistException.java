package com.bank.bankingapi.Exceptions;

public class NoCustomerExistException extends BankingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1021014721837818308L;

	public NoCustomerExistException(String e) {
		
		super("No customer exist with panNumber exception:"+e);
	}
}
