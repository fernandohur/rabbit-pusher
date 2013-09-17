package com.thinkbites.rabbitpusher.exceptions;

/**
 * Exception thrown when attempting to read from an non existing channel
 * @author mono
 *
 */
public class ChannelNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6882173266006916953L;

	public ChannelNotFoundException(String channel) {
		super("the channel <"+channel+"> was not found");
	}
}
