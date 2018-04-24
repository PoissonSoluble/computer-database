package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {
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

        public Computer build() {
            return new Computer(this);
        }

        public Builder withCompany(Company pCompany) {
            company = pCompany;
            return this;
        }

        public Builder withDiscontinued(LocalDate pDiscontinued) {
            discontinued = pDiscontinued;
            return this;
        }

        public Builder withId(Long pId) {
            id = pId;
            return this;
        }

        public Builder withIntroduced(LocalDate pIntroduced) {
            introduced = pIntroduced;
            return this;
        }

        public Builder withName(String pName) {
            name = pName;
            return this;
        }
    }

    private Optional<Long> id;
    private Optional<String> name;
    private Optional<LocalDate> introduced;
    private Optional<LocalDate> discontinued;

    private Optional<Company> company;

    public Computer() {
        id = Optional.empty();
        name = Optional.empty();
        introduced = Optional.empty();
        discontinued = Optional.empty();
        company = Optional.empty();
    }

    public Computer(Builder builder) {
        id = Optional.ofNullable(builder.id);
        name = Optional.ofNullable(builder.name);
        introduced = Optional.ofNullable(builder.introduced);
        discontinued = Optional.ofNullable(builder.discontinued);
        company = Optional.ofNullable(builder.company);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (!company.isPresent()) {
            if (other.company.isPresent()) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (!discontinued.isPresent()) {
            if (other.discontinued.isPresent()) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (!id.isPresent()) {
            if (other.id.isPresent()) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (!introduced.isPresent()) {
            if (other.introduced.isPresent()) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (!name.isPresent()) {
            if (other.name.isPresent()) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public Optional<Company> getCompany() {
        return company;
    }

    public Optional<LocalDate> getDiscontinued() {
        return discontinued;
    }

    public Optional<Long> getId() {
        return id;
    }

    public Optional<LocalDate> getIntroduced() {
        return introduced;
    }

    public Optional<String> getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((!company.isPresent()) ? 0 : company.hashCode());
        result = (prime * result) + ((!discontinued.isPresent()) ? 0 : discontinued.hashCode());
        result = (prime * result) + ((!id.isPresent()) ? 0 : id.hashCode());
        result = (prime * result) + ((!introduced.isPresent()) ? 0 : introduced.hashCode());
        result = (prime * result) + ((!name.isPresent()) ? 0 : name.hashCode());
        return result;
    }

    public void setCompany(Company pCompany) {
        company = Optional.ofNullable(pCompany);
    }

    public void setDiscontinued(LocalDate pDiscontinued) {
        discontinued = Optional.ofNullable(pDiscontinued);
    }

    public void setId(Long pId) {
        id = Optional.ofNullable(pId);
    }

    public void setIntroduced(LocalDate pintroduced) {
        introduced = Optional.ofNullable(pintroduced);
    }

    public void setName(String pName) {
        name = Optional.ofNullable(pName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id.isPresent()) {
            sb.append(id.get());
        }
        if (name.isPresent()) {
            sb.append(" - ").append(name.get());
        }
        if (company.isPresent()) {
            sb.append(" (").append(company.get()).append(")");
        }
        return sb.toString();
    }
}
