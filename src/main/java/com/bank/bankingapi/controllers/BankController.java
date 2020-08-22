package com.bank.bankingapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bankingapi.Entities.Branch;
import com.bank.bankingapi.Entities.Transaction;
import com.bank.bankingapi.Entities.accounts.BankAccount;
import com.bank.bankingapi.Status.EntityCreationSatus;
import com.bank.bankingapi.Status.ResponceWrapper;
import com.bank.bankingapi.dto.AccountDTO;
import com.bank.bankingapi.dto.TransactionDTO;
import com.bank.bankingapi.services.BankService;

@RestController
public class BankController {
	@Autowired
	private BankService bankService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/createOffice")
	public EntityCreationSatus createHeadOffice()
	{
		return bankService.createHeadOffice();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/createBranch/{branchName}")
	public EntityCreationSatus createBranchOffice(@PathVariable String branchName )
	{
		return bankService.createBranchByName(branchName);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/createAccount")
	public EntityCreationSatus createBankAccout(@RequestBody AccountDTO account)
	{
		return bankService.createBankAccount(account);
	}
	
	@RequestMapping("/getAllBranches")
	public ResponceWrapper<Branch> getAllBranch()
	{
		return bankService.getAllBranches();
	}
	
	@RequestMapping("/getMiniStatement")
	public ResponceWrapper<Transaction> getMiniStatement(@RequestBody AccountDTO account)
	{
		return bankService.getStatement(account, true);
	}
	@RequestMapping("/getTransactionHistory")
	public ResponceWrapper<Transaction> getTransactionHistory(@RequestBody AccountDTO account)
	{
		return bankService.getStatement(account, false);
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/withdrawAmmount")
	public ResponceWrapper<BankAccount> withdrawAmmount(@RequestBody TransactionDTO dto)
	{
		return bankService.withdrawAmmount(dto);
	}
	@RequestMapping(method = RequestMethod.POST,value="/depositAmmount")
	public ResponceWrapper<BankAccount> depositAmmount(@RequestBody TransactionDTO dto)
	{
		return bankService.depositAmmount(dto);
	}
}
