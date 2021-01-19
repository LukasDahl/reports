/**
 * @author Wassim
 */

package com.gr15.businesslogic.resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.gr15.businesslogic.models.QueueService;
import com.gr15.messaging.rabbitmq.RabbitMqListener;

@ApplicationPath("/")
public class RestApplication extends Application {
	
	private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";
    private static final String PAYMENT_EVENT_BASE = "payment.events.";
    private static final String TRANSACTION_CREATED_EVENT = "transactionCreated";
	
	public RestApplication() {
		
		RabbitMqListener listener = new RabbitMqListener(new QueueService(), "rabbitMq");
		try {
			listener.listen(EXCHANGE_NAME, QUEUE_TYPE, PAYMENT_EVENT_BASE + TRANSACTION_CREATED_EVENT);
		} catch (Exception e) {
			throw new Error(e);
		}
		
	}

}
