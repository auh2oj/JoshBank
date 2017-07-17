package com.josh.web.bank;

public class Account {

	private final String username;

	private final String password;

	private double balance = 0;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		System.out.println("Successfully created new account.");
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
	
	protected String getPassword() {
		return password;
	}
}
