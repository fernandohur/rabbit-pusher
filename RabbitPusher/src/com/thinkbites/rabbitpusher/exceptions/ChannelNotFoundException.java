package com.thinkbites.rabbitpusher.exceptions;

public class ChannelNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6882173266006916953L;

	public ChannelNotFoundException(String channel) {
		super("the channel <"+channel+"> was not found");
	}
}
