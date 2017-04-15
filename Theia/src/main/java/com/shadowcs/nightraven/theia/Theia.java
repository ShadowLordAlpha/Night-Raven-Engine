package com.shadowcs.nightraven.theia;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadowcs.nightraven.theia.window.Window;
import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class Theia {
	
	private static final Logger logger = LoggerFactory.getLogger(Theia.class);
	
	private static boolean glfwInit;
	/*
	 * This is for things that must be thrown on the main thread. 
	 */
	private static EventManager mainEventManager;
	
	/*
	 * This one is for common things and throwing events
	 */
	private static EventManager eventManager;
	
	private Theia() {}
	
	public static Theia newBuilder() {
		
		if(!glfwInit) {
			Future<Procedure> f = mainEventManager.submitEvent(Procedure.class, () -> GLFW.glfwSetErrorCallback(Theia::ErrorCallback));
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f = mainEventManager.submitEvent(Procedure.class, Theia::InitilizeGlfw);
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Theia();
	}
	
	private static void ErrorCallback(int error, long description) {
		// TODO: log errors
	}
	
	private static void InitilizeGlfw() {
		if(!GLFW.glfwInit()) {
			// TODO: log error
			logger.error("Failed to initilize the GLFW library!");
			System.exit(-1);
		}
	}
	
	public static EventManager getMainEventManager() {
		return mainEventManager;
	}
	
	public static void setMainEventManager(EventManager mainEventManager) {
		 Theia.mainEventManager = mainEventManager;
	}
	
	public Window build() {
		return null;
	}
}
