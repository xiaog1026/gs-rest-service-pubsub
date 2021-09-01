package com.pubsub.bean;

public class Message {

	private final long id;
	private final String message;
	private final String time;
	
	
	public Message(long id, String message) {
		this.id = id;
		this.message = message;
		this.time = "";
	}

	public Message(long id, String time ,String message) {
		this.id = id;
		this.time = time;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return message;
	}
	
	public String getTime() {
		return time;
	}
}
