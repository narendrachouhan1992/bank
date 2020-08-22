package com.bank.bankingapi.Exceptions;

public class NoBranchExistException extends BankingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6197403718087167632L;

	public NoBranchExistException(String e) {
		
		super("No Branch exist with ID exception:"+e);
	}
}
