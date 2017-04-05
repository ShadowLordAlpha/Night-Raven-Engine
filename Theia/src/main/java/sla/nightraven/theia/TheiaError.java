package sla.nightraven.theia;

public class TheiaError {

	private int error;
	private String description;

	public TheiaError(int error, String description) {
		this.error = error;
		this.description = description;
	}

	public int getError() {
		return error;
	}

	public String getDescription() {
		return description;
	}
}
