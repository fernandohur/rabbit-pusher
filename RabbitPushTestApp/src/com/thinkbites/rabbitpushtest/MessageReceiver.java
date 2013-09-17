package com.thinkbites.rabbitpushtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class MessageReceiver extends BroadcastReceiver {

	public static final String EXTRA_MESSAGE = "extra_message";
	public static final String ACTION_NEW_MESSAGE = "action_new_message";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String message = intent.getStringExtra(EXTRA_MESSAGE);
		onMessageReceived(message);
	}
	
	public abstract void onMessageReceived(String message);
	
	public static Intent getBroadcastIntent(String message, Context c){
		Intent intent = new Intent();
		intent.setAction(ACTION_NEW_MESSAGE);
		intent.putExtra(EXTRA_MESSAGE, message);
		return intent;
	}
}
