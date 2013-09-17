package com.thinkbites.rabbitpusher.rabbitmq;

/**
 * Handler class to be notified when the connection is lost
 * @author mono
 *
 */
public interface ConnectionHandler {

	public void onConnectionLost();
}
