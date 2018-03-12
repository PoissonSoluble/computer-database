package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long company;

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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate pintroduced) {
		introduced = pintroduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate pDiscontinued) {
		discontinued = pDiscontinued;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long pCompany) {
		company = pCompany;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(id.toString()).append(":").append(name).toString();
	}

}
