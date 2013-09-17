package com.thinkbites.rabbitpushapp.test;

import com.thinkbites.rabbitpusher.io.AbstractMessageStream;

import junit.framework.TestCase;

public class TestAbstractMessageStream extends TestCase {

	private AbstractMessageStream stream = new AbstractMessageStream() {
		
		@Override
		public void write(String message) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public String read() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public void testSetChannelAssignsChannel(){
		assertNull(stream.getChannel());
		stream.setChannel("foo-bar");
		assertEquals("foo-bar", stream.getChannel());
	}
	
}
