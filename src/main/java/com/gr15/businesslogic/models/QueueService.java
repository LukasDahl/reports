package com.gr15.businesslogic.models;

import com.google.gson.Gson;
import com.gr15.messaging.interfaces.IEventReceiver;
import com.gr15.messaging.models.Event;
import com.gr15.messaging.rabbitmq.RabbitMqListener;


public class QueueService implements IEventReceiver {
	
    private static final String TRANSACTION_CREATED_EVENT = "transactionCreated";
    
    
	public QueueService () {
		
	}
	
	
	@Override
	public void receiveEvent(Event event) throws Exception {
		
		System.out.println("Handling event: " + event);
        
        if (event.getEventType().equals(TRANSACTION_CREATED_EVENT)) {
        	Transaction to_add = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), Transaction.class);
        	Report.report.addTransaction(to_add);
        	System.out.println("Transaction added");
        } else {
        	System.out.println("event ignored: " + event);
        }
		
	}

}
