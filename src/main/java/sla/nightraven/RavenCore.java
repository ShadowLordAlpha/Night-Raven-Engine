package sla.nightraven;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sla.nightraven.graphics.IGraphicsModule;
import sla.nightraven.graphics.phoebe.PhoebeModule;
import sla.nightraven.window.IWindow;
import sla.nightraven.window.IWindowModule;
import sla.nightraven.window.theia.TheiaModule;

public class RavenCore {
	
	private static final Logger LOG = LoggerFactory.getLogger(RavenCore.class);
	
	private static final String engineName = "Night-Raven";
	private static final Version engineVersion = new Version(0, 1, 0);

	private ShutdownThread shutdownThread;
	
	private IWindowModule windowModule;
	private IGraphicsModule graphicsModule;
	
	public RavenCore() {
		
		LOG.debug("Starting Night-Raven Engine");
		
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownThread = new ShutdownThread(), "shutdown"));
		LOG.debug("Shutdown Hook Added");
		
		// TODO: load base properties
		
		// TODO: load saved properties
		
		// TODO: possibly allow for modules other than the defaults?
		windowModule = new TheiaModule();
		//graphicsModule = new PhoebeModule("", null);
		
		
		IWindow windowCore = windowModule.newBuilder().setTitle("UCGO - R").build().center().setVisible(true);
		while(!windowCore.shouldClose()) {
			
			
			
			try {
				windowModule.update();
				//graphicsModule.update();
				windowCore.swapBuffers();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getEngineName() {
		return engineName;
	}
	
	public static Version getEngineVersion() {
		return engineVersion;
	}
}
