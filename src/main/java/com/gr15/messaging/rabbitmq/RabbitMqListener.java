/**
 * @author Wassim
 */

package com.gr15.messaging.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.gr15.messaging.interfaces.IEventReceiver;
import com.gr15.messaging.models.Event;

public class RabbitMqListener {

    IEventReceiver eventReceiver;
    String hostname;

    public RabbitMqListener(IEventReceiver eventReceiver, String hostname) {
        this.eventReceiver = eventReceiver;
        this.hostname = hostname;
    }

    public void listen(String exchangeName, String queueType, String topic) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.hostname);
        if (hostname.equals("localhost")) {
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
        }
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, queueType);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, topic);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] receiving " + message);
            Event event = new Gson().fromJson(message, Event.class);
            try {
                eventReceiver.receiveEvent(event);
            } catch (Exception e) {
                throw new Error(e);
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
