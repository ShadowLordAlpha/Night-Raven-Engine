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
 */
package sla.nightraven.themis.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public class EventManager {

	private Map<Class<?>, List<Consumer<?>>> eventMap;

	public EventManager() {
		eventMap = new HashMap<Class<?>, List<Consumer<?>>>();
	}

	/**
	 * Registers a class as an event to be used. If a class is already registered as an event the method will return without any changes being made.
	 * 
	 * @param clazz The class to be registered as an event.
	 * @return The EventManger that was used to call this method, useful for method chaining.
	 */
	public <T> EventManager registerEvent(Class<T> clazz) {
		
		if(eventMap.get(clazz) == null) {
			eventMap.put(clazz, new Vector<Consumer<?>>());
		}
		
		System.out.println(eventMap.get(clazz) + " " + clazz.getName());

		return this;
	}

	/**
	 * Unregisters a class from the event system. If a class that is not registered as an event is unregistered nothing will happen and the method
	 * will complete as normal. A registered event will be removed and all listener references dropped.
	 * 
	 * @param clazz The class to be unregistered as an event.
	 * @return The EventManger that was used to call this method, useful for method chaining.
	 */
	public EventManager unregisterEvent(Class<?> clazz) {

		eventMap.remove(clazz);
		
		return this;
	}
	
	/**
	 * Because any and every class is allowed to be made into an event and due to the way lambda type methods work the class or the type of event a listener
	 * listens for must be passed in as an argument in addition to the listener itself.
	 * 
	 * @param clazz
	 * @param listener
	 * @return The EventManger that was used to call this method, useful for method chaining.
	 */
	public <T> EventManager addListener(Class<T> clazz, Consumer<T> listener) {
		
		List<Consumer<?>> listenerList = eventMap.get(clazz);
		
		if(listenerList == null) {
			throw new RuntimeException();
		}
		
		listenerList.add(listener);
		
		return this;
	}
	
	public <T> EventManager removeListeners(Class<T> clazz, Consumer<T> listener) {
		
		
		
		return this;
	}

	public EventManager clearListeners() {
		return this;
	}
	
	public <T> EventManager throwEvent(T event) {
		
		System.out.println(event.getClass().getName());
		
		return this;
	}
}
