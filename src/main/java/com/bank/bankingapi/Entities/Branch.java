package com.bank.bankingapi.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Entities.accounts.CurrentAccount;
import com.bank.bankingapi.Entities.accounts.SavingAccount;
import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.Exceptions.InvalidAccountTypeException;
import com.bank.bankingapi.Exceptions.NoBankAccountExistException;
import com.bank.bankingapi.Status.EntityCreationSatus;
import com.bank.bankingapi.Status.EntityType;
import com.bank.bankingapi.utils.BankUtils;

public class Branch {
	private Map<String,Customer> customers;
	private Map<String, BankAccount> accounts;
	private String branchID;
	private String name;
	@Override
	public String toString() {
		return "Branch [customers=" + customers + ", accounts=" + accounts + ", branchID=" + branchID + "]";
	}
	public Customer getCustomerByPanNumber(String pan) throws NoBankAccountExistException{
		if(!customers.containsKey(pan))
			throw new NoBankAccountExistException("Customer does not exist with PAN:"+pan);
		return customers.get(pan);
	}
	public BankAccount getAccountByAccountNumber(String an) throws BankingException {
		if(!accounts.containsKey(an))
			throw new NoBankAccountExistException("bankAccount does not exist with id:"+an);
		return accounts.get(an);
	}
	public String getBranchID() {
		return branchID;
	}
	public EntityCreationSatus createBankAccount(String pan, String type, double ammount, String holderName) throws BankingException
	{
		String accountID;
		StringBuffer message=new StringBuffer("");
		if("Saving".equalsIgnoreCase(type))
		{
			accountID = BankUtils.getInstance().generateUniqueAccountNum();
			if(customers.containsKey(pan))
			{
				BankAccount acc = new SavingAccount(accountID, ammount, holderName);
				customers.get(pan).addBankAccount(acc);
				accounts.put(acc.getAccountnumber(), acc);
			}
			else
			{
				List<BankAccount> bal = new ArrayList<BankAccount>();
				BankAccount acc = new SavingAccount(accountID, ammount, holderName);
				bal.add(acc);
				Customer c = new Customer(bal, pan);
				accounts.put(acc.getAccountnumber(), acc);
				customers.put(pan, c);
				message.append("customer created with PAN number :"+pan+". ");
			}
		}
		else if ("Current".equalsIgnoreCase(type))
		{
			accountID = BankUtils.getInstance().generateUniqueAccountNum();
			if(customers.containsKey(pan))
			{
				
				BankAccount acc = new CurrentAccount(accountID, ammount, holderName);
				customers.get(pan).addBankAccount(acc);
				accounts.put(acc.getAccountnumber(), acc);
			}
			else
			{
				List<BankAccount> bal = new ArrayList<BankAccount>();
				BankAccount acc = new CurrentAccount(accountID, ammount, holderName);
				bal.add(acc);
				Customer c = new Customer(bal, pan);
				accounts.put(acc.getAccountnumber(), acc);
				customers.put(pan, c);
				message.append("customer created with PAN number :"+pan+". ");
			}
		}
		else
		{
			throw new InvalidAccountTypeException("Account type passed is not valid :"+type);
		}
		message.append("bank account created with accountID:"+accountID);
		return EntityCreationSatus.createStatusObject(true, message.toString(), EntityType.Account);
	}
	public void loadBranchFromDB(List<Customer> c, List<BankAccount> ba)
	{
		c.forEach(s->{
			customers.put(s.getPanNumber(), s);
		});
		ba.forEach(s->{
			accounts.put(s.getAccountnumber(), s);
		});
	}
	
	public Branch(String branchID, String name) {
		super();
		this.customers = new HashMap<String ,Customer>();
		this.accounts = new HashMap<String ,BankAccount>();
		this.branchID = branchID;
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
