package com.gr15.businesslogic.models;

import com.google.gson.Gson;
import com.gr15.messaging.interfaces.IEventReceiver;
import com.gr15.messaging.models.Event;
import com.gr15.messaging.rabbitmq.RabbitMqListener;


public class QueueService implements IEventReceiver {
	
	private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";
    private static final String PAYMENT_EVENT_BASE = "payment.events.";
    private static final String TRANSACTION_CREATED_EVENT = "transactionCreated";
    
    public static QueueService queue = new QueueService();
   
	
	public QueueService () {
		try {
			RabbitMqListener listener = new RabbitMqListener(this, "rabbitMq");
			listener.listen(EXCHANGE_NAME, QUEUE_TYPE, PAYMENT_EVENT_BASE + TRANSACTION_CREATED_EVENT);
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	
	@Override
	public void receiveEvent(Event event) throws Exception {
		
		System.out.println("Handling event: " + event);
        
        if (event.getEventType().equals(TRANSACTION_CREATED_EVENT)) {
        	Transaction to_add = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), Transaction.class);
        	Report.report.addTransaction(to_add);
        } else {
        	System.out.println("event ignored: " + event);
        }
		
	}

}
