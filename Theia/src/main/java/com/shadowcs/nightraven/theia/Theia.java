package com.shadowcs.nightraven.theia;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.lwjgl.glfw.GLFW;

import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class Theia {
	
	private static Object errorCallbackLock = new Object();
	private static boolean errorCallback = false;
	
	private static Object initLock = new Object();
	private static boolean init = false;
	
	private EventManager mainEventManager;
	private String title;
	
	private Theia() {}
	
	EventManager getMainEventManager() {
		return this.mainEventManager;
	}
	
	public Theia setMainEventManager(EventManager mainEventManager) {
		this.mainEventManager = mainEventManager;
		synchronized(errorCallbackLock) {
			if(!errorCallback) {
				Future<Procedure> future = mainEventManager.submitEvent(Procedure.class, () -> GLFW.glfwSetErrorCallback(Theia::GlfwErrorCallback));
				try {
					future.get();
				} catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				errorCallback = true;
			}
		}
		synchronized(initLock) {
			if(!init) {
				Future<Procedure> future = mainEventManager.submitEvent(Procedure.class, () -> GLFW.glfwInit());
				try {
					future.get();
				} catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				init = true;
			}
		}
		return this;
	}
	
	public Theia setTitle(String title) {
		this.title = title;
		return this;
	}
	
	String getTitle() {
		return (this.title == null) ? "Night-Raven Engine" : this.title;
	}
	
	public Window build() {
		return new Window(getMainEventManager(), getTitle());
	}
	
	public static Theia newBuilder() {
		return new Theia();
	}
	
	private static void GlfwErrorCallback(int error, long description) {
		
	}
}
