package com.shadowcs.nightraven.theia;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class Window {
	
	private EventManager mainEventManager;
	private long window;
	
	Window(EventManager mainEventManager, String title) {
		
		this.mainEventManager = mainEventManager;
		
		mainEventManager.submitEvent(Procedure.class, () -> window = GLFW.glfwCreateWindow(640, 480, title, 0L, MemoryUtil.NULL));
		// TODO
		
		mainEventManager.executeEvent(Procedure.class, () -> GLFW.glfwShowWindow(window));

		// TODO: setup all window callbacks
		
		// TODO: better way of doing this
		mainEventManager.executeEvent(Procedure.class, this::WaitEvent);
	}
	
	

	private final void WaitEvent() {
		GLFW.glfwWaitEventsTimeout(0.5);
		mainEventManager.executeEvent(Procedure.class, this::WaitEvent);
	}
}
