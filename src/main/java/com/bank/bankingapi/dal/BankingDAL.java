package com.bank.bankingapi.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.bankingapi.Entities.Branch;
import com.bank.bankingapi.Entities.Customer;
import com.bank.bankingapi.Entities.HeadOffice;
import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Status.Status;
import com.bank.bankingapi.mapping.EntityMapper;
import com.bank.bankingapi.pojo.BankAccountPojo;
import com.bank.bankingapi.pojo.BranchPojo;
import com.bank.bankingapi.pojo.HeadOfficePojo;
import com.bank.bankingapi.repo.BankAccountRepository;
import com.bank.bankingapi.repo.BranchRepository;
import com.bank.bankingapi.repo.CustomerRepository;
import com.bank.bankingapi.repo.HeadOfficeRepository;
import com.bank.bankingapi.repo.TransactionRepository;

@Service
public class BankingDAL {
	@Autowired
	private BranchRepository branchRepo;
	@Autowired
	private BankAccountRepository bankAccountRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private HeadOfficeRepository headOfficeRepo;
	@Autowired
	private TransactionRepository transactionRepo;
	@Autowired
	private EntityMapper mapper;
	
	
	public Status storeHeadOffice(HeadOffice ho)
	{
		HeadOfficePojo pojo = mapper.getHeadOfficeDBObj(ho);
		headOfficeRepo.save(pojo);
		return Status.SUCCESS;
	}
	public Status storeBranch(Branch branch, String headOfficeName)
	{
		BranchPojo pojo = mapper.getBranchDBObj(branch);
		Optional<HeadOfficePojo> hop = headOfficeRepo.findById(headOfficeName);
		pojo.setHeadOffice(hop.get());
		branchRepo.save(pojo);
		return Status.SUCCESS;
	}
	public HeadOffice loadHeadOfficeFromDB()
	{
		Iterator<HeadOfficePojo> office;
		List<Transaction> trans = new ArrayList<Transaction>();
		Map<String, BankAccount> accounts = new HashMap<String, BankAccount>();
		trans.forEach(s->{
			
		});
		
		HeadOffice ho;
		if(office.hasNext())
		{
			ho = mapper.getHeadOfficeFromDBObj(office.next());
			List<Branch> branches = branchRepo.findAllByHeadOfficeName(ho.getName()).stream().map(b->mapper.getBranchFromDBObject(b)).collect(Collectors.toList());
			for (Branch branch : branches) {
				List<BankAccountPojo> pojos =  bankAccountRepo.findAllByBranchBranchID(branch.getBranchID());
				Map<String,BankAccount> accounts = pojos.stream().map(s->getAccountObject(s)).collect(Collectors.toMap(e->e.getAccountnumber(), e->e));
				Map<String, Customer> customers = customerRepo.findAllByBranchBranchID(branch.getBranchID()).stream().map(s->mapper.getCustomerFromDBObject(s)).collect(Collectors.toMap(s->s.getPanNumber(), s->s));
				
			}
			
		}
		return null;
	}
	private BankAccount getAccountObject(BankAccountPojo pojo)
	{
		BankAccount acc = mapper.getBankAccountFromDBObject(pojo);
		acc.loadTransactions(transactionRepo.findAllByAccountAccountNumber(pojo.getAccountnumber()).parallelStream().map(s->mapper.getTransactionFromDBObject(s)).collect(Collectors.toList()));
		return acc;
	}
}
