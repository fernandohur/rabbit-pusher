package com.thinkbites.rabbitpushapp.test;

import com.thinkbites.rabbitpusher.io.AbstractMessageStream;

public class MockMessageStream extends AbstractMessageStream {

	private String message;
	
	@Override
	public String read() throws Exception {
		String result = message;
		message = null;
		return result;
	}

	@Override
	public void write(String message) throws Exception {
		this.message = message;
	}

}
