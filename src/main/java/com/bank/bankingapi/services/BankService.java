package com.bank.bankingapi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bank.bankingapi.Entities.Branch;
import com.bank.bankingapi.Entities.Customer;
import com.bank.bankingapi.Entities.HeadOffice;
import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.Status.EntityCreationSatus;
import com.bank.bankingapi.Status.EntityType;
import com.bank.bankingapi.Status.ResponceWrapper;
import com.bank.bankingapi.Status.Status;
import com.bank.bankingapi.dal.BankingDAL;
import com.bank.bankingapi.dto.AccountDTO;
import com.bank.bankingapi.dto.TransactionDTO;
import com.bank.bankingapi.mapping.EntityMapper;
import com.bank.bankingapi.pojo.BankAccountPojo;
import com.bank.bankingapi.pojo.BranchPojo;
import com.bank.bankingapi.pojo.CustomerPojo;


@Service
public class BankService {
	HeadOffice ho;
	@Autowired
	EntityMapper mapper;
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
					return EntityCreationSatus.createStatusObject(true, "Head office "+ho.getName()+" created successfully", EntityType.HeadOffice);
				}
			}
		}
		
		return EntityCreationSatus.createStatusObject(false, "Head office "+ho.getName()+" already exist", EntityType.HeadOffice);
	}
	@PostConstruct
	public void initializeService()
	{
		Map<String, List<Transaction>> transByAccId = new HashMap<String, List<Transaction>>();
		Map<String, List<Branch>> branchesbyHeadOffice = new HashMap<String, List<Branch>>();
		Map<String, List<Customer>> customersByranch = new HashMap<String, List<Customer>>();
		Map<String, List<BankAccount>> accountsByBranch = new HashMap<String, List<BankAccount>>();
		Map<String, List<BankAccount>> accountByCust = new HashMap<String, List<BankAccount>>();
		dal.loadAllTransactionsFromDB().forEach(s->
		{
			System.out.println(s);
			BankAccountPojo acc = s.getAccount();
			CustomerPojo cust = acc.getCustomer();
			BranchPojo branch = acc.getBranch();
			if(transByAccId.containsKey(acc.getAccountnumber()))
			{
				transByAccId.get(acc.getAccountnumber()).add(mapper.getTransactionFromDBObject(s));
			}
			else
			{
				List<Transaction> l = new ArrayList<Transaction>(); 
				l.add(mapper.getTransactionFromDBObject(s));
				transByAccId.put(acc.getAccountnumber(), l);
			}
			BankAccount accEntity = mapper.getBankAccountFromDBObject(acc);
			if(accountByCust.containsKey(cust.getPanNumber()))
			{
				accountByCust.get(cust.getPanNumber()).add(accEntity);
			}
			else
			{
				List<BankAccount> l = new ArrayList<BankAccount>();
				l.add(accEntity);
				accountByCust.put(cust.getPanNumber(), l);
			}
			
			if(accountsByBranch.containsKey(branch.getBranchID()))
			{
				accountsByBranch.get(branch.getBranchID()).add(accEntity);
			}
			else
			{
				List<BankAccount> l = new ArrayList<BankAccount>();
				l.add(accEntity);
				accountsByBranch.put(branch.getBranchID(), l);
			}
			
			if(customersByranch.containsKey(branch.getBranchID()))
			{
				customersByranch.get(branch.getBranchID()).add(mapper.getCustomerFromDBObject(cust));
			}
			else
			{
				List<Customer> l = new ArrayList<Customer>();
				l.add(mapper.getCustomerFromDBObject(cust));
				customersByranch.put(branch.getBranchID(), l);
			}
			

			if(branchesbyHeadOffice.containsKey(branch.getHeadOffice().getName()))
			{
				branchesbyHeadOffice.get(branch.getHeadOffice().getName()).add(mapper.getBranchFromDBObject(branch));
			}
			else
			{
				List<Branch> l = new ArrayList<Branch>();
				l.add(mapper.getBranchFromDBObject(branch));
				branchesbyHeadOffice.put(branch.getHeadOffice().getName(), l);
			}
		});
		if(branchesbyHeadOffice.size()==1)
		{
			String hoName = branchesbyHeadOffice.keySet().iterator().next();
			ho = new HeadOffice(hoName);
			
			accountsByBranch.forEach((k,v)->{
				v.forEach(a->{
					Collections.sort(transByAccId.get(a.getAccountnumber()), (x,y)->x.getSequence()-y.getSequence());
					a.loadTransactions(transByAccId.get(a.getAccountnumber()));
				});
			});
			customersByranch.forEach((k,v)->{
				v.forEach(c->{
					c.loadAccounts(accountByCust.get(c.getPanNumber()));
				});
			});
			branchesbyHeadOffice.forEach((k,v)->{
				v.forEach(b->{
					b.loadBranchFromDB(customersByranch.get(b.getBranchID()), accountsByBranch.get((b.getBranchID())));
				});
				
			});
			ho.LoadBranches(branchesbyHeadOffice.get(hoName));
		}
	}
	public EntityCreationSatus createBranchByName(String branchName)
	{
		if(ho==null)
			return EntityCreationSatus.createStatusObject(false, "head office is not created, create head office first.", EntityType.Branch);
		if(StringUtils.isEmpty(branchName))
			return EntityCreationSatus.createStatusObject(false, "Branch name is empty", EntityType.Branch);
		return ho.createBranch(branchName);
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
	
	public ResponceWrapper<Status> withdrawAmmount(TransactionDTO dto)
	{
		try {
			ho.getBranchByBranchID(dto.getBranchNuber()).getAccountByAccountNumber(dto.getAccountNumber()).withdraw(dto.getTranAmmount());
			return new ResponceWrapper<Status>(true, new ArrayList<Status>(), "ammount withdrawn successfully");
		} catch (BankingException e) {
			return new ResponceWrapper<Status>(false, new ArrayList<Status>(), e.getMessage());
		}
	}

	public ResponceWrapper<Status> depositAmmount(TransactionDTO dto)
	{
		try {
			ho.getBranchByBranchID(dto.getBranchNuber()).getAccountByAccountNumber(dto.getAccountNumber()).deposit(dto.getTranAmmount());
			return new ResponceWrapper<Status>(true, new ArrayList<Status>(), "ammount deposited successfully");
		} catch (BankingException e) {
			return new ResponceWrapper<Status>(false, new ArrayList<Status>(), e.getMessage());
		}
	}
	
}
