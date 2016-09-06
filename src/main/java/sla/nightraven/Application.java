package sla.nightraven;

import java.io.File;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Application {

	@JsonProperty("title")
	private String title;

	@JsonProperty("sub_title")
	private String subTitle;
	
	@JsonProperty("icon_file")
	private File icon;

	@JsonProperty("version")
	private Version version;

	private Application() {

	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}
	
	public File getIcon() {
		return icon;
	}

	public Version getVersion() {
		return version;
	}

}
