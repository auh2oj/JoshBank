package com.josh.web.bank;

import java.util.Date;

public class Account {

	private String username;

	private String password;
	
	private Date dateCreated;

	private double balance = 0;
	
	public Account() {}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		this.dateCreated = new Date();
		System.out.println("Successfully created new account.");
		//TODO: Later, include field for date & time account was created
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
