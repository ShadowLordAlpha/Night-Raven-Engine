package com.shadowcs.nightraven.theia;

import org.lwjgl.glfw.GLFW;

import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class Theia {
	
	// TODO: add logger
	
	private static boolean glfwInit;
	private static EventManager mainEventManager;
	
	private Theia() {}
	
	public static Theia newBuilder() {
		
		if(!glfwInit) {
			mainEventManager.fireEventAsync(Procedure.class, () -> GLFW.glfwSetErrorCallback(Theia::ErrorCallback));
			mainEventManager.fireEventAsync(Procedure.class, Theia::InitilizeGlfw);
		}
		
		return new Theia();
	}
	
	private static void ErrorCallback(int error, long description) {
		// TODO: log errors
	}
	
	private static void InitilizeGlfw() {
		if(!GLFW.glfwInit()) {
			// TODO: log error
			System.exit(-1);
		}
	}
	
	public static void setMainEventManager() {
		
	}
}
