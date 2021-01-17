package com.gr15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.gr15.businesslogic.models.Report;
import com.gr15.businesslogic.models.Transaction;
import com.gr15.messaging.interfaces.IEventReceiver;
import com.gr15.messaging.interfaces.IEventSender;
import com.gr15.messaging.models.Event;
import com.gr15.messaging.rabbitmq.RabbitMqListener;
import com.gr15.messaging.rabbitmq.RabbitMqSender;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetReportSteps implements IEventReceiver {
	private String cid, mid, man;
	private LocalDateTime date;
	private LocalDateTime startdate, enddate;
	private Report report = Report.testinstance;
	
	private Map<String, Transaction> result_costumer = new HashMap<String, Transaction>();
	private Map<String, Transaction> result_merchant = new HashMap<String, Transaction>();
	private Map<String, Transaction> result_manager = new HashMap<String, Transaction>();
	
	IEventSender eventSender = new RabbitMqSender("localhost");
	private int sizebefore;
	
	private static final String QUEUE_TYPE = "topic";
	private static final String EXCHANGE_NAME = "paymentsExchange";
	private static final String PAYMENT_EVENT_BASE = "payment.events.";
	private static final String TRANSACTION_CREATED_EVENT = "transactionCreated";
	
	
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
		
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate temp = LocalDate.parse(date, formatter);
		this.date = temp.atStartOfDay();
		
		report.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), id, "01", "description", this.date));
		
		report.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(200), id, "01", "description", this.date));
		
	}
	
	@And("the merchant has {int} transactions date {string} from id {string}")
	public void merchant_has_transactions_date(Integer n_transactions, String date, String id) throws ParseException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate temp = LocalDate.parse(date, formatter);
		this.date = temp.atStartOfDay();
		
		report.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(10), this.mid, id, "description", this.date));
		
		report.addTransaction(new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(20), this.mid, id, "description", this.date));
		
	}
	
	@And("they all ask to see their transactions between {string} and {string}")
	public void they_asks_to_see_his_transactions_between_and(String startdate, String enddate) throws ParseException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate tempstart = LocalDate.parse(startdate, formatter);
		this.startdate = tempstart.atStartOfDay();
		
		LocalDate tempend = LocalDate.parse(enddate, formatter);
		this.enddate = tempend.atStartOfDay();
		
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
		
		for (String key: result_merchant.keySet()) {
			if (result_merchant.get(key).getMerchantId().equals(this.mid)) {
				if (!result_merchant.get(key).getCustomerId().equals("anonymous")) {
					clientDebtorAnonymous = false;
				}
			}
		}
		
		assertTrue(clientDebtorAnonymous & clientCreditorAnonymous);
		
	}
	
	
	@Given("the customer asks to see his transactions between {string} and {string}")
	public void customer_asks_to_see_his_transactions_between_and(String startdate, String enddate) throws ParseException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate tempstart = LocalDate.parse(startdate, formatter);
		this.startdate = tempstart.atStartOfDay();
		
		LocalDate tempend = LocalDate.parse(enddate, formatter);
		this.enddate = tempend.atStartOfDay();
	}
	
	@When("the customer requests the transaction report")
	public void customer_request_the_transaction_report() {
		result_costumer = report.getCustomerTransactions(cid, startdate, enddate);
	}
	
	
	@Given("we are listening")
	public void we_are_listening() {
		
		this.sizebefore = report.getDBSize();
		System.out.println(report.getDBSize());
		
		try {
			RabbitMqListener listener = new RabbitMqListener(this, "localhost");
			listener.listen(EXCHANGE_NAME, QUEUE_TYPE, PAYMENT_EVENT_BASE + TRANSACTION_CREATED_EVENT);
		} catch (Exception e) {
			System.out.println("Something went wrong");
			throw new Error(e);
		}
	}
	
	@When("a message from payments is received")
	public void a_message_from_payments_is_received() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate temp = LocalDate.parse("2021-01-17", formatter);
		LocalDateTime testdate = temp.atStartOfDay();
		
		Transaction test = new Transaction(UUID.randomUUID().toString(),"token1",
				BigDecimal.valueOf(100), "15", "05", "description", testdate);
		
		Event event = new Event(TRANSACTION_CREATED_EVENT, test);
		
		try {
			eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, PAYMENT_EVENT_BASE + TRANSACTION_CREATED_EVENT);
		} catch (Exception e) {
			throw new Error(e);
		}
		
		
	}
	
	@Then("a transaction is recorded")
	public void a_transaction_is_recorded() {
		
		assertEquals(this.sizebefore + 1, report.getDBSize());
	}
	
	
	@Override
	public void receiveEvent(Event event) throws Exception {
		
		System.out.println("Handling event: " + event);
        
        if (event.getEventType().equals(TRANSACTION_CREATED_EVENT)) {
        	Transaction to_add = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), Transaction.class);
        	report.addTransaction(to_add);
        } else {
        	System.out.println("event ignored: " + event);
        }
		
	}

}
