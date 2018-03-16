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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Company getCompany() {
		return company;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public void setCompany(Company pCompany) {
		company = pCompany;
	}

	public void setDiscontinued(LocalDate pDiscontinued) {
		discontinued = pDiscontinued;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public void setIntroduced(LocalDate pintroduced) {
		introduced = pintroduced;
	}

	public void setName(String pName) {
		name = pName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(id != null) {
			sb.append(id.toString());
		}
		sb.append(" - ").append(name);
		if(company != null && company.getId() != null && company.getId() != 0) {
			sb.append(" (").append(company).append(")");
		}
		return sb.toString();
	}

}
