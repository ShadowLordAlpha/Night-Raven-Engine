package sla.nightraven.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sla.nightraven.theia.TheiaModule;
import sla.nightraven.theia.Window;

public class RavenCore {
	
	private static final Logger LOG = LoggerFactory.getLogger(RavenCore.class);
	
	public RavenCore() {
		LOG.debug("Starting Night-Raven Engine");
		
		GsonBuilder builder = new GsonBuilder();
		builder.setVersion(1.0);
		builder.setPrettyPrinting();
		
		Gson gson = builder.create();
		
		// TODO: need to setup all needed files beforehand or make my own version of creating all needed folders as well as the files
		
		LOG.debug("Loading Application file");
		
		Reader json;
		try {
			json = new FileReader("Data/Core/application.json");
		} catch(FileNotFoundException e) {
			LOG.warn("Application File Not Found:", e);
			json = new StringReader("{}");
		}
		
		Application application = gson.fromJson(json, Application.class);
		
		LOG.info(application.toString());
		
		try {
			Files.write(Paths.get("Data", "Core", "application.json"), Arrays.asList(gson.toJson(application).split("\n")), Charset.forName("UTF-8"));
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TheiaModule.init();
		Window window = new Window();
		for(int i = 0; i < 120; i++) {
			TheiaModule.pollEvents();
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TheiaModule.close();
	}

	public static void main(String... args) {
		new RavenCore();
	}
}
