package com.thinkbites.rabbitpusher.rabbitmq;

/**
 * Implementation for {@link ConnectionHandler} that does nothing. This is
 * simply a convenience class
 * @author mono
 *
 */
public class EmptyConnectionHandler implements ConnectionHandler {

	@Override
	public void onConnectionLost() {}

}
