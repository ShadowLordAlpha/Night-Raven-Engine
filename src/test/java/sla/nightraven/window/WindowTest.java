package sla.nightraven.window;

import org.junit.Test;
import sla.nightraven.window.theia.TheiaModule;

public class WindowTest {

	@Test
	public void windowCreateTest() throws InterruptedException {
		IWindowModule module = new TheiaModule();
		IWindow window = module.newBuilder().setTitle("Window Create Test #1").build().setVisible(true);
		window.setTitle("Window Create Test #2");
	}
}
