package com.excilys.cdb.model;

import java.util.Date;

public class Computer {
	private Long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	public Computer() {
	}

	public long getId() {
		return id;
	}

	public void setId(long pId) {
		id = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		name = pName;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date pintroduced) {
		introduced = pintroduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date pDiscontinued) {
		discontinued = pDiscontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company pCompany) {
		company = pCompany;
	}

}
