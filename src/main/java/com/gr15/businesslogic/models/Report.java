package com.gr15.businesslogic.models;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class Report {
	public static Report reportinstance = new Report();
	public static Report testinstance = new Report();
	
	
	private Map<String, Transaction> transactions = new HashMap<String, Transaction>();
	
	
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
		transactions.put(transaction.getId(), transaction);
	}
	
	public void removeAll() {
		transactions.clear();
	}
	
	
	
	
	public Map<String, Transaction> getCustomerTransactions(String id, LocalDateTime start, LocalDateTime end) {
		Map<String, Transaction> result = 	transactions.entrySet()
				.stream()
				.filter(map -> (map.getValue().getCustomerId().equals(id)))
				.filter(map -> (map.getValue().getTime().isAfter(start) &  map.getValue().getTime().isBefore(end)))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		
		return result;
	}
	
	public Map<String, Transaction> getMerchantTransactions(String id, LocalDateTime start, LocalDateTime end) {
		Map<String, Transaction> result = transactions.entrySet()
				.stream()
				.filter(map -> (map.getValue().getMerchantId().equals(id)))
				.filter(map -> (map.getValue().getTime().isAfter(start) &  map.getValue().getTime().isBefore(end)))
				.collect(Collectors.toMap(map -> map.getKey(), map ->
				new Transaction(map.getValue().getId(),map.getValue().getToken() , map.getValue().getAmount(),
						map.getValue().getMerchantId(), "anonymous", map.getValue().getDescription(),
						map.getValue().getTime())));
		
//		Map<String, Transaction> mid_debtor = transactions.entrySet()
//				.stream()
//				.filter(map -> (map.getValue().getDebtor().equals(id)))
//				.filter(map -> (map.getValue().getDate().after(start) &  map.getValue().getDate().before(end)))
//				.collect(Collectors.toMap(map -> map.getKey(), map ->
//				new Transaction(map.getValue().getDebtor(), "anonymous", map.getValue().getAmount(), map.getValue().getDate())));
		
//		Map<String, Transaction> result = new HashMap<UUID, Transaction>();
//		result.putAll(mid_debtor);
//		result.putAll(mid_creditor);
		return result;
		
	}
	
	public Map<String, Transaction> getAllTransactions(String id, LocalDateTime dBegin, LocalDateTime dEnd) {
		return transactions;
	}
	
	
//	public Map<UUID, Transaction> getMerchantTransactions(String id, Date start, Date end) {
//		if (isManager(id)) {
//			return transactions;
//		}
//		
//		if (isMerchant(id)) {
//			Map<UUID, Transaction> mid_creditor = 	transactions.entrySet()
//					.stream()
//					.filter(map -> (map.getValue().getCreditor().equals(id)))
//					.filter(map -> (map.getValue().getDate().after(start) &  map.getValue().getDate().before(end)))
//					.collect(Collectors.toMap(map -> map.getKey(), map ->
//					new Transaction("anonymous", map.getValue().getCreditor(), map.getValue().getAmount(), map.getValue().getDate())));
//			
//			Map<UUID, Transaction> mid_debtor = 	transactions.entrySet()
//					.stream()
//					.filter(map -> (map.getValue().getDebtor().equals(id)))
//					.filter(map -> (map.getValue().getDate().after(start) &  map.getValue().getDate().before(end)))
//					.collect(Collectors.toMap(map -> map.getKey(), map ->
//					new Transaction(map.getValue().getDebtor(), "anonymous", map.getValue().getAmount(), map.getValue().getDate())));
//			
//			Map<UUID, Transaction> result = new HashMap<UUID, Transaction>();
//			result.putAll(mid_debtor);
//			result.putAll(mid_creditor);
//			return result;
//		}
//		
//		if (isCustomer(id)) {
//			Map<UUID, Transaction> result = 	transactions.entrySet()
//					.stream()
//					.filter(map -> (map.getValue().getDebtor().equals(id) ||  map.getValue().getCreditor().equals(id)))
//					.filter(map -> (map.getValue().getDate().after(start) &  map.getValue().getDate().before(end)))
//					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
//			
//			return result;
//		}
//		
//		return null;
//	}
	
	
}
