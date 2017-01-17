package sla.nightraven.theia;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class Window {
	
	 
	
	public Window(WindowProperties properties) {
		GLFW.glfwDefaultWindowHints();
		
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);
		
		
		long window = GLFW.glfwCreateWindow(640, 480, "Window Title", MemoryUtil.NULL, MemoryUtil.NULL);
		
	}
	
}
