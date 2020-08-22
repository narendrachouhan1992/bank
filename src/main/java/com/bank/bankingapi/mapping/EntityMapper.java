package com.bank.bankingapi.mapping;

import org.springframework.stereotype.Service;

import com.bank.bankingapi.Entities.Branch;
import com.bank.bankingapi.Entities.Customer;
import com.bank.bankingapi.Entities.HeadOffice;
import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Entities.accounts.CurrentAccount;
import com.bank.bankingapi.Entities.accounts.SavingAccount;
import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.pojo.BankAccountPojo;
import com.bank.bankingapi.pojo.BankAccountType;
import com.bank.bankingapi.pojo.BranchPojo;
import com.bank.bankingapi.pojo.CustomerPojo;
import com.bank.bankingapi.pojo.HeadOfficePojo;
import com.bank.bankingapi.pojo.TransactionPojo;

@Service
public class EntityMapper {
	public HeadOfficePojo getHeadOfficeDBObj(HeadOffice ho) {
		HeadOfficePojo pojo = new HeadOfficePojo();
		pojo.setName(ho.getName());
		return pojo;
	}

	public BranchPojo getBranchDBObj(Branch branch) {
		BranchPojo pojo = new BranchPojo();
		pojo.setBranchID(branch.getBranchID());
		pojo.setName(branch.getName());
		return pojo;
	}

	public HeadOffice getHeadOfficeFromDBObj(HeadOfficePojo hopojo) {
		return new HeadOffice(hopojo.getName());
	}

	public Branch getBranchFromDBObject(BranchPojo bpojo) {
		return new Branch(bpojo.getBranchID(), bpojo.getName());
	}

	public BankAccount getBankAccountFromDBObject(BankAccountPojo pojo) {
		try 
		{
			if (BankAccountType.SAVING.equals(pojo.getType())) 
			{
				return new SavingAccount(pojo.getAccountnumber(), pojo.getCurrentBalance(), pojo.getHolderName());
			} 
			else 
			{
				return new CurrentAccount(pojo.getAccountnumber(), pojo.getCurrentBalance(), pojo.getHolderName());
			}
		} 
		catch (BankingException e) 
		{
			System.out.println("account number: "+pojo.getAccountnumber()+" couldn't be loaded from db due to exception "+e);
			e.printStackTrace();
			return null;
		}
	}
	public Customer getCustomerFromDBObject(CustomerPojo pojo)
	{
		return new Customer(null, pojo.getPanNumber());
	}
	public Transaction getTransactionFromDBObject(TransactionPojo pojo)
	{
		return new Transaction(pojo.getTransactionId(), pojo.getTransactionAmmount(), pojo.getType());
	}
}
