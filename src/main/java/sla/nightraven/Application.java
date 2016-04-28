package sla.nightraven;

import sla.nightraven.window.Window;
import sla.nightraven.window.WindowBuilder;

public interface Application extends AutoCloseable {
	
	public default Window createCoreWindow() {
		return new WindowBuilder().build();
	}
	
	@Override
	public default void close() throws Exception {
		
	}
}
