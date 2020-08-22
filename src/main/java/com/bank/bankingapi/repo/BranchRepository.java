package com.bank.bankingapi.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.bankingapi.pojo.BranchPojo;

public interface BranchRepository extends CrudRepository<BranchPojo, String>{

//	public Iterable<BranchPojo> findAllByeadOfficeId(Iterable<String> ids);
	public List<BranchPojo> findAllByHeadOfficeName(String headOffice);
}
