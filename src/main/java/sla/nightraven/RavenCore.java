package sla.nightraven;

import java.io.IOException;
import org.lwjgl.system.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sla.nightraven.graphics.IGraphicsModule;
import sla.nightraven.graphics.phoebe.PhoebeModule;
import sla.nightraven.window.IWindow;
import sla.nightraven.window.IWindowModule;
import sla.nightraven.window.theia.TheiaModule;

public class RavenCore {
	
	private static final Logger LOG = LoggerFactory.getLogger(RavenCore.class);
	
	private static final String engineName = "Night-Raven";
	private static final Version engineVersion = new Version(0, 2, 0);

	private ShutdownHook shutdownHook = new ShutdownHook();
	
	public ObjectMapper mapper = new ObjectMapper();
	
	private Application application;
	// private IWindowModule windowModule;
	// private IGraphicsModule graphicsModule;
	
	public RavenCore() throws JsonParseException, JsonMappingException, IOException {
		
		LOG.debug("Starting Night-Raven Engine");
		
		LOG.debug("Loading game file");
		application = mapper.readValue(ClassLoader.getSystemResourceAsStream("Core/game.json"), Application.class);
		LOG.debug("Finished Loading game file");
		
		LOG.debug("Adding Shutdown hook");
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));
		LOG.debug("Shutdown hook added");
		
		LOG.debug("Initilizing Modules");
		
		try(IWindowModule windowModule = new TheiaModule(); IGraphicsModule graphicsModule = new PhoebeModule(application.getTitle(), application.getVersion())) {
			
			String title = application.getTitle() + ((application.getSubTitle() == null) ? "" : " - " + application.getSubTitle()) + " v" + application.getVersion().getVersionString();
			IWindow window = windowModule.newBuilder().setTitle(title).setIcon(application.getIcon()).build().setVisible(true);
			
			while(!window.shouldClose()) {
				windowModule.update();
				window.swapBuffers();
			}
			
		} catch(Exception e) {
			
		}
	}
	
	public static String getEngineName() {
		return engineName;
	}
	
	public static Version getEngineVersion() {
		return engineVersion;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// Configuration.DEBUG.set(true);
		// Configuration.DEBUG_LOADER.set(true);
		new RavenCore();
	}
}
