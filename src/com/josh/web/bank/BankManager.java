package com.josh.web.bank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public final class BankManager {

	private Map<String, Account> accounts;
	private SessionFactory factory;
	
	protected BankManager(SessionFactory factory) {
		this.factory = factory;
		
        accounts = Collections.synchronizedMap(new HashMap<String, Account>());
        //TODO: Load all accounts from database
        
		//<for test purposes>
		addAccountTest("josh", "josh");
		//</for test purposes>
		
		System.out.println("Successfully created new BankManager.");
	}
	
	protected final boolean authenticate(String username, String password) {
		return accounts.containsKey(username+","+password);
	}

	protected final Account getAccount(String username, String password) {
		return accounts.get(username+","+password);
	}
	
	protected final void addAccountTest(String username, String password) {
		accounts.put(username+","+password, new Account(username, password));
	}
	
	final Integer addAccount(String username, String password) {
		String key = username+","+password;
		Account newAccount = new Account(username, password);
		accounts.put(key, newAccount);
		
		Session session = factory.openSession();
		Transaction tx = null;
		Integer accountID = null;
		try {
			tx = session.beginTransaction();
			accountID = (Integer) session.save(newAccount);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return accountID;
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
	
	final void deleteAccount(String username, String password) {
		Account removedAccount = accounts.remove(username+","+password);
		if (removedAccount == null) {
			System.out.println("Error removing account. Please try again.");
		}
	}
}
