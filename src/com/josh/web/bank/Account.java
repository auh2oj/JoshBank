package com.josh.web.bank;

import java.util.Date;

public class Account {

	private final String username;

	private final String password;
	
	private final Date dateCreated;

	private double balance = 0;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		this.dateCreated = new Date();
		System.out.println("Successfully created new account.");
		//TODO: Later, include field for date & time account was created
	}
	
	public void deposit(double amount) {
		this.balance += amount;
		System.out.println("New balance: " + balance);
	}
	
	public void withdraw(double amount) {
		if (this.balance < amount || this.balance < 20) {
			System.out.println("Insufficient funds.");
		} else {
			this.balance -= amount;
			System.out.println("New balance: " + balance);
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	protected String getPassword() {
		return password;
	}
}
