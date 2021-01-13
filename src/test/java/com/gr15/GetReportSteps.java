package com.gr15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.gr15.Report;
import com.gr15.Transaction;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetReportSteps {
	private String cid;
	private Date startdate, enddate;
	private Report report;
	private Map<UUID, Transaction> result;
	
	@Given("a client with id {string}")
	public void a_client_with_id(String cid) {
	    this.cid = cid;
	}

	@And("he has {int} transactions date {string}")
	public void he_has_transactions_date(Integer n_transactions, String date) throws ParseException {
		report.removeAll();
		
		Date date1 = new SimpleDateFormat( "yyyyMMdd" ).parse(date);
		
		report.addTransaction(new Transaction(this.cid, "10", "100", date1));
		report.addTransaction(new Transaction(this.cid, "20", "200", date1));
	}

	@And("he asks to see his transactions between {string} and {string}")
	public void he_asks_to_see_his_transactions_between_and(String startdate, String enddate) throws ParseException {
	    this.startdate = new SimpleDateFormat( "yyyyMMdd" ).parse(startdate);
	    this.enddate = new SimpleDateFormat( "yyyyMMdd" ).parse(enddate);
	}

	@When("we request the transaction report")
	public void we_request_the_transaction_report() {
	    result = report.getTransactions(cid, startdate, enddate);
	}

	@Then("he receives a hashmap of {int} transactions")
	public void he_receives_a_hashmap_of_transactions(Integer n_transactions) {
	    assertEquals(n_transactions, result.size());
	}

}
