package com.bank.bankingapi.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.bankingapi.pojo.CustomerPojo;

public interface CustomerRepository extends CrudRepository<CustomerPojo, String> {
	public List<CustomerPojo> findAllByBranchBranchID(String branchId);
}
