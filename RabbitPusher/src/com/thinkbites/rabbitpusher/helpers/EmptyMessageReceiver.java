package com.thinkbites.rabbitpusher.helpers;

import com.thinkbites.rabbitpusher.rabbitmq.MessageReceiver;

public class EmptyMessageReceiver extends MessageReceiver {

	@Override
	public void onMessageReceived(String message, String channel) {
	}

}
