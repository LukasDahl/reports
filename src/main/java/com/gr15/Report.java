package com.gr15;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Report {
	public static Report reportinstance = new Report();
	
	
	private Map<UUID, Transaction> transactions = new HashMap<UUID, Transaction>();
	
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
		transactions.put(UUID.randomUUID(), transaction);
	}
	
	public void removeAll() {
		transactions.clear();
	}
	
	public Map<UUID, Transaction> getTransactions(String id, Date start, Date end) {
		
		
		if (isManager(id)) {
			return transactions;
		}
		
		if (isCustomer(id)) {
			Map<UUID, Transaction> result = 	transactions.entrySet()
					.stream()
					.filter(map -> (map.getValue().getDebtor().equals(id) ||  map.getValue().getCreditor().equals(id)))
					.filter(map -> (map.getValue().getDate().after(start) &  map.getValue().getDate().before(end)))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
			return result;
		}
		
		if (isMerchant(id)) {
			Map<UUID, Transaction> result = 	transactions.entrySet()
					.stream()
					.filter(map -> (map.getValue().getDebtor().equals(id) ||  map.getValue().getCreditor().equals(id)))
					.filter(map -> (map.getValue().getDate().after(start) &  map.getValue().getDate().before(end)))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
			return result;
		}
		
		return null;
	}
	
	public String check (String id, Date start, Date end) {
		return id + " " + start.toString() + " " + end.toString();
	}
	

}
