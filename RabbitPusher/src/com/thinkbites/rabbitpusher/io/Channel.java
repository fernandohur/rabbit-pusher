package com.thinkbites.rabbitpusher.io;

public class Channel {

	private String name;
	private MessageStream stream;
	
	public Channel(String name, MessageStream stream) {
		super();
		this.name = name;
		this.stream = stream;
		stream.setChannel(name);
	}
	
	public String getName() {
		return name;
	}
	
	public MessageStream getStream() {
		return stream;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
