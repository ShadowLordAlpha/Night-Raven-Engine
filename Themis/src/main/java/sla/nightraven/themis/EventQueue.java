package sla.nightraven.themis;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public abstract class EventQueue implements Runnable, AutoCloseable {
	
	private Semaphore sema = new Semaphore(0);
	private Queue<Object> eventQueue;
	
	public abstract <T> void handleEvent(T event);
	
	@Override
	public void run() {

		try {
			while(true) {
				sema.acquire();
				
				handleEvent(eventQueue.poll());
			}
		} catch(InterruptedException e) {
			// TODO Auto-generated catch block
			// expected interruption exception
			e.printStackTrace();
		}
	}
	
	public <T> void fireEvent(T event) {
		eventQueue.add(event);
		sema.release();
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
	}
}
