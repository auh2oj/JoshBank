package com.josh.web.bank;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public final class BankManager {

	private Map<String, Account> accounts;
	private SessionFactory factory;
	
	private EntityManager em;
	
	protected BankManager(SessionFactory factory) {
		this.factory = factory;
		//this.em = em;

		
        accounts = Collections.synchronizedMap(new HashMap<String, Account>());
        //TODO: Load all accounts from database
        // Modify accounts HashMap so that keys contain accountID's
        // Possibility: keys consist of a string containing:
        // ID, username, SHA256(password) separated by single spaces
        
        // Obvious alternative that doesn't require modification:
        // Keep keys as is; for authentication, search HashMap based on
        // username+SHA256(password). If that combo doesn't exist, immediately reject.
        // Else, retrieve account, get ID, search DB. If result is invalid, reject.

        // TODO: Add transaction logger that logs different transactions to a separate DB
        // Rough model:
        /* Create Table Transactions (
        			TransactionID int not null auto_increment primary key,
        			AccountID int not null foreign key references Accounts(ID),
        			TransactionType char(20) not null,
        			TransactionAmount double
        				);
        	*/
        
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
	
	protected final boolean accountExists(String username) {
		//TODO: Convert to HQL
		Session session = factory.openSession();
		Account account = null;
		
		Criteria c = session.createCriteria(Account.class);
		c.add(Restrictions.eq("username", username));
		return c.list().size() > 0;
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
	
	final Account deposit(Integer accountID, double amount) {
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
			session.update(account);
			tx.commit();
			
			System.out.println("New balance: " + account.getBalance());
			System.out.println("Current ballance: " + accounts.get(account.getUsername()+","+account.getPassword()).getBalance());
		} catch (HibernateException e) {
			if (tx != null) {tx.rollback();}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return account;
		
		
	}
	
	final Account withdraw(Integer accountID, double amount) {
//		double balance = account.getBalance();
//		if (balance < amount || balance < 20) {
//			System.out.println("Insufficient funds.");
//		} else {
//			account.setBalance(balance - amount);
//			System.out.println("New balance: " + account.getBalance());
//		}
		
		Session session = factory.openSession();
		Transaction tx = null;
		Account account = null;
		try {
			tx = session.beginTransaction();
			account = (Account) session.get(Account.class, accountID);
			account.setBalance(account.getBalance() - amount);
			accounts.put(account.getUsername()+","+account.getPassword(), account);
			session.update(account);
			tx.commit();
			
			System.out.println("New balance: " + account.getBalance());
			System.out.println("Current ballance: " + accounts.get(account.getUsername()+","+account.getPassword()).getBalance());
		} catch (HibernateException e) {
			if (tx != null) {tx.rollback();}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return account;
		
		
	}
	
	final void deleteAccount(String username, String password) {
		Account removedAccount = accounts.remove(username+","+password);
		if (removedAccount == null) {
			System.out.println("Error removing account. Please try again.");
		}
	}
	
	// TODO: Add level-2 cache for application
	
	// TODO: For version 2.0: Add admin feature that lists all account info
	// For this feature, use query-level cache, and set a cache region for accounts
}
