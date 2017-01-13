package sla.nightraven.themis.test;

import org.junit.Test;
import sla.nightraven.themis.event.EventManager;

public class EventTest {

	@Test
	public void testRegisterEvent() {
		
		EventManager manager = new EventManager();
		manager.registerEvent(getClass());
		manager.addListener(EventTest.class, EventTest::listener);
		
	}
	
	private static void listener(EventTest t) {
		
	}
}
