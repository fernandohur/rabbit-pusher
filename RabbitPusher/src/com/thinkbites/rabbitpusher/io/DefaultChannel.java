package com.thinkbites.rabbitpusher.io;

public class DefaultChannel extends Channel{

	public DefaultChannel(String channelName, String hostName) {
		super(channelName, new RabbitFanoutMessageStream(hostName));
		getStream().setChannel(channelName);
	}
	
}
