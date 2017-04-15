package com.shadowcs.nightraven.core;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shadowcs.nightraven.theia.Theia;
import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.ProvidedThreadFactory;
import com.shadowcs.nightraven.themis.Themis;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class NightRaven {
	
	private static final Logger logger = LoggerFactory.getLogger(NightRaven.class);
	
	private static Gson gson;
	
	// The following two objects take command of the programs main thread
	private static EventManager eventManager;
	private static ProvidedThreadFactory threadFactory;
	
	private NightRaven() {}

	public static void main(String... args) {
		
		logger.info("Night-Raven Engine Starting");
		
		threadFactory = new ProvidedThreadFactory();
		new Thread(NightRaven::CoreThread, "core").start();
		
		logger.debug("Ceding main thread");
		
		threadFactory.cede();
		
		logger.error("Main Thread returned");
	}
	
	private static void CoreThread() {
		
		logger.info("Starting core thread");
		
		// Wait for cede to be called on the main thread
		threadFactory.isReady();
		
		// The main thread will always simply run procedure type events
		getMainEventManager().addListener(Procedure.class, (procedure) -> procedure.invoke());
		Theia.setMainEventManager(eventManager);
		
		
		
	}
	
	// TODO: maybe use delate methods instead of returning the objects
	
	public static Gson getGson() {
		if(gson == null) {
			gson = new GsonBuilder().setPrettyPrinting().create();
		}
		
		return gson;
	}
	
	public static EventManager getMainEventManager() {
		if(eventManager == null) {
			eventManager = Themis.newBuilder().setExecutorService(Executors.newSingleThreadExecutor(threadFactory)).build();
		}
		
		return eventManager;
	}
}
