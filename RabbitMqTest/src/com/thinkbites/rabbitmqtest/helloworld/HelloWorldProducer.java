package com.thinkbites.rabbitmqtest.helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import static com.thinkbites.rabbitmqtest.Constants.*;

public class HelloWorldProducer {
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(SERVER_URL);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_HELLO_WOLRD, false,false,false,null);
		
		channel.basicPublish("", QUEUE_HELLO_WOLRD, null, MESSAGE_HELLO_WORLD.getBytes());
		
		channel.close();
		connection.close();
		
	}
	
}
