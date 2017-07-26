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
        // Modify accounts HashMap so that keys contain accountID's
        // Possibility: keys consist of a string containing:
        // ID, username, SHA256(password) separated by single spaces
        
        // Obvious alternative that doesn't require modification:
        // Keep keys as is; for authentication, search HashMap based on
        // username+SHA256(password). If that combo doesn't exist, immediately reject.
        // Else, retrieve account, get ID, search DB. If result is invalid, reject.

        
		//<for test purposes>
		addAccount("josh", "josh");
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
	
	final void deposit(Integer accountID, double amount) {
//		account.setBalance(account.getBalance() + amount);
//		System.out.println("New balance: " + account.getBalance());

		
		Session session = factory.openSession();
		Transaction tx = null;
		Account account = null;
		try {
			tx = session.beginTransaction();
			account = (Account) session.get(Account.class, accountID);
			account.setBalance(account.getBalance() + amount);
			accounts.put(account.getUsername()+","+account.getPassword(), account);
			tx.commit();
			
			System.out.println("New balance: " + account.getBalance());
			System.out.println("Current ballance: " + accounts.get(account.getUsername()+","+account.getPassword()).getBalance());
		} catch (HibernateException e) {
			if (tx != null) {tx.rollback();}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		
		
		
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
