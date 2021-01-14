package com.gr15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetReportSteps {
	private String cid, mid, man;
	private Date date;
	private Date startdate, enddate;
	private Report report = Report.testinstance;
	private Map<UUID, Transaction> result_costumer = new HashMap<UUID, Transaction>();
	private Map<UUID, Transaction> result_merchant = new HashMap<UUID, Transaction>();
	private Map<UUID, Transaction> result_manager = new HashMap<UUID, Transaction>();
	
	@Given("a customer with id {string}")
	public void a_customer_with_id(String cid) {
	    this.cid = cid;
	}
	
	@And("a merchant with id {string}")
	public void a_merchant_with_id(String mid) {
	    this.mid = mid;
	}
	
	@And("a manager with id {string}")
	public void a_manager_with_id(String man) {
	    this.man = man;
	}
	
	@And("an empty transaction database")
	public void an_empty_transaction_database() {
		report.removeAll();
	}
	
	@And("the customer has {int} transactions date {string} to id {string}")
	public void customer_has_transactions_date(Integer n_transactions, String date, String id) throws ParseException {
		
		
		this.date = new SimpleDateFormat( "yyyyMMdd" ).parse(date);
		
		report.addTransaction(new Transaction(this.cid, id, "10", this.date));
		report.addTransaction(new Transaction(this.cid, id, "20", this.date));
		
	}
	
	@And("the merchant has {int} transactions date {string} from id {string}")
	public void merchant_has_transactions_date(Integer n_transactions, String date, String id) throws ParseException {
		
		
		this.date = new SimpleDateFormat( "yyyyMMdd" ).parse(date);
		
		report.addTransaction(new Transaction(id, this.mid, "40", this.date));
		report.addTransaction(new Transaction(id, this.mid, "50", this.date));
		
	}
	
	@And("they all ask to see their transactions between {string} and {string}")
	public void they_asks_to_see_his_transactions_between_and(String startdate, String enddate) throws ParseException {
	    this.startdate = new SimpleDateFormat( "yyyyMMdd" ).parse(startdate);
	    this.enddate = new SimpleDateFormat( "yyyyMMdd" ).parse(enddate);
	}
	
	@When("they request the transaction report")
	public void we_request_the_transaction_report() {
		result_costumer = report.getCustomerTransactions(cid, startdate, enddate);
	    result_merchant = report.getMerchantTransactions(mid, startdate, enddate);
	    result_manager = report.getAllTransactions(man, startdate, enddate);
	}
	
	@Then("the customer receives a hashmap of {int} transactions")
	public void customer_receives_a_hashmap_of_transactions(Integer n_transactions) {
	    assertEquals(n_transactions, result_costumer.size());
	}
	
	@And("the merchant receives a hashmap of {int} transactions")
	public void he_receives_a_hashmap_of_transactions(Integer n_transactions) {
	    assertEquals(n_transactions, result_merchant.size());
	}
	
	@And("the manager receives a hashmap of {int} transactions")
	public void manager_receives_a_hashmap_of_transactions(Integer n_transactions) {
	    assertEquals(n_transactions, result_manager.size());
	}
	
	@And("the client ids are anonymous to the merchant")
	public void the_client_ids_are_anonymous() {
		
		boolean clientDebtorAnonymous = true;
		boolean clientCreditorAnonymous = true;
		
		for (UUID key: result_merchant.keySet()) {
			if (result_merchant.get(key).getCreditor().equals(this.mid)) {
				if (!result_merchant.get(key).getDebtor().equals("anonymous")) {
					clientDebtorAnonymous = false;
				}
			}
			
			if (result_merchant.get(key).getDebtor().equals(this.mid)) {
				if (!result_merchant.get(key).getCreditor().equals("anonymous")) {
					clientCreditorAnonymous = false;
				}
			}
			
		}
		
		assertTrue(clientDebtorAnonymous & clientCreditorAnonymous);
		
	}
	
	
	@Given("the customer asks to see his transactions between {string} and {string}")
	public void customer_asks_to_see_his_transactions_between_and(String startdate, String enddate) throws ParseException {
	    this.startdate = new SimpleDateFormat( "yyyyMMdd" ).parse(startdate);
	    this.enddate = new SimpleDateFormat( "yyyyMMdd" ).parse(enddate);
	}
	
	@When("the customer requests the transaction report")
	public void customer_request_the_transaction_report() {
		result_costumer = report.getCustomerTransactions(cid, startdate, enddate);
	}

}
