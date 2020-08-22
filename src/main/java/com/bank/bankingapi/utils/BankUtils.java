package com.bank.bankingapi.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class BankUtils {
	private static BankUtils _instance;
	AtomicInteger transactionNumber = new AtomicInteger(1);
	AtomicInteger accountNumberNumber = new AtomicInteger(1);
	AtomicInteger branchNumber = new AtomicInteger(1);
	
	
	public static BankUtils getInstance() {
		if (_instance == null) {
			synchronized (BankUtils.class) {
				if (_instance == null) {
					_instance = new BankUtils();
				}
			}
		}
		return _instance;
	}
	public String generateUniqueTransactionNum()
	{
		return Integer.toString(transactionNumber.getAndIncrement());
	}
	public String generateUniqueAccountNum()
	{
		return Integer.toString(accountNumberNumber.getAndIncrement());
	}
	public String generateUniqueBranchNum()
	{
		return Integer.toString(branchNumber.getAndIncrement());
	}

}
