package com.gr15;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Report {
	public static Report reportinstance = new Report();
	
	private Map<Integer, Transaction> transactions = new HashMap<Integer, Transaction>();
	
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
		Integer transactionID = transactions.size() + 1;
		transactions.put(transactionID, transaction);
	}
	
	public Map<Integer, Transaction> getTransactions(String ID) {
		
		
		if (isManager(ID)) {
			return transactions;
		}
		
		if (isCustomer(ID)) {
			Map<Integer, Transaction> result = 	transactions.entrySet()
					.stream()
					.filter(map -> (map.getValue().getDebtor().equals(ID) ||  map.getValue().getCreditor().equals(ID)))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
			return result;
		}
		
		if (isMerchant(ID)) {
			Map<Integer, Transaction> result = 	transactions.entrySet()
					.stream()
					.filter(map -> (map.getValue().getDebtor().equals(ID) ||  map.getValue().getCreditor().equals(ID)))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
			return result;
		}
		
		return null;
	}
	

}
