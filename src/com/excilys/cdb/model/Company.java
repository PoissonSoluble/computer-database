package com.excilys.cdb.model;

public class Company {
	private Long id;
	private String name;

	public Company() {
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
	
	@Override
	public String toString() {
		return new StringBuilder(id.toString()).append(" - ").append(name).toString();
	}

}
