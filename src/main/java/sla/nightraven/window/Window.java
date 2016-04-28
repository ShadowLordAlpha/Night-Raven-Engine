package sla.nightraven.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import sla.nightraven.Pointer;

public class Window extends Pointer implements AutoCloseable {
	
	private boolean visible;
	private int width;
	private int height;
	private String title;

	Window(WindowBuilder wBuilder) {
		
		wBuilder.setWindowHints();
		width = wBuilder.getWidth();
		height = wBuilder.getHeight();
		title = wBuilder.getTitle();
		address = GLFW.glfwCreateWindow(width, height, title, wBuilder.getMonitor(), MemoryUtil.NULL);
		if(wBuilder.getMonitor() != 0) {
			center();
		}
	}

	public boolean isVisible() {
		return visible;
	}
	
	public Window center() {
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(address(), ((vidmode.width()/2) - (width / 2)), ((vidmode.height()/2) - (height / 2)));
		return this;
	}

	public Window show() {
		if(!isVisible()) {
			visible = true;
			GLFW.glfwShowWindow(address());
		}
		return this;
	}
	
	public Window hide() {
		if(isVisible()) {
			visible = false;
			GLFW.glfwHideWindow(address());
		}
		return this;
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(address());
	}

	@Override
	public void close() throws Exception {
		GLFW.glfwDestroyWindow(address());
	}
}
