package com.thinkbites.rabbitpushapp.test;

import com.thinkbites.rabbitpusher.io.AbstractMessageStream;
import com.thinkbites.rabbitpusher.io.MessageStream;

/**
 * THis is a {@link MessageStream} that always fails. USeful when testing no connection scenarios
 * @author mono
 *
 */
public class FunkyMessageStream extends AbstractMessageStream{

	@Override
	public String read() throws Exception {
		throw new Exception("unable to read because this is a funky channel");
	}

	@Override
	public void write(String message) throws Exception {
		
	}


}
