package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer() {
	}

	public Computer(Builder builder) {
		id = builder.id;
		name = builder.name;
		introduced = builder.introduced;
		discontinued = builder.discontinued;
		company = builder.company;
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
		if (Optional.ofNullable(company).isPresent()) {
			if (Optional.ofNullable(other.company).isPresent())
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (!Optional.ofNullable(discontinued).isPresent()) {
			if (Optional.ofNullable(other.discontinued).isPresent())
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (!Optional.ofNullable(id).isPresent()) {
			if (Optional.ofNullable(other.id).isPresent())
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Optional.ofNullable(introduced).isPresent()) {
			if (Optional.ofNullable(other.introduced).isPresent())
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (!Optional.ofNullable(name).isPresent()) {
			if (Optional.ofNullable(other.name).isPresent())
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}

	public Optional<LocalDate> getDiscontinued() {
		return Optional.ofNullable(discontinued);
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}

	public Optional<LocalDate> getIntroduced() {
		return Optional.ofNullable(introduced);
	}

	public Optional<String> getName() {
		return Optional.ofNullable(name);
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
		if (Optional.ofNullable(id).isPresent()) {
			sb.append(id.toString());
		}
		if (Optional.ofNullable(name).isPresent()) {
			sb.append(" - ").append(name);
		}
		if (Optional.ofNullable(company).isPresent()) {
			sb.append(" (").append(company).append(")");
		}
		return sb.toString();
	}

	public static class Builder {
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;

		public Builder() {
		}

		public Builder(Long pId) {
			id = pId;
		}

		public Builder(String pName) {
			name = pName;
		}

		public Builder withId(Long pId) {
			id = pId;
			return this;
		}

		public Builder withName(String pName) {
			name = pName;
			return this;
		}

		public Builder withIntroduced(LocalDate pIntroduced) {
			introduced = pIntroduced;
			return this;
		}

		public Builder withDiscontinued(LocalDate pDiscontinued) {
			discontinued = pDiscontinued;
			return this;
		}

		public Builder withCompany(Company pCompany) {
			company = pCompany;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}
}
