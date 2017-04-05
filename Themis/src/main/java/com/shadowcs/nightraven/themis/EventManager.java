/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2017 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.shadowcs.nightraven.themis;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * A generics based event system for using Java Objects as events.
 * 
 * @version 1.2.0
 * @author Josh "ShadowLordAlpha"
 *
 */
public class EventManager {
	
	private boolean async;
	private ExecutorService pool;
	private LoadingCache<Class<?>, Set<Consumer<?>>> listenerCache;
	
	EventManager(boolean async, ExecutorService pool) {
		
		this.async = async;
		this.pool = pool;
		listenerCache = Caffeine.newBuilder().build(key -> ConcurrentHashMap.newKeySet());
	}

	public <V> EventManager addListener(Class<V> clazz, Consumer<V> listener) {

		Set<Consumer<?>> list = listenerCache.get(clazz);
		if(list == null) {
			throw new NullPointerException();
		}
		
		list.add(listener);
		
		return this;
	}

	public <V> EventManager removeListener(Class<V> clazz, Consumer<V> listener) {
		
		Set<Consumer<?>> list = listenerCache.get(clazz);
		if(list == null) {
			throw new NullPointerException();
		}
		
		list.remove(listener);
		
		return this;
	}

	// honestly might be able to get rid of the regular fireEvent methods as I doubt I will actually be using them
	
	public <V> EventManager fireEvent(V event) {
		
		return fireEvent((Class<V>) event.getClass(), event);
	}
	
	public <V> EventManager fireEvent(Class<V> clazz, V event) {
		
		Set<Consumer<?>> list = listenerCache.get(clazz);
		if(list == null) {
			throw new NullPointerException();
		}
		
		for(Consumer<?> listener: list) {
			((Consumer<V>) listener).accept(event);
		}
		
		return this;
	}

	public <V> Future<V> fireEventAsync(V event) {
		return fireEventAsync(event, pool);
	}
	
	public <V> Future<V> fireEventAsync(Class<V> clazz, V event) {
		return fireEventAsync(clazz, event, pool);
	}
	
	public <V> Future<V> fireEventAsync(V event, ExecutorService pool) {
		return pool.submit(() -> fireEvent(event), event);
	}
	
	public <V> Future<V> fireEventAsync(Class<V> clazz, V event, ExecutorService pool) {
		return pool.submit(() -> fireEvent(clazz, event), event);
	}
}
