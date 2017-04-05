package com.shadowcs.nightraven.themis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public class Themis {

	private boolean async = true;
	private ExecutorService executorServicer;

	private Themis() {}

	/**
	 * Constructs a new {@code Themis} instance with default settings, including asynchronous event throwing, and a
	 * cached thread pool.
	 *
	 * @return a new instance with default settings
	 */
	public static Themis newBuilder() {
		return new Themis();
	}

	/**
	 * 
	 * @param async
	 * @return this builder instance
	 */
	public Themis setAsyncEvents(boolean async) {
		this.async = async;
		return this;
	}

	boolean getAsyncEvents() {
		return this.async;
	}

	/**
	 * 
	 * @param executorServicer
	 * @return this builder instance
	 */
	public Themis setExecutorService(ExecutorService executorServicer) {
		this.executorServicer = executorServicer;
		return this;
	}

	ExecutorService getExecutorSerivce() {
		return (this.executorServicer == null) ? Executors.newCachedThreadPool() : this.executorServicer;
	}

	public EventManager build() {
		return new EventManager(getAsyncEvents(), getExecutorSerivce());
	}
}
