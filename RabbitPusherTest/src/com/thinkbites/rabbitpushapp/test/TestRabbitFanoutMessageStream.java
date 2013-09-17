package com.thinkbites.rabbitpushapp.test;

import junit.framework.TestCase;

import com.thinkbites.rabbitpushapp.test.helpers.AndroidPublisher;
import com.thinkbites.rabbitpusher.io.RabbitFanoutMessageStream;
import static com.thinkbites.rabbitpushapp.test.Constants.HOST_NAME;;

public class TestRabbitFanoutMessageStream extends TestCase {

	final static String CHANNEL = "channel-foo.bar";
	private RabbitFanoutMessageStream stream;
	private AndroidPublisher publisher;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		stream = new RabbitFanoutMessageStream(HOST_NAME);
		stream.setChannel(CHANNEL);
		publisher = new AndroidPublisher(CHANNEL,HOST_NAME);
		publisher.connect();
	}
	
	/**
	 * When publishing 25 messages, 25 messages should be read by the {@link RabbitFannoutMessageStream}
	 * @throws Exception
	 */
	public void testWhenMessageIsPublishedThenItIsReceivedByMessageStream() throws Exception {
		int numMessages = 25;
		stream.connect();
		for (int i = 0; i < numMessages; i++) {
			String message = "foo-bar-message-"+i;
			publisher.publish(message);
		}
		int messagesRead = 0;
		for (int i = 0; i < numMessages; i++) {
			String message = stream.read();
			if (message!= null && message.matches("foo-bar-message-.+")){
				messagesRead++;
			}
		}
		assertEquals(numMessages, messagesRead);
	}
	
	public void testMessageBodyIsDeliveredCorrectly() throws Exception{
		String message = "foo-bar-message";
		stream.connect();
		publisher.publish(message);
		String read = stream.read();
		assertEquals(message, read);
		
		message = "bar-baz-message";
		publisher.publish(message);
		read = stream.read();
		assertEquals(message, read);
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		publisher.close();
	}
	
}
