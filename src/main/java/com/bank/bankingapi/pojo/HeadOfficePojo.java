package com.bank.bankingapi.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HeadOfficePojo {
	@Id
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
