package com.thinkbites.rabbitpushapp.test.mocks;

import com.thinkbites.rabbitpusher.io.AbstractMessageStream;

/**
 * A Mock implementation of {@link MockMessageStream} that does no real IO
 * @author mono
 *
 */
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
	
	@Override
	public void connect() throws Exception {
		
	}

}
