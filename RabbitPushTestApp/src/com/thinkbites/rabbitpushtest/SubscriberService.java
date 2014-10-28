package com.thinkbites.rabbitpushtest;

import android.widget.Toast;

import com.thinkbites.rabbitpusher.services.RabbitReaderService;

public class SubscriberService extends RabbitReaderService {

	@Override
	public void onConnectionSuccessful() {
		Toast.makeText(getApplicationContext(), "connection successful", Toast.LENGTH_SHORT).show();
	} 

	@Override
	public void onConnectionLost() {
		Toast.makeText(getApplicationContext(), "connection failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMessageReceived(String message) {
		sendBroadcast(MessageReceiver.getBroadcastIntent(message, getApplicationContext()));
	}

	@Override
	public String getChannelName() {
		return Constants.CHANNEL;
	}

	@Override
	public String getHostName() {
		return Constants.HOST_NAME;
	}

	@Override
	public boolean running() {
		return true;
	}

}
