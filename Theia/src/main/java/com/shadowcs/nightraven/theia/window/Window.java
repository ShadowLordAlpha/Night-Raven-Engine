package com.shadowcs.nightraven.theia.window;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadowcs.nightraven.theia.Theia;
import com.shadowcs.nightraven.theia.monitor.Monitor;
import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class Window {
	
	private static final Logger logger = LoggerFactory.getLogger(Window.class);

	private EventManager windowEventManager;
	
	private long windowPointer;
	private int width;
	private int height;
	private String title;
	private Monitor monitor;
	
	Window(int width, int height, String title, Monitor monitor) {
		
		// Create window
		
		
		// Set window callbacks
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetWindowCloseCallback(windowPointer, this::WindowCloseCallback));
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetWindowFocusCallback(windowPointer, this::WindowFocusCallback));
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetFramebufferSizeCallback(windowPointer, this::WindowFramebufferSizeCallback));
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetWindowIconifyCallback(windowPointer, this::WindowIconifyCallback));
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetWindowPosCallback(windowPointer, this::WindowPositionCallback));
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetWindowRefreshCallback(windowPointer, this::WindowRefreshCallback));
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetWindowSizeCallback(windowPointer, this::WindowSizeCallback));
		GLFW.glfwPostEmptyEvent();
	}
	
	/*
	 *  GLFW window events. These include input being sent to the window
	 */
	
	private void WindowCloseCallback(long window) {
		logger.info("window close called");
	}
	
	private void WindowFocusCallback(long window, boolean focus) {
		logger.info("window focus called");
	}
	
	private void WindowFramebufferSizeCallback(long window, int x, int y) {
		logger.info("window framebuffer size called");
	}
	
	private void WindowIconifyCallback(long window, boolean iconify) {
		logger.info("window iconify called");
	}
	
	private void WindowPositionCallback(long window, int x, int y) {
		logger.info("window position called");
	}
	
	private void WindowRefreshCallback(long window) {
		logger.info("window refresh called");
	}
	
	private void WindowSizeCallback(long window, int width, int height) {
		logger.info("window size called");
	}
}
