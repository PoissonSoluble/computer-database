package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company pCompany) {
		company = pCompany;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(id.toString()).append(":").append(name).append("(").append(company).append(")").toString();
	}

}
