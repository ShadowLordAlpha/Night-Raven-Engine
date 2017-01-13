/**
 * 
 */
package sla.nightraven.theia;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sla.nightraven.theia.Event.Event;

/**
 * this is a singleton type class
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public class TheiaModule implements Runnable, AutoCloseable {
	
	private static final Logger LOG = LoggerFactory.getLogger(TheiaModule.class);
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
	private static List<Consumer<String>> listenerList = new Vector<Consumer<String>>();
	private List<Event> eventList = new Vector<Event>();
	
	static {
		
		// we can set the callback once and leave it forever
		GLFW.glfwSetErrorCallback(TheiaModule::errorCallback);
		
		// Very simple logger for testing
		if(LOG.isErrorEnabled()) {
			listenerList.add((msg) -> LOG.error(msg));
		}
	}
	
	/**
	 * Because GLFW requires the Main thread control of the main thread must be relinquish to the Theia Module. This should be called
	 * after another thread has been started to take over application functions
	 */
	public static void cedeMain() {
		
	}
	
	private static void errorCallback(int error, long description) {
		
		// there is nothing listening so we don't need to do anything
		if(listenerList.isEmpty()) {
			return;
		}
		
		String errorString = String.format("%d: %s", error, MemoryUtil.memUTF8(description));
		
		EXECUTOR_SERVICE.submit(() -> listenerList.forEach(consumer -> consumer.accept(errorString)));
	}
	
	public static void addErrorListeners(Consumer<String>... listeners) {
		for(Consumer<String> listener: listeners) {
			listenerList.add(listener);
		}
	}
	
	public static void removeErrorListeners(Consumer<String>... listeners) {
		for(Consumer<String> listener: listeners) {
			listenerList.remove(listener);
		}
	}
	
	/**
	 * Removes all the Error Listeners
	 */
	public static void clearAllErrorListeners() {
		listenerList.clear();
	}
	
	public TheiaModule() {
		
		if(!GLFW.glfwInit()) {
			TheiaModule.errorCallback(-1, MemoryUtil.memAddress(MemoryStack.stackUTF8("Failed to initilize GLFW Library!", true)));
		}
	}

	@Override
	public void run() {
		
	}
	
	@Override
	public void close() throws Exception {
		GLFW.glfwTerminate();
		TheiaModule.clearAllErrorListeners();
	}
}
