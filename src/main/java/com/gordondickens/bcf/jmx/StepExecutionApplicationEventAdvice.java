package com.gordondickens.bcf.jmx;

import org.aspectj.lang.JoinPoint;
import org.springframework.batch.core.StepExecution;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Wraps calls for methods taking {@link StepExecution} as an argument and
 * publishes notifications in the form of {@link ApplicationEvent}.
 * 
 */
public class StepExecutionApplicationEventAdvice implements
		ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	/*
	 * @see org.springframework.context.ApplicationEventPublisherAware#
	 * setApplicationEventPublisher
	 * (org.springframework.context.ApplicationEventPublisher)
	 */
	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void before(JoinPoint jp, StepExecution stepExecution) {
		String msg = "Before: " + jp.toShortString() + " with: "
				+ stepExecution;
		publish(jp.getTarget(), msg);
	}

	public void after(JoinPoint jp, StepExecution stepExecution) {
		String msg = "After: " + jp.toShortString() + " with: " + stepExecution;
		publish(jp.getTarget(), msg);
	}

	public void onError(JoinPoint jp, StepExecution stepExecution, Throwable t) {
		String msg = "Error in: " + jp.toShortString() + " with: "
				+ stepExecution + " (" + t.getClass() + ":" + t.getMessage()
				+ ")";
		publish(jp.getTarget(), msg);
	}

	/*
	 * Publish a {@link SimpleMessageApplicationEvent} with the given
	 * parameters.
	 */
	private void publish(Object source, String message) {
		applicationEventPublisher
				.publishEvent(new SimpleMessageApplicationEvent(source, message));
	}

}
