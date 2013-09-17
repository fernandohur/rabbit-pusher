package com.thinkbites.rabbitpushtest;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.thinkbites.rabbitpushapp.R;

public class MainActivity extends ListActivity {

	private MessageAdapter messageAdapter;
	private BroadcastReceiver receiver;
	private IntentFilter filter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageAdapter = new MessageAdapter(getApplicationContext());
		setListAdapter(messageAdapter);
		
		filter = new IntentFilter(MessageReceiver.ACTION_NEW_MESSAGE);
		receiver = new MessageReceiver() {
			
			@Override 
			public void onMessageReceived(String message) {
				messageAdapter.add(message);
				messageAdapter.notifyDataSetChanged();
				getListView().smoothScrollToPosition(messageAdapter.getCount()-1);
			}
		};
		
		startService(new Intent(getApplicationContext(), RabbitService.class));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
	


}
