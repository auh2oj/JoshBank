package com.josh.web.bank;

import java.util.HashMap;
import java.util.Map;

public class BankManager {

	private Map<String, Account> accounts;
	
	public BankManager() {
        accounts = new HashMap<String, Account>();
		//<for test purposes>
		addAccount("josh", "josh");
		//</for test purposes>
		
		System.out.println("Successfully created new BankManager");
	}
	
	protected boolean authenticate(String username, String password) {
		return accounts.containsKey(username+","+password);
	}

	protected Account getAccount(String username, String password) {
		return accounts.get(username+","+password);
	}
	
	protected void addAccount(String username, String password) {
		accounts.put(username+","+password, new Account(username, password));
	}
	
}
