package com.shadowcs.nightraven.theia.monitor;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;

import com.shadowcs.nightraven.theia.Theia;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class Monitor {

	private static List<Monitor> monitorList = new ArrayList<Monitor>();
	
	private long monitor;
	private GammaRamp gammaramp;
	private VideoMode vidMode;
	
	static {
		Theia.getMainEventManager().executeEvent(Procedure.class, () -> GLFW.glfwSetMonitorCallback(Monitor::MonitorCallback));
		GLFW.glfwPostEmptyEvent();
	}
	
	private Monitor(long monitor) {
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (monitor ^ (monitor >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			if(obj instanceof Integer) {
				if(((Integer) obj).equals(monitor)) {
					return true;
				}
			}
			return false;
		}
		Monitor other = (Monitor) obj;
		if (monitor != other.monitor)
			return false;
		return true;
	}
	
	// Static Monitor Methods
	public static List<Monitor> getMonitors() {
		if(monitorList.isEmpty()) {
			List<PointerBuffer> pbl = new ArrayList<PointerBuffer>();
			Theia.getMainEventManager().executeEvent(Procedure.class, () -> pbl.set(0, GLFW.glfwGetMonitors()));
			GLFW.glfwPostEmptyEvent();
			
			PointerBuffer buffer = pbl.get(0);
			for(int i = 0; i < buffer.limit(); i++) {
				
				monitorList.add(new Monitor(buffer.get(i)));
			}
		}
		
		return monitorList;
	}
	
	private static void MonitorCallback(long monitor, int event) {
		if(event == GLFW.GLFW_DISCONNECTED) {
			monitorList.remove(monitor);
		} else if(event == GLFW.GLFW_CONNECTED) {
			monitorList.add(new Monitor(monitor));
		}
	}
}
