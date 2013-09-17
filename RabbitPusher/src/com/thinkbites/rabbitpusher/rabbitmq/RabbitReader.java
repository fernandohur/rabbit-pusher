package com.thinkbites.rabbitpusher.rabbitmq;

import java.util.Collection;
import java.util.HashMap;

import com.thinkbites.rabbitpusher.exceptions.ChannelNotFoundException;
import com.thinkbites.rabbitpusher.exceptions.ChannelRegistrationException;
import com.thinkbites.rabbitpusher.io.Channel;

public class RabbitReader {

	private Channel latestChannel;
	private HashMap<String,Channel> channels;
	private MessageReceiver receiver;
	
	public RabbitReader() {
		channels = new HashMap<String, Channel>();
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
		if (channels.containsKey(channel)){
			Channel chn = channels.get(channel);
			String message = readFromStream(chn);
			receiver.onMessagePreReceived(message, channel);
		}
		else{
			throw new ChannelNotFoundException(channel);
		}
	}
	
	private String readFromStream(Channel channel){
		try {
			return channel.getStream().read();
		} catch (Exception e) {
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
