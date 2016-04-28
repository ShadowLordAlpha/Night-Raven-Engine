package sla.nightraven.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import sla.nightraven.Builder;

public class WindowBuilder implements Builder<Window> {

	private int width = 800;
	private int height = 600;
	private String title = "Night-Raven";
	private long monitor = MemoryUtil.NULL;
	
	public int getWidth() {
		return width;
	}

	public WindowBuilder setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public WindowBuilder setHeight(int height) {
		this.height = height;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public WindowBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public long getMonitor() {
		return monitor;
	}

	public WindowBuilder setMonitor(long monitor) {
		this.monitor = monitor;
		return this;
	}

	void setWindowHints() {
		// Reset window hints as we don't want former hints messing with current hints
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // We never want a window to show right after creation.
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API); // We always assume a Vulkan instance with this engine
		// GLFW.glfwWindowHint(hint, value);
	}
	
	@Override
	public Window build() {
		return new Window(this);
	}
}
