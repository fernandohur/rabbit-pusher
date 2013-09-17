package com.thinkbites.rabbitpushapp.test.helpers;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AndroidPublisher {

	private String channelName;
	private String hostName;
	private Channel channel;
	private Connection connection;
	
	public AndroidPublisher(String channelName, String hostName) {
		this.channelName = channelName;
		this.hostName = hostName;
	}
	
	public void connect() throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(channelName, "fanout");

	}
	
	public void publish(String message) throws IOException{
		channel.basicPublish(channelName, "", null, message.getBytes());
	}
	
	public void close() throws IOException{
		channel.close();
        connection.close();
	}
	
}
