rabbit-pusher
=============

Wrapper built on top of RabbitMQ to create android push services


## Project Structure

The project is structured as follows:
 - **RabbitMqTest**: don't pay much attention to this project, it contains samples and experiments using the Java Client for RabbitMQ
 - **RabbitPushTestApp**: this project has an Android project dependency with RabbitPusher, it contains a sample app that shows how to receive pushes. More info on this later.
 - **RabbitPusher**: This is the important project, an Android library project, contains the actual code to receive pushes.
 - **RabbitPusherTest**: this is the test project for RabbitPusher and RapidPushTestApp

## Concepts

Although this project is a wrapper for RabbitMQ and knowledge of RabbitMQ internals are a good thing, you should not need to know
about RabbitMQ to use *rabbit-pusher*

#### Channel
A **channel** is, similar to Parse's push channels or PubNub's channels a place where messages of a certain type are delivered.
A subscriber can listen to many different channels.
#### Publisher
A **publisher** publishes messages into channels, these messages are delivered to any Subscriber listening on a specific channel
#### Subscriber
A **subscriber** can subscribe to several different channels, each of which will send him push messages.

## Basics

This is a very basic tutorial to create a Subscriber that receives messages from a RabbitMQ server.  
First things first, in your `AndroidManifest.xml` add the following lines
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```
and
```xml
<service android:name="your_package_name.SubscriberService"></service>
```

Now lets create the Service that receives the messages. To do this we must extend RabbitReaderService (which extends IntentService).
For a very basic implementation you can just copy-paste the following:

```java
public class SubscriberService extends RabbitReaderService {

	@Override
	public void onConnectionSuccessful() {
		Toast.makeText(getApplicationContext(), "connection successful", Toast.LENGTH_SHORT).show();
	} 

	@Override
	public void onConnectionLost() {
		Toast.makeText(getApplicationContext(), "connection successful", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMessageReceived(String message) {
		sendBroadcast(MessageReceiver.getBroadcastIntent(message, getApplicationContext()));
	}

	@Override
	public String getChannelName() {
		return Constants.CHANNEL;
	}

	@Override
	public String getHostName() {
		return Constants.HOST_NAME;
	}

	@Override
	public boolean running() {
		return true;
	}

}
```

the most important thing to do here is to notice the `onMessageReceived`. This method is executed whenever a push arrives, 
in this case we are broadcasting the received message to a broadcast receiver called MessageReceiver, but you could do whatever you want

To start the service use 

```java
startService(new Intent(getApplicationContext(), SubscriberService.class));
```

