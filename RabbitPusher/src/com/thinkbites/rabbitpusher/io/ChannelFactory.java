package com.thinkbites.rabbitpusher.io;

/**
 * 
 * @author mono
 */
public class ChannelFactory {

	public static RabbitFanoutMessageStream getChannel(String hostName){
		return new RabbitFanoutMessageStream(hostName);		
	}
}
