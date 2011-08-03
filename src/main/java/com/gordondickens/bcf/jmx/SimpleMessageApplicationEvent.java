package com.gordondickens.bcf.jmx;

import org.springframework.context.ApplicationEvent;

public class SimpleMessageApplicationEvent extends ApplicationEvent {

	private String message;

	public SimpleMessageApplicationEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	@Override
	public String toString() {
		return "message=[" + message + "], " + super.toString();
	}

}