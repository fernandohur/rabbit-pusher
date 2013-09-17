package com.thinkbites.rabbitpusher.rabbitmq;

public class EmptyMessageReceiver extends MessageReceiver {

	@Override
	public void onMessageReceived(String message, String channel) {
	}

}
