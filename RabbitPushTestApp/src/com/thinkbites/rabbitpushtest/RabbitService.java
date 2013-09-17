package com.thinkbites.rabbitpushtest;

import android.app.IntentService;
import android.content.Intent;

import com.thinkbites.rabbitpushtest.RabbitReader.Handler;

public class RabbitService extends IntentService implements Handler {

	private RabbitReader reader;
	
	public RabbitService() {
		super("rabit reader service");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			reader = new RabbitReader("157.253.116.148", "android-test", this);
			reader.loop();
			
		} catch (Exception e) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			startService(new Intent(getApplicationContext(), RabbitService.class));
		}
	}

	@Override
	public void onMessageReceived(String message) {
		sendBroadcast(MessageReceiver.getBroadcastIntent(message, getApplicationContext()));
	}
}
