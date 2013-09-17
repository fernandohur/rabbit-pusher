package com.thinkbites.rabbitpushapp.test;

import com.thinkbites.rabbitpusher.rabbitmq.MessageReceiver;

import junit.framework.TestCase;

public class TestMessageReceiver extends TestCase {

	boolean messageReceiverCalled;
	
	public void testWhenOnMessagePreReceivedIsCalled(){
		
		final String message = "foo message";
		final String channel = "bar channel;";
		messageReceiverCalled = false;
		
		MessageReceiver receiver = new MessageReceiver() {
			
			@Override
			public void onMessageReceived(String msg, String chn) {
				assertEquals(message, msg);
				assertEquals(channel, chn);
				messageReceiverCalled = true;
			}
		};
		receiver.onMessagePreReceived(message, channel);

		assertEquals(message, receiver.getLastMessage());
		assertTrue(messageReceiverCalled);
	}
	
}
