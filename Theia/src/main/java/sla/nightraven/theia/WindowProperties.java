package sla.nightraven.theia;

public class WindowProperties {
	
	private WindowMode windowMode;
	private int width;
	private int height;
	private transient String title;
	// TODO: window icon?
	
	
	public WindowProperties() {
		setWindowMode(WindowMode.FULLSCREEN);
		setWidth(640);
		setHeight(480);
		setTitle("Night-Raven Engine");
	}


	public WindowMode getWindowMode() {
		return windowMode;
	}


	public void setWindowMode(WindowMode windowMode) {
		this.windowMode = windowMode;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
}
