package com.shadowcs.nightraven.theia.event;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.lwjgl.glfw.GLFW;
import com.shadowcs.nightraven.themis.util.function.Procedure;
import sla.nightraven.theia.TheiaModule;

public class PollEvent implements Procedure {
	
	private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
	
	@Override
	public void invoke() {
		GLFW.glfwWaitEvents();
		System.out.println("EVENT!");
		executor.schedule(() -> TheiaModule.getInstance().fireEvent(this), 15, TimeUnit.MILLISECONDS);
	}
}
