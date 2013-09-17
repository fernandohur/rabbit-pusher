package com.thinkbites.rabbitpusher.services;

import android.app.IntentService;
import android.content.Intent;

import com.thinkbites.rabbitpusher.io.Channel;
import com.thinkbites.rabbitpusher.io.RabbitFanoutMessageStream;
import com.thinkbites.rabbitpusher.rabbitmq.ConnectionHandler;
import com.thinkbites.rabbitpusher.rabbitmq.MessageReceiver;
import com.thinkbites.rabbitpusher.rabbitmq.RabbitReader;

public abstract class RabbitReaderService extends IntentService implements ConnectionHandler {

	private RabbitReader rabbitReader;
	private Channel channel;
	private RabbitFanoutMessageStream stream;
	
	public RabbitReaderService() {
		super("rabbit reader");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		stream = new RabbitFanoutMessageStream(getHostName());
		channel = new Channel(getChannelName(), stream);
		stream.setChannel(getChannelName());
		
		rabbitReader = new RabbitReader();
		rabbitReader.setConnectionHandler(this);
		rabbitReader.setMessageReceiver(new MessageReceiver() {
			@Override
			public void onMessageReceived(String message, String channel) {
				RabbitReaderService.this.onMessageReceived(message);
			}
		});
		rabbitReader.subscribe(channel);
	}
	
	@Override
	public void onHandleIntent(Intent intent) {
		try {
			channel.getStream().connect();
			onConnectionSuccessful();
			while(running()){
				rabbitReader.read(getChannelName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		onServiceClosing();
	}
	
	public abstract void onConnectionSuccessful();
	
	/**
	 * Handler executed when the connection is lost
	 */
	public abstract void onConnectionLost();
	/**
	 * Handler that is executed when a message is retrieved from the stream
	 * @param message the message read
	 */
	public abstract void onMessageReceived(String message);
	/**
	 * The name of the channel to connect to
	 * @return a string with the channel name
	 */
	public abstract String getChannelName();
	/**
	 * @return the URL of the RabbitMQ
	 */
	public abstract String getHostName();
	/**
	 * while this is true, the intent will attempt to read from the stream
	 * if false, it will quit
	 * @return boolean
	 */
	public abstract boolean running();
	
	public void onServiceClosing(){};
}




