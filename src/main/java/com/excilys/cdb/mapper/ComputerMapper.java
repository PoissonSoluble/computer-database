package com.excilys.cdb.mapper;

import java.sql.Date;

import com.excilys.cdb.model.Computer;

public enum ComputerMapper {

	INSTANCE;

	private CompanyMapper companyMapper = CompanyMapper.INSTANCE;

	public Computer createComputer(Long id, String name, Date introduced, Date discontinued, Long companyId,
			String companyName) {
		if (id == null && name == null) {
			return null;
		}
		Computer.Builder computerBuilder = new Computer.Builder().withId(id).withName(name);
		if (introduced != null) {
			computerBuilder.withIntroduced(introduced.toLocalDate());
		}
		if (discontinued != null) {
			computerBuilder.withDiscontinued(discontinued.toLocalDate());
		}
		if(companyId != null) {
			computerBuilder.withCompany(companyMapper.createCompany(companyId, companyName));
		}
		return computerBuilder.build();
	}
}
