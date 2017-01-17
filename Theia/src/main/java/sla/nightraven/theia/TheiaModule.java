package sla.nightraven.theia;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import sla.nightraven.themis.EventManager;

public class TheiaModule {
	
	private static EventManager staticEventManager;
	
	public static void init() {
		staticEventManager = new EventManager();
		staticEventManager.addListener(String.class, error -> System.err.println(error));
		
		GLFW.glfwSetErrorCallback(TheiaModule::GlfwErrorCallback);
		
		if(!GLFW.glfwInit()) {
			staticEventManager.fireEventAsync("-1: Failed to initialize the GLFW library");
		}
	}
	
	public static void pollEvents() {
		GLFW.glfwPollEvents();
	}
	
	// because GLFW requires the main thread for window reasons
	public static void cedeMainThread() {
		
	}
	
	private static void GlfwErrorCallback(int error, long description) {
		// don't much care when this gets fired as if its fired there is an error that needs to be fixed
		staticEventManager.fireEventAsync(error + ": " + MemoryUtil.memUTF8(description));
	}
	
	public static void close() {
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).close();
		staticEventManager.close();
		staticEventManager = null;
	}
}
