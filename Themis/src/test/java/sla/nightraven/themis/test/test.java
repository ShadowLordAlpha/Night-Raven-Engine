package sla.nightraven.themis.test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

public class test {
	
	static Thread mainThread;
	private static int i = 0;

	public static void main(String[] args) {
		mainThread = Thread.currentThread();
		System.out.println(mainThread);
		new Thread(test::core).start();
		TestThreadFactory.cede();
	}
	
	public static void core() {
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExecutorService exec = Executors.newSingleThreadExecutor(new TestThreadFactory());
		
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		
		try {
			Thread.sleep(60000);
		} catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		exec.submit(() -> System.out.println(i++ + " " + Thread.currentThread().getName()));
		
		exec.shutdown();
	}
	
	public static class TestThreadFactory implements ThreadFactory {
		
		private static Semaphore sema = new Semaphore(0);
		private static Queue<Runnable> q = new ConcurrentLinkedQueue<Runnable>();
		
		public static void cede() {
			try {
				sema.acquire();
				
				q.poll().run();
			} catch(InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public Thread newThread(Runnable r) {
			System.out.println(r.getClass());
			
			return new Thread(() -> { q.offer(r); sema.release(); }, "WRAPPER");
		}
		
	}
}
