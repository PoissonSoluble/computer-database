package com.excilys.cdb.ui;

import java.util.Arrays;
import java.util.List;

enum UserChoice {
	LIST_COMPUTERS("1","list computers"),
	LIST_COMPANIES("2","list companies"),
	DETAIL_COMPUTER("3","detail computer"),
	CREATE_COMPUTER("4","create computer"),
	UPDATE_COMPUTER("5","update computer"),
	REMOVE_COMPUTER("6","remove computer");
	
	private List<String> validChoices;
	private UserChoice(String ... pValidChoices) {
		validChoices = Arrays.asList(pValidChoices);
	}
	
	public boolean accept(String choice) {
		if(validChoices.contains(choice)) {
			return true;
		}
		return false;
	}
	
}
