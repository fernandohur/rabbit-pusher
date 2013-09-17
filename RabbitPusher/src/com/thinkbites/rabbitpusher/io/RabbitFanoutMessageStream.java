package com.thinkbites.rabbitpusher.io;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.thinkbites.rabbitpusher.exceptions.RabbitInitializationException;

public class RabbitFanoutMessageStream extends AbstractMessageStream{

	private Channel channel;
	private Connection connection;
	private ConnectionFactory factory;
	private QueueingConsumer consumer;
	private boolean connected;
	
	public RabbitFanoutMessageStream(String hostName){
		factory = new ConnectionFactory();
		factory.setHost(hostName);
	}
	
	public void connect() throws Exception{
		if (getChannel()==null){
			throw new RabbitInitializationException("channel cannot be null. Maybe you have not called setChannel(String)");
		}
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		channel.exchangeDeclare(getChannel(), "fanout");
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, getChannel(), "");
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(queue, true,consumer);
		connected = true;
	}
	
	public String read() throws Exception {
		if (!connected){
			connect();
		}
		try {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			return message;
		} catch (Exception e) {
			connected = false;
			throw new Exception("connection lost");
		}
	};
	
	@Override
	public void write(String message) throws Exception {
		throw new IllegalStateException("unsuported method");
	}
}
