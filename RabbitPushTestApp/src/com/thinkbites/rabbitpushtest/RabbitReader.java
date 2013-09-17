package com.thinkbites.rabbitpushtest;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitReader {

	private QueueingConsumer consumer;
	private Handler handler;
	
	public RabbitReader(String serverUrl, String exchangeName, Handler handler) throws IOException {
		this.handler = handler;
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(serverUrl);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(exchangeName, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, "");

		consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		
		System.out.println("Reading messages from RabbitReader");
	}
	
	public void loop() throws Exception{
		String message = null;
		do {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			message = new String(delivery.getBody());
			System.out.println("Message read: "+message);
			handler.onMessageReceived(message);
		} while(message != null);
	}
	
	public interface Handler{
		public void onMessageReceived(String message);
	}
	
}
