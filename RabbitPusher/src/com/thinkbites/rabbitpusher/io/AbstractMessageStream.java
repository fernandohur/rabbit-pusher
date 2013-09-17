package com.thinkbites.rabbitpusher.io;

public abstract class AbstractMessageStream implements MessageStream{

	private String channel;
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getChannel() {
		return channel;
	}
}
