package com.josh.web.bank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class BankManager {

	private Map<String, Account> accounts;
	
	protected BankManager() {
        accounts = Collections.synchronizedMap(new HashMap<String, Account>());
		//<for test purposes>
		addAccount("josh", "josh");
		//</for test purposes>
		
		System.out.println("Successfully created new BankManager");
	}
	
	protected final boolean authenticate(String username, String password) {
		return accounts.containsKey(username+","+password);
	}

	protected final Account getAccount(String username, String password) {
		return accounts.get(username+","+password);
	}
	
	protected final void addAccount(String username, String password) {
		accounts.put(username+","+password, new Account(username, password));
	}
	
	final void deposit(Account account, double amount) {
		account.setBalance(account.getBalance() + amount);
		System.out.println("New balance: " + account.getBalance());
	}
	
	final void withdraw(Account account, double amount) {
		double balance = account.getBalance();
		if (balance < amount || balance < 20) {
			System.out.println("Insufficient funds.");
		} else {
			account.setBalance(balance - amount);
			System.out.println("New balance: " + account.getBalance());
		}
	}
}
