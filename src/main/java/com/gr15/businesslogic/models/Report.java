package com.gr15.businesslogic.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Report {
	
	public static Report testinstance = new Report();
	public static Report report = new Report();
	
	private List<Transaction> transactions =new ArrayList<Transaction>();
	
	
	public boolean isCustomer(String customerID) {
		if (customerID.substring(0, 1).equals("0")) {
			return true;
		}
		return false;
	}
	
	public boolean isMerchant(String merchantID) {
		if (merchantID.substring(0, 1).equals("1")) {
			return true;
		}
		return false;
	}
	
	public boolean isManager(String managerID) {
		if (managerID.substring(0, 1).equals("2")) {
			return true;
		}
		return false;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void removeAll() {
		transactions.clear();
	}
	
	public int getDBSize() {
		return transactions.size();
	}
	
	public List<Transaction> getCustomerTransactions(String id, LocalDateTime start, LocalDateTime end) {
		
		List<Transaction> result =new ArrayList<Transaction>();
		
		for (Transaction transaction: transactions) {
			if (transaction.getCustomerId().equals(id)) {
				if (transaction.getTime().isAfter(start)) {
					if (transaction.getTime().isBefore(end)) {
						result.add(transaction);
					}
				}
			}
		}
		
		return result;
	}
	
	public List<Transaction> getMerchantTransactions(String id, LocalDateTime start, LocalDateTime end) {
		
		List<Transaction> result =new ArrayList<Transaction>();
		
		for (Transaction transaction: transactions) {
			if (transaction.getMerchantId().equals(id)) {
				if (transaction.getTime().isAfter(start)) {
					if (transaction.getTime().isBefore(end)) {
						Transaction anonymized = new Transaction(transaction.getId(),
								transaction.getToken(), transaction.getAmount(),
								id, "anonymous", transaction.getDescription(), transaction.getTime());
						result.add(anonymized);
					}
				}
			}
		}
		
		return result;
		
	}
	
	public List<Transaction> getAllTransactions(String id, LocalDateTime start, LocalDateTime end) {
		
		List<Transaction> result =new ArrayList<Transaction>();
		
		for (Transaction transaction: transactions) {
			if (transaction.getTime().isAfter(start)) {
				if (transaction.getTime().isBefore(end)) {
					result.add(transaction);
				}
			}
		}
		
		return result;
	}
	
}
