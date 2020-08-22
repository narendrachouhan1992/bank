package com.bank.bankingapi.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.bankingapi.pojo.BankAccountPojo;

public interface BankAccountRepository extends CrudRepository<BankAccountPojo, String>{
	public List<BankAccountPojo> findAllByBranchBranchID(String branchId);
}
