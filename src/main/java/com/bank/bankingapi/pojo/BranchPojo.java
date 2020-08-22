package com.bank.bankingapi.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BranchPojo {
	@Id
	private String branchID;
	private String name;
	@ManyToOne
	private HeadOfficePojo headOffice;
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HeadOfficePojo getHeadOffice() {
		return headOffice;
	}
	public void setHeadOffice(HeadOfficePojo headOffice) {
		this.headOffice = headOffice;
	}
}
