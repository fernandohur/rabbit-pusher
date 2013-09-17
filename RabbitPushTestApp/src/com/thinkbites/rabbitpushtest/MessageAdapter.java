package com.thinkbites.rabbitpushtest;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MessageAdapter extends ArrayAdapter<String> {

	public MessageAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}
	
}
