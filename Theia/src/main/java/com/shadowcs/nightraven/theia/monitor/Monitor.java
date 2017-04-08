package com.shadowcs.nightraven.theia.monitor;

import org.lwjgl.glfw.GLFW;

public class Monitor {

	private long monitor;
	
	static {
		GLFW.glfwSetMonitorCallback(Monitor::monitorCallback);
	}
	
	Monitor() {
		
	}
	
	// Static Monitor Methods
	
	private static void monitorCallback(long monitor, int event) {
	
	}
}
