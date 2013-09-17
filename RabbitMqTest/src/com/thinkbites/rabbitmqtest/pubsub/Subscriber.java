package com.thinkbites.rabbitmqtest.pubsub;

import static com.thinkbites.rabbitmqtest.Constants.PUBSUB_EXCHANGE_NAME;
import static com.thinkbites.rabbitmqtest.Constants.SERVER_URL;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Subscriber {

	static int ID = 0;
	static long maxDelay;
	
	public Subscriber() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		ID++;
		maxDelay = 0;
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_URL);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(PUBSUB_EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, PUBSUB_EXCHANGE_NAME, "");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		
	}

	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {

		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						new Subscriber();
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}).start();
			System.out.println(" ["+i+"] Waiting for messages. To exit press CTRL+C");
		}
		while (true){
			
		}

	}

}
