package com.thinkbites.rabbitpushapp.test;

import junit.framework.TestCase;

import com.thinkbites.rabbitpushapp.test.mocks.FunkyMessageStream;
import com.thinkbites.rabbitpushapp.test.mocks.MockMessageStream;
import com.thinkbites.rabbitpusher.exceptions.ChannelNotFoundException;
import com.thinkbites.rabbitpusher.exceptions.ChannelRegistrationException;
import com.thinkbites.rabbitpusher.io.Channel;
import com.thinkbites.rabbitpusher.io.MessageStream;
import com.thinkbites.rabbitpusher.rabbitmq.ConnectionHandler;
import com.thinkbites.rabbitpusher.rabbitmq.EmptyMessageReceiver;
import com.thinkbites.rabbitpusher.rabbitmq.MessageReceiver;
import com.thinkbites.rabbitpusher.rabbitmq.RabbitReader;

public class TestAbstractRabbitReader extends TestCase {

	final static String CHANNEL1 = "channel-foo";
	final static String CHANNEL2 = "channel-bar";
	final static String FUNKY_CHANNEL = "funky_channel";
	
	private MessageStream channel1Stream;
	private MessageStream channel2Stream;
	private MessageStream funkyStream;
	
	private Channel channel1;
	private Channel channel2;
	private Channel funkyChannel;
	
	private RabbitReader reader;
	private boolean connectionLost;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		channel1Stream = new MockMessageStream();
		channel2Stream = new MockMessageStream();
		funkyStream = new FunkyMessageStream();
		
		channel1 = new Channel(CHANNEL1, channel1Stream);
		channel2 = new Channel(CHANNEL2, channel2Stream);
		funkyChannel = new Channel(FUNKY_CHANNEL, funkyStream);
		
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
	
	/**
	 * Subscribing more than once to a channel raises a {@link ChannelRegistrationException}
	 */
	public void testSubscribe2TimesToChannelRaisesError(){
		reader.subscribe(channel1);
		try {
			reader.subscribe(channel1);
			fail();
		} catch (ChannelRegistrationException e) {
		}
	}
	
	/**
	 * When registering a {@link MessageReceiver}, it should not be null
	 */
	public void testRegisteringHandler(){
		reader.subscribe(channel1);
		reader.setMessageReceiver(new MessageReceiver(){
			public void onMessageReceived(String message, String channel){
				
			}
		});
		assertNotNull(reader.getMessageReceiver());
	}
	
	/**
	 * When reading from a channel, the correct channel should be sent to the message receiver
	 * @throws Exception
	 */
	public void testWhenMessageIsPostedInChannelReceiverShouldBeNotified() throws Exception{
		final String text = "foo.bar";
		reader.subscribe(channel1);
		reader.setMessageReceiver(new MessageReceiver(){
			public void onMessageReceived(String message, String channel){
				assertEquals(CHANNEL1, channel);
				assertEquals(text, message);
			}
		});
		channel1Stream.write(text);
		reader.read(CHANNEL1);
		assertEquals(text, reader.getLastMessage());		
	}
	
	
	/**
	 * When reading from a channel, the correct channel should be sent to the message receiver
	 * @throws Exception
	 */
	public void testWhenMessageIsPostedInChannelReceiverShouldBeNotified2() throws Exception{
		final String text = "foo.bar";
		reader.subscribe(channel1);
		reader.subscribe(channel2);
		reader.setMessageReceiver(new MessageReceiver(){
			public void onMessageReceived(String message, String channel){
				assertEquals(CHANNEL2, channel);
				assertEquals(text, message);
			}
		});
		channel2Stream.write(text);
		reader.read(CHANNEL2);
		assertEquals(text, reader.getLastMessage());		
	}
	
	/**
	 * When reading from a disconnected channel or if for some reason the channel is not readable
	 * the {@link ConnectionHandler} will be notified.
	 */
	public void testWhenReadingAndNoConnectionMessageReceiverShouldNotBeNotified(){
		connectionLost = false;
		reader.subscribe(funkyChannel);
		reader.setConnectionHandler(new ConnectionHandler() {
			
			@Override
			public void onConnectionLost() {
				connectionLost = true;
			}
		});
		reader.setMessageReceiver(new MessageReceiver() {
			
			@Override
			public void onMessageReceived(String message, String channel) {
				fail();
			}
		});
		reader.read(FUNKY_CHANNEL);
		assertEquals(null, reader.getLastMessage());
		assertTrue(connectionLost);
	}
	
	/**
	 * When reading from a non registered channel, 
	 * a {@link ChannelNotFoundException} is thrown
	 */
	public void testReadingFromNonExistingChannel(){
		reader.subscribe(channel1);
		reader.setMessageReceiver(new EmptyMessageReceiver());
		try {
			reader.read(CHANNEL2);
			fail();
		} catch (ChannelNotFoundException e) {
		}
	}
	
	
	
}
