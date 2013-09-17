package com.thinkbites.rabbitpushapp.test.mocks;

import java.util.concurrent.CountDownLatch;

import android.content.Intent;

import com.thinkbites.rabbitpushapp.test.Constants;
import com.thinkbites.rabbitpusher.services.RabbitReaderService;

public class RabbitReaderServiceImpl extends RabbitReaderService {
	
	private CountDownLatch connectionLatch;
	private CountDownLatch latch;
	private int messagesReceived;
	private String messageReceived;
	private boolean connectionLost;
	private boolean running;
	
	public RabbitReaderServiceImpl() {
		connectionLost = false;
		running = true;
		messagesReceived = 0;
		connectionLatch = new CountDownLatch(1);
	}
	
	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
	
	public void waitForConnection() throws InterruptedException{
		connectionLatch.await();
	}
	
	@Override
	public void onHandleIntent(Intent intent) {
		System.out.println("onHandleIntent called");
		super.onHandleIntent(intent);
		System.out.println("onHandleIntent finished");
	}
	
	public void onConnectionSuccessful(){
		connectionLatch.countDown();
	}
	
	@Override
	public void onConnectionLost() {
		connectionLost = true;
	}

	@Override
	public void onMessageReceived(String message) {
		System.out.println("Message received: "+message);
		messageReceived = message;
		messagesReceived++;
		latch.countDown();
	}
	

	@Override
	public String getChannelName() {
		return Constants.CHANNEL_NAME;
	}

	@Override
	public String getHostName() {
		return Constants.HOST_NAME;
	}

	@Override
	public boolean running() {
		return running;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getMessageReceived() {
		return messageReceived;
	}

	public boolean isConnectionLost() {
		return connectionLost;
	}
	
	public int getMessagesReceived() {
		return messagesReceived;
	}

}
