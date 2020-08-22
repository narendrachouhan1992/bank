package com.bank.bankingapi.Exceptions;

public class NegativeAmmountException extends BankingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6316649205755114307L;

	public NegativeAmmountException(String e) {
		super("Ammount is not correct:"+e);
	}
}
