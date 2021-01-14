package com.gr15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.groovy.parser.antlr4.GroovyParser.SuperPrmrAltContext;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetReportSteps {
	private String id;
	private Date date;
	private Date startdate, enddate;
	private Report report = new Report();
	private Map<UUID, Transaction> result = new HashMap();
	
	@Given("a client with id {string}")
	public void a_client_with_id(String cid) {
	    this.id = cid;
	}
	
	@Given("a merchant with id {string}")
	public void a_merchant_with_id(String mid) {
	    this.id = mid;
	}

	@And("the client has {int} transactions date {string} to id {string}")
	public void he_has_transactions_date(Integer n_transactions, String date, String id) throws ParseException {
		report.removeAll();
		
		
		this.date = new SimpleDateFormat( "yyyyMMdd" ).parse(date);
		
		report.addTransaction(new Transaction(this.id, id, "100", this.date));
		report.addTransaction(new Transaction(this.id, id, "200", this.date));
		
	}
	
	@And("the merchant has {int} transactions date {string} from id {string}")
	public void theMerchantHasTransactionsDateFromId(Integer n_transactions, String date, String id) throws ParseException {
		report.removeAll();
		
		this.date = new SimpleDateFormat( "yyyyMMdd" ).parse(date);
		
		report.addTransaction(new Transaction(id, this.id, "100", this.date));
		report.addTransaction(new Transaction(id, this.id, "200", this.date));
		
	}
	
	@And("the merchant has {int} transactions date {string} to id {string}")
	public void theMerchantHasTransactionsDateToId(Integer n_transactions, String date, String id) throws ParseException {
		
		this.date = new SimpleDateFormat( "yyyyMMdd" ).parse(date);
		
		report.addTransaction(new Transaction(this.id, id, "100", this.date));
		
	}
	
	@And("he asks to see his transactions between {string} and {string}")
	public void he_asks_to_see_his_transactions_between_and(String startdate, String enddate) throws ParseException {
	    this.startdate = new SimpleDateFormat( "yyyyMMdd" ).parse(startdate);
	    this.enddate = new SimpleDateFormat( "yyyyMMdd" ).parse(enddate);
	}

	@When("we request the transaction report")
	public void we_request_the_transaction_report() {
	    result = report.getTransactions(id, startdate, enddate);
	}

	@Then("he receives a hashmap of {int} transactions")
	public void he_receives_a_hashmap_of_transactions(Integer n_transactions) {
	    assertEquals(n_transactions, result.size());
	}
	
	@Then("the client ids are anonymous")
	public void the_client_ids_are_anonymous() {
		
		boolean clientDebtorAnonymous = true;
		boolean clientCreditorAnonymous = true;
		
		for (UUID key: result.keySet()) {
			if (result.get(key).getCreditor().equals(this.id)) {
				if (!result.get(key).getDebtor().equals("anonymous")) {
					clientDebtorAnonymous = false;
				}
			}
			
			if (result.get(key).getDebtor().equals(this.id)) {
				if (!result.get(key).getCreditor().equals("anonymous")) {
					clientCreditorAnonymous = false;
				}
			}
			
		}
		
		assertTrue(clientDebtorAnonymous & clientCreditorAnonymous);
		
	}

}
