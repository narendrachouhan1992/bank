package com.bank.bankingapi.Entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bank.bankingapi.Exceptions.BankingException;
import com.bank.bankingapi.Exceptions.NoBranchExistException;
import com.bank.bankingapi.Status.EntityCreationSatus;
import com.bank.bankingapi.Status.EntityType;
import com.bank.bankingapi.utils.BankUtils;

public class HeadOffice {
	Map<String , Branch> branches;
	String name;
	public String getName() {
		return name;
	}
	public List<Branch> getAllBranches() {
		return branches.values().stream().collect(Collectors.toList());
	}
	public Branch getBranchByBranchID(String id) throws BankingException
	{
		if(branches.containsKey(id))
			return branches.get(id);
		throw new NoBranchExistException("No branch found with ID:"+id);
	}
	public void LoadBranches(List<Branch> branches)
	{
		for (Branch branch : branches) {
			this.branches.put(branch.getBranchID(), branch);
		}
	}
	public EntityCreationSatus createBranch(String name)
	{
		String branchID = BankUtils.getInstance().generateUniqueBranchNum();
		Branch b = new Branch(branchID, name);
		branches.put(b.getBranchID(), b);
		System.out.println("branch created with ID:"+branchID);
		return EntityCreationSatus.createStatusObject(true, "branch created with ID:"+branchID+ " and name:"+name, EntityType.Branch);
	}
	public HeadOffice(String name) {
		branches = new HashMap<String, Branch>();
		this.name = name;
	}
	@Override
	public String toString() {
		return "HeadOffice [branches=" + branches + "]";
	}
}
