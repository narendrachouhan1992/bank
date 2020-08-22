package com.bank.bankingapi.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class CustomerPojo {
	@Id
	private String panNumber;
	@ManyToOne
	private BranchPojo branch;
	public BranchPojo getBranch() {
		return branch;
	}
	public void setBranch(BranchPojo branch) {
		this.branch = branch;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
}
