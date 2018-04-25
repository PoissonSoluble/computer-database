package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cu_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "cu_name")
    private String name;

    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "cu_introduced")
    private LocalDate introduced;

    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "cu_discontinued")
    private LocalDate discontinued;

    @ManyToOne(optional = true)
    @JoinColumn(name = "ca_id")
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

    public Computer(String pName, LocalDate pIntroduced, LocalDate pDiscontinued, Company pCompany) {
        name = pName;
        introduced = pIntroduced;
        discontinued = pDiscontinued;
        company = pCompany;
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

    public void setCompany(Company pCompany) {
        company = pCompany;
    }

    public void setDiscontinued(LocalDate pDiscontinued) {
        discontinued = pDiscontinued;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public void setIntroduced(LocalDate pIntroduced) {
        introduced = pIntroduced;
    }

    public void setName(String pName) {
        name = pName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
        }
        if (name != null) {
            sb.append(" - ").append(name);
        }
        if (company != null) {
            sb.append(" (").append(company).append(")");
        }
        return sb.toString();
    }
}
