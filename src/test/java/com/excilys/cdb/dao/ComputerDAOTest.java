package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;

import org.junit.Before;

import org.junit.Test;

import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Matchers.*;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

//@RunWith(MockitoJUnitRunner.class)
public class ComputerDAOTest {

/*
	@Mock
	private DatabaseConnection databaseConnection;

	@Mock
	private Connection sqlConnection;

	@Mock
	private PreparedStatement stmt;

	@Mock
	private ResultSet rs;

	private Computer computer;
	private Company company;
	@Before
	public void setUp() throws Exception {

		assertNotNull(databaseConnection);

		when(sqlConnection.prepareStatement(any(String.class))).thenReturn(stmt);

		when(databaseConnection.getConnection()).thenReturn(sqlConnection);

		company = new Company.Builder(1L).withName("Test company").build();
		computer = new Computer.Builder(1L).withName("Test").withIntroduced(LocalDate.of(2000, 01, 01))
				.withDiscontinued(LocalDate.of(2010, 01, 01)).build();

		when(rs.first()).thenReturn(true);
		when(rs.getLong(1)).thenReturn(1L);
		when(rs.getString(2)).thenReturn(computer.getName().get());
		when(rs.getDate(3)).thenReturn(Date.valueOf(computer.getIntroduced().get()));
		when(rs.getDate(4)).thenReturn(Date.valueOf(computer.getDiscontinued().get()));
		when(rs.getLong(5)).thenReturn(1L);
		when(rs.getString(6)).thenReturn(company.getName());
		when(stmt.executeQuery()).thenReturn(rs);

	}

	@Test(expected = IllegalArgumentException.class)
	public void nullCreateThrowsException() {

		ComputerDAO.INSTANCE.createComputer(null);

	}

	@Test
	public void createComputer() {
		ComputerDAO.INSTANCE.createComputer(computer);
	}

	@Test

	public void createAndRetrievePerson() throws Exception {
		ComputerDAO.INSTANCE.createComputer(computer);

		Computer result = ComputerDAO.INSTANCE.getComputer(1L);

		assertEquals(computer, result);

	}
*/
}
