package com.gr15;

import java.util.Date;

public class Transaction {
	private String debtor;
	private String creditor;
	private String amount;
	private String date;
	
	public Transaction() {
		
	}
	
	public Transaction(String debtor, String creditor, String amount, String date) {
		this.debtor = debtor;
		this.creditor = creditor;
		this.amount = amount;
		this.date = date;
	}
	
	public String getDebtor() {
		return debtor;
	}
	
	public void setDebtor(String debtor) {
		this.debtor = debtor;
	}
	
	public String getCreditor() {
		return creditor;
	}
	
	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

}
