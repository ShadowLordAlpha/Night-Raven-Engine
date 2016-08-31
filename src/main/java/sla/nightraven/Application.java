package sla.nightraven;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Application {

	@JsonProperty("title")
	private String title;

	@JsonProperty("sub_title")
	private String subTitle;

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

	public Version getVersion() {
		return version;
	}

}
