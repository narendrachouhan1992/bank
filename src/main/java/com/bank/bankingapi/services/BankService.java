package com.bank.bankingapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bank.bankingapi.Entities.Branch;
import com.bank.bankingapi.Entities.HeadOffice;
import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.Status.EntityCreationSatus;
import com.bank.bankingapi.Status.EntityType;
import com.bank.bankingapi.Status.ResponceWrapper;
import com.bank.bankingapi.dal.BankingDAL;
import com.bank.bankingapi.dto.AccountDTO;
import com.bank.bankingapi.dto.TransactionDTO;


@Service
public class BankService {
	HeadOffice ho;

	@Autowired
	BankingDAL dal;
	public EntityCreationSatus createHeadOffice()
	{
		if(ho==null)
		{
			synchronized (this) {
				if(ho==null)
				{
					ho = new HeadOffice("SBI");
					dal.storeHeadOffice(ho);
				}
			}
		}
		
		return EntityCreationSatus.createStatusObject(false, "Head office "+ho.getName()+" already exist", EntityType.HeadOffice);
	}
	public BankService() {
		
	}
	
	public EntityCreationSatus createBranchByName(String branchName)
	{
		if(ho==null)
			return EntityCreationSatus.createStatusObject(false, "head office is not created, create head office first.", EntityType.Branch);
		if(StringUtils.isEmpty(branchName))
			return EntityCreationSatus.createStatusObject(false, "Branch name is empty", EntityType.Branch);
		EntityCreationSatus response =  ho.createBranch(branchName);
		return null;
	}
	
	public EntityCreationSatus createBankAccount(AccountDTO dto)
	{
		if(ho==null)
			return EntityCreationSatus.createStatusObject(false,"head office is not created yet, please create head office first", EntityType.Account);
		if(dto==null)
			return EntityCreationSatus.createStatusObject(false, "account details passed are empty", EntityType.Account);
		try {
			return ho.getBranchByBranchID(dto.getBranchId()).createBankAccount(dto.getPan(), dto.getAccountType(), dto.getInitilAmmount(), dto.getName());
		} catch (Exception e) {
			return EntityCreationSatus.createStatusObject(false, e.getMessage(), EntityType.Account);
		}
	}
	
	public ResponceWrapper<Branch> getAllBranches()
	{
		if(ho==null)
			return new ResponceWrapper<Branch>(false, new ArrayList<Branch>(), "head office is not created yet, please create head office first");
		List<Branch> branches =  ho.getAllBranches();
		if(branches.size()==0)
			return new ResponceWrapper<Branch>(false, branches, "couldn't find any branch in the head office");
		return new ResponceWrapper<Branch>(true, branches,null);
	}

	public ResponceWrapper<Transaction> getStatement(AccountDTO account, boolean isMini) {
		if(account == null)
			return new ResponceWrapper<Transaction>(false, new ArrayList<Transaction>(), "head office is not created yet, please create head office first");
		if(StringUtils.isEmpty(account.getBranchId()))
			return new ResponceWrapper<Transaction>(false, new ArrayList<Transaction>(), "branch id is not populated in the request, please populate branch id");
		if(StringUtils.isEmpty(account.getAccountNumber()))
			return new ResponceWrapper<Transaction>(false, new ArrayList<Transaction>(), "no accpunt nuber is populated in request, please populate account number");
		try {
			if(isMini)
				return new ResponceWrapper<Transaction>(true, ho.getBranchByBranchID(account.getBranchId()).getAccountByAccountNumber(account.getAccountNumber()).getMiniStatement(),"");
			return new ResponceWrapper<Transaction>(true, ho.getBranchByBranchID(account.getBranchId()).getAccountByAccountNumber(account.getAccountNumber()).getTransactionHistory(), "");
		} catch (BankingException e) {
			return new ResponceWrapper<Transaction>(false, new ArrayList<Transaction>(), e.getMessage());
		}
	}
	
	public ResponceWrapper<BankAccount> withdrawAmmount(TransactionDTO dto)
	{
		if(StringUtils.isEmpty(dto.getAccountNumber()))
		{
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), "account number is empty, please pass some value in it");
		}
		if (StringUtils.isEmpty(dto.getBranchNuber())) {
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), "branch id number is empty, please pass some value in it");
		}
		if (dto.getTranAmmount()==0) {
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), "transaction ammount is empty, please pass some value in it");
		}
		List<BankAccount> account = new ArrayList<BankAccount>();
		try {
			account = new ArrayList<BankAccount>();
			account.add(ho.getBranchByBranchID(dto.getBranchNuber()).getAccountByAccountNumber(dto.getAccountNumber()));
			account.get(0).withdraw(dto.getTranAmmount());
		} catch (BankingException e) {
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), e.getMessage());
		}
		
		return new ResponceWrapper<BankAccount>(true,account,"");
	}

	public ResponceWrapper<BankAccount> depositAmmount(TransactionDTO dto)
	{
		if(StringUtils.isEmpty(dto.getAccountNumber()))
		{
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), "account number is empty, please pass some value in it");
		}
		if (StringUtils.isEmpty(dto.getBranchNuber())) {
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), "branch id number is empty, please pass some value in it");
		}
		if (dto.getTranAmmount()==0) {
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), "transaction ammount is empty, please pass some value in it");
		}
		List<BankAccount> account = new ArrayList<BankAccount>();
		try {
			account = new ArrayList<BankAccount>();
			account.add(ho.getBranchByBranchID(dto.getBranchNuber()).getAccountByAccountNumber(dto.getAccountNumber()));
			account.get(0).deposit(dto.getTranAmmount());
		} catch (BankingException e) {
			return new ResponceWrapper<BankAccount>(false, new ArrayList<BankAccount>(), e.getMessage());
		}
		return new ResponceWrapper<BankAccount>(true,account,"");
	}
	
}
