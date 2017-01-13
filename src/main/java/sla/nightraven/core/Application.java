package sla.nightraven.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.gson.annotations.Since;

public class Application {
	
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

	@Since(1.0)
	private String title;
	
	@Since(1.0)
	private String subTitle;
	
	@Since(1.0)
	private String version;
	
	private Application() {
		title = "NRx";
		subTitle = FORMAT.format(new Date());
		version = "0.0.0";
	}

	@Override
	public String toString() {
		return "Application [title=" + title + ", subTitle=" + subTitle + ", version=" + version + "]";
	}
}
