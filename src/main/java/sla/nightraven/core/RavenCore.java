package sla.nightraven.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.ProvidedSingleThreadFactory;
import com.shadowcs.nightraven.themis.Themis;
import com.shadowcs.nightraven.themis.util.function.Procedure;
import sla.nightraven.theia.TheiaModule;
import sla.nightraven.theia.Window;
import sla.nightraven.theia.WindowProperties;

public class RavenCore {

	private static final Logger LOG = LoggerFactory.getLogger(RavenCore.class);
	
	/**
	 * This exsists to handle events that must use the main thread in order to execute
	 */
	private EventManager mainThreadEventManager;
	private Gson gson;
	private ProvidedSingleThreadFactory mainThreadFactory = new ProvidedSingleThreadFactory();
	

	public RavenCore() {
		
		new Thread(this::MainLoop, "core").start();
		mainThreadFactory.cede();
		// Main thread control returned
		System.err.println("Main Thread Returned!");
	}

	private void MainLoop() {
		LOG.debug("Starting Night-Raven Engine");

		GsonBuilder builder = new GsonBuilder();
		builder.setVersion(1.0);
		builder.setPrettyPrinting();

		gson = builder.create();
		
		mainThreadEventManager = Themis.newBuilder().setExecutorService(Executors.newSingleThreadExecutor(mainThreadFactory)).build();
		mainThreadEventManager.addListener(Procedure.class, (pro) -> pro.invoke());
		
		TheiaModule.setMainEventHandler(mainThreadEventManager);
		TheiaModule.getInstance();
		
		Application application = loadJson("Data/Core/application.json", Application.class);
		WindowProperties properties = loadJson("Data/Core/window.json", WindowProperties.class);
		properties.setTitle(String.format("%s: %s - %s", application.getTitle(), application.getSubTitle(), application.getVersion()));
		
		Window window = new Window(properties);
		window.show();

		while(true) {
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			LOG.info("test Loops");
		}
	}

	public static void main(String... args) {
		new RavenCore();
	}

	private <T> T loadJson(String file, Class<T> clazz) {
		File filef = new File(file);
		filef.mkdirs();
		try {
			filef.createNewFile();
		} catch(IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Reader json;
		try {
			json = new FileReader(file);
		} catch(FileNotFoundException e) {
			LOG.warn("Application File Not Found:", e);
			json = new StringReader("{}");
		}

		T obj = gson.fromJson(json, clazz);

		try {
			Files.write(Paths.get(file), Arrays.asList(gson.toJson(obj).split("\n")), Charset.forName("UTF-8"));
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}
}
