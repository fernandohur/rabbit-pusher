package com.thinkbites.rabbitpusher.rabbitmq;

public abstract class MessageReceiver {

	private String lastMessage;
	
	public void onMessagePreReceived(String message, String channel){
		this.lastMessage = message;
		onMessageReceived(message, channel);
	}
	
	public abstract void onMessageReceived(String message, String channel);

	public String getLastMessage(){
		return lastMessage;
	}
}
