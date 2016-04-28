package sla.nightraven;

import sla.nightraven.window.Window;
import sla.nightraven.window.WindowBuilder;

public class Yirath implements Application {

	public static void main(String... args) {
		new RavenCore(new Yirath());
	}
	
	@Override
	public Window createCoreWindow() {
		return new WindowBuilder().setTitle("Yirath").build();
	}
}
