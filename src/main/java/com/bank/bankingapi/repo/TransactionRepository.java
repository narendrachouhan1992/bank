package com.bank.bankingapi.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.bankingapi.pojo.TransactionPojo;

public interface TransactionRepository extends CrudRepository<TransactionPojo, String>{
	public List<TransactionPojo> findAllByAccountAccountNumber(String accountAccountNumber);
}
