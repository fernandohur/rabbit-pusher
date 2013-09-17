package com.thinkbites.rabbitpushapp.test;

import junit.framework.TestCase;

import com.thinkbites.rabbitpusher.exceptions.ChannelRegistrationException;
import com.thinkbites.rabbitpusher.io.Channel;
import com.thinkbites.rabbitpusher.io.MessageStream;
import com.thinkbites.rabbitpusher.rabbitmq.MessageReceiver;
import com.thinkbites.rabbitpusher.rabbitmq.RabbitReader;

public class TestAbstractRabbitReader extends TestCase {

	final static String CHANNEL1 = "channel-foo";
	final static String CHANNEL2 = "channel-bar";
	
	private MessageStream channel1Stream;
	private MessageStream channel2Stream;
	private Channel channel1;
	private Channel channel2;
	private RabbitReader reader;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		channel1Stream = new MockMessageStream();
		channel2Stream = new MockMessageStream();
		channel1 = new Channel(CHANNEL1, channel1Stream);
		channel2 = new Channel(CHANNEL2, channel2Stream);
		reader = new RabbitReader();
	}
	
	public void testSubscribeToSingleChannel(){
		reader.subscribe(channel1);
		assertEquals(1,reader.getChannels().size());
		assertEquals(CHANNEL1,reader.getLatestChannel().getName());		
	}
	
	public void testSubscribeTo2Channels(){
		reader.subscribe(channel1);
		reader.subscribe(channel2);
		assertEquals(2,reader.getChannels().size());
		assertEquals(CHANNEL2,reader.getLatestChannel().getName());
	}
	
	public void testSubscribe2TimesToChannelRaisesError(){
		reader.subscribe(channel1);
		try {
			reader.subscribe(channel1);
			fail();
		} catch (ChannelRegistrationException e) {
		}
	}
	
	public void testRegisteringHandler(){
		reader.subscribe(channel1);
		reader.register(new MessageReceiver(){
			public void onMessageReceived(String message, String channel){
				
			}
		});
		assertNotNull(reader.getMessageReceiver());
	}
	
	public void testWhenMessageIsPostedInChannelReceiverShouldBeNotified() throws Exception{
		final String text = "foo.bar";
		reader.subscribe(channel1);
		reader.register(new MessageReceiver(){
			public void onMessageReceived(String message, String channel){
				assertEquals(CHANNEL1, channel);
				assertEquals(text, message);
			}
		});
		channel1Stream.write(text);
		reader.read(CHANNEL1);
		assertEquals(text, reader.getLastMessage());		
	}
	
}
