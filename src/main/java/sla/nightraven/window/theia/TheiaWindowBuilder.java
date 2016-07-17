package sla.nightraven.window.theia;

import java.io.File;
import sla.nightraven.util.function.ObjIntIntConsumer;
import sla.nightraven.window.IWindow;
import sla.nightraven.window.IWindowBuilder;

public class TheiaWindowBuilder implements IWindowBuilder {

	private String title;
	private boolean decorated = true;
	private File icon;
	private boolean fullscreen; // TODO: support multiple monitors
	private int width = 640;
	private int height = 480;

	public TheiaWindowBuilder() {

	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public TheiaWindowBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public boolean getDecorated() {
		return this.decorated;
	}

	@Override
	public IWindowBuilder setDecorated(boolean decorated) {
		this.decorated = decorated;
		return this;
	}
	
	public File getIcon() {
		return this.icon;
	}
	
	public IWindowBuilder setIcon(File icon) {
		this.icon = icon;
		return this;
	}
	
	
	
	@Override
	public boolean getFullscreen() {
		return this.fullscreen;
	}

	@Override
	public IWindowBuilder setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public IWindowBuilder setWidth(int width) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public IWindowBuilder setHeight(int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjIntIntConsumer<IWindow> getSizeCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindowBuilder setSizeCallback(ObjIntIntConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjIntIntConsumer<IWindow> getPixelSizeCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindowBuilder setPixelSizeCallback(ObjIntIntConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IWindow build() {
		// TODO Auto-generated method stub
		return new TheiaWindow(this);
	}
}
