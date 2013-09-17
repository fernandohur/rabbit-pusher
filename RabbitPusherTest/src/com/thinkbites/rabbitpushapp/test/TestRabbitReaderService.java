package com.thinkbites.rabbitpushapp.test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import android.os.AsyncTask;

import com.thinkbites.rabbitpushapp.test.helpers.AndroidPublisher;
import com.thinkbites.rabbitpushapp.test.mocks.RabbitReaderServiceImpl;

import junit.framework.TestCase;

public class TestRabbitReaderService extends TestCase{

	private CountDownLatch latch;
	private RabbitReaderServiceImpl service;
	private AsyncTask<Void, Void, Void> task;
	private AndroidPublisher publisher;
	
	@Override
	protected void setUp() throws Exception {
		service = new RabbitReaderServiceImpl();
		service.onCreate();
		task = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				service.onHandleIntent(null);
				return null;
			}
		};
		publisher = new AndroidPublisher(Constants.CHANNEL_NAME, Constants.HOST_NAME);
		publisher.connect();
	}
	
	public void testInitialValues(){
		assertEquals(Constants.HOST_NAME, service.getHostName());
		assertEquals(Constants.CHANNEL_NAME, service.getChannelName());
		assertNull(service.getMessageReceived());
		assertEquals(0, service.getMessagesReceived());
	}
	
	public void testSendingMessagesShouldIncreaseMessageCount() throws IOException, InterruptedException{
		int numMessages = 100;
		latch = new CountDownLatch(numMessages);
		service.setLatch(latch);
		task.execute();
		service.waitForConnection();
		
		for (int i = 0; i < numMessages; i++) {
			publisher.publish("message-number-"+i);
			System.out.println("message published: "+i);
		}
		latch.await();
		assertEquals(numMessages, service.getMessagesReceived());
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		task.cancel(true);
		publisher.close();
	}
	
	
	
}



