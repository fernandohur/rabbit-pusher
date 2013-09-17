package com.thinkbites.rabbitpusher.rabbitmq;

import java.util.Collection;
import java.util.HashMap;

import com.thinkbites.rabbitpusher.exceptions.ChannelNotFoundException;
import com.thinkbites.rabbitpusher.exceptions.ChannelRegistrationException;
import com.thinkbites.rabbitpusher.exceptions.RabbitInitializationException;
import com.thinkbites.rabbitpusher.io.Channel;

public class RabbitReader {

	private Channel latestChannel;
	private HashMap<String,Channel> channels;
	private MessageReceiver receiver;
	private ConnectionHandler connectionHandler;
	
	public RabbitReader() {
		channels = new HashMap<String, Channel>();
		connectionHandler = new EmptyConnectionHandler();
	}

	public void setConnectionHandler(ConnectionHandler connectionHandler) {
		this.connectionHandler = connectionHandler;
	}
	
	public void subscribe(Channel channel) {
		if (channels.containsKey(channel.getName())){
			throw new ChannelRegistrationException(channel.getName());
		}
		else{
			channels.put(channel.getName(), channel);
			this.latestChannel = channel;
		}
	}
	
	public void read(String channel){
		verifyHandlers();
		if (channels.containsKey(channel)){
			Channel chn = channels.get(channel);
			String message = readFromStream(chn);
			if (message != null){
				receiver.onMessagePreReceived(message, channel);
			}
		}
		else{
			throw new ChannelNotFoundException(channel);
		}
	}
	
	private void verifyHandlers() {
		if (connectionHandler==null || receiver == null){
			throw new RabbitInitializationException("connectionHandler or receiver is null");
		}
	}

	private String readFromStream(Channel channel){
		try {
			return channel.getStream().read();
		} catch (Exception e) {
			connectionHandler.onConnectionLost();
			return null;
		}
	}

	public Collection<Channel> getChannels() {
		return channels.values();
	}

	public Channel getLatestChannel() {
		return latestChannel;
	}

	public void register(MessageReceiver messageReceiver) {
		this.receiver = messageReceiver;
	}

	public MessageReceiver getMessageReceiver() {
		return receiver;
	}

	public String getLastMessage() {
		return receiver.getLastMessage();
	}

}
