package com.thinkbites.rabbitpushapp.test;

import junit.framework.TestCase;

import com.thinkbites.rabbitpushapp.test.helpers.AndroidPublisher;
import com.thinkbites.rabbitpusher.io.RabbitFannoutMessageStream;

public class TestRabbitFanoutMessageStream extends TestCase {

	final static String CHANNEL = "channel-foo.bar";
	final static String HOST_NAME = "157.253.118.124";
	private RabbitFannoutMessageStream stream;
	private AndroidPublisher publisher;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		stream = new RabbitFannoutMessageStream(HOST_NAME);
		stream.setChannel(CHANNEL);
		publisher = new AndroidPublisher(CHANNEL,HOST_NAME);
		publisher.connect();
	}
	
	public void testWhenMessageIsPublishedThenItIsReceivedByMessageStream() throws Exception{
		int numMessages = 10;
		
		for (int i = 0; i < numMessages; i++) {
			String message = "foo-bar-message-"+i;
			publisher.publish(message);
			System.out.println(message+" was published");
		}
		for (int i = 0; i < numMessages; i++) {
			System.out.println("reading from stream");
			String message = stream.read();
			System.out.println(message + " was read ");
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		publisher.close();
	}
	
}
