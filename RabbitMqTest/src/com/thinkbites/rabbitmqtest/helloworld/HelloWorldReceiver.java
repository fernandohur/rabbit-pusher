package com.thinkbites.rabbitmqtest.helloworld;

import static com.thinkbites.rabbitmqtest.Constants.QUEUE_HELLO_WOLRD;
import static com.thinkbites.rabbitmqtest.Constants.SERVER_URL;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class HelloWorldReceiver {

	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(SERVER_URL);
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.queueDeclare(QUEUE_HELLO_WOLRD, false, false, false, null);
	    
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(QUEUE_HELLO_WOLRD, true, consumer);
	    
	    while (true) {
	        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	        String message = new String(delivery.getBody());
	        System.out.println(" [x] Received '" + message + "'");
	      }
	}
}
