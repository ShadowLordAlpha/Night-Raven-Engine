package sla.nightraven.window;

import java.io.File;
import sla.nightraven.IBuilder;
import sla.nightraven.util.function.ObjIntIntConsumer;

public interface IWindowBuilder extends IBuilder<IWindow> {

	public String getTitle();

	public IWindowBuilder setTitle(String title);
	
	public boolean getDecorated();
	
	public IWindowBuilder setDecorated(boolean decorated);
	
	public File getIcon();
	
	public IWindowBuilder setIcon(File icon);
	
	// ---------------------------------
	
	public boolean getFullscreen();
	
	public IWindowBuilder setFullscreen(boolean fullscreen);
	
	public int getWidth();
	
	public IWindowBuilder setWidth(int width);
	
	public int getHeight();
	
	public IWindowBuilder setHeight(int height);
	
	public ObjIntIntConsumer<IWindow> getSizeCallback(); // TODO: this needs a better name
	
	public IWindowBuilder setSizeCallback(ObjIntIntConsumer<IWindow> callback); // TODO: as does this one
	
	public ObjIntIntConsumer<IWindow> getPixelSizeCallback(); // TODO: and this one
	
	public IWindowBuilder setPixelSizeCallback(ObjIntIntConsumer<IWindow> callback); // TODO: I need more things to say to rename these
}
