package com.thinkbites.rabbitpusher.io;

public interface MessageStream {

	public String read() throws Exception;
	public void write(String message) throws Exception;
	public String getChannel();
	public void setChannel(String channel);
}
