package com.thinkbites.rabbitpusher.exceptions;

public class ChannelRegistrationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 933761650648817703L;

	public ChannelRegistrationException(String channelName) {
		super("the channel <"+channelName+"> is already registered");
	}
}
