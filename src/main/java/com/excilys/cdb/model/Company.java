package com.excilys.cdb.model;


public class Company {
	private Long id;
	private String name;

	public Company() {}

	public Company(Builder builder) {
		id = builder.id;
		name = builder.name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static class Builder {
		private Long id;
		private String name;

		public Builder() {}

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

		public Company build() {
			return new Company(this);
		}

	}
}
