/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package sla.nightraven.window;

import java.io.File;
import java.util.function.Consumer;
import sla.nightraven.util.function.ObjBoolConsumer;
import sla.nightraven.util.function.ObjIntIntConsumer;

/**
 * TODO: comment
 * 
 * @author Josh "ShadowLordAlpha"
 */
public interface IWindow extends AutoCloseable {

	public String getTitle();

	public IWindow setTitle(String title);

	public boolean getDecorated();

	public boolean getVisible();

	public IWindow setVisible(boolean visible);

	public File getIcon();

	public IWindow setIcon(File icon);
	
	public boolean getFullscreen();

	public IWindow setFullscreen(boolean fullscreen);
	
	public IWindow center();
	
	public int getWidth();
	
	public IWindow setSize(int width, int height);
	
	public default IWindow setWidth(int width) {
		return setSize(width, getHeight());
	}
	
	public int getHeight();
	
	public default IWindow setHeight(int height) {
		return setSize(getWidth(), height);
	}
	
	public void swapBuffers();
	
	public boolean shouldClose();
	
	@Override
	public void close();
	
	// --< Callback Methods >--
	// Instead of throwing an exception these methods will do nothing if not supported
	
	public boolean supportPositionCallback();
	
	public ObjIntIntConsumer<IWindow> getPositionCallback();
	
	public IWindow setPositionCallback(ObjIntIntConsumer<IWindow> callback);
	
	public boolean supportSizeCallback();
	
	public ObjIntIntConsumer<IWindow> getSizeCallback();
	
	public IWindow setSizeCallback(ObjIntIntConsumer<IWindow> callback);
	
	public boolean supportCloseCallback();
	
	public Consumer<IWindow> getCloseCallback();
	
	public IWindow setCloseCallback(Consumer<IWindow> callback);
	
	public boolean supportRefreshCallback();
	
	public Consumer<IWindow> getRefreshCallback();
	
	public IWindow setRefreshCallback(Consumer<IWindow> callback);
	
	public boolean supportFocusCallback();
	
	public ObjBoolConsumer<IWindow> getFocusCallback();
	
	public IWindow setFocusCallback(ObjBoolConsumer<IWindow> callback);
	
	public boolean supportIconifyCallback();
	
	public ObjBoolConsumer<IWindow> getIconifyCallback();
	
	public IWindow setIconifyCallback(ObjBoolConsumer<IWindow> callback);
	
	public boolean supportFrameBufferSizeCallback();
	
	public ObjIntIntConsumer<IWindow> getFrameBufferSizeCallback();
	
	public IWindow setFrameBufferSizeCallback(ObjIntIntConsumer<IWindow> callback);
}
