package com.bank.bankingapi.Exceptions;

public class NoBankAccountExistException extends BankingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5569213140393533363L;

	public NoBankAccountExistException(String e) {
		
		super("No Account exist with accountID exception:"+e);
	}
}
