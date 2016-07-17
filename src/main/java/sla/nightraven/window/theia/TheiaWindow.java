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
package sla.nightraven.window.theia;

import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import sla.nightraven.util.function.ObjBoolConsumer;
import sla.nightraven.util.function.ObjIntIntConsumer;
import sla.nightraven.window.IWindow;
import sla.nightraven.window.IWindowBuilder;

/**
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public class TheiaWindow implements IWindow {

	private static final String DEFAULT_TITLE = "Night-Raven Engine";

	private final long window;

	private String title;
	private boolean decorated;
	private boolean visible;
	private File icon;
	private boolean fullscreen;
	private int width;
	private int height;

	public TheiaWindow(IWindowBuilder builder) {

		// TODO: set all properties from builder
		title = (builder.getTitle() == null) ? DEFAULT_TITLE : builder.getTitle();
		decorated = builder.getDecorated();
		visible = false;
		icon = builder.getIcon(); // TODO: replace this with a good icon
		long monitor = NULL;
		if(builder.getFullscreen()) {
			monitor = glfwGetPrimaryMonitor();
			fullscreen = true;
		}
		width = (builder.getWidth() <= 0) ? 640 : builder.getWidth();
		height = (builder.getHeight() <= 0) ? 480 : builder.getHeight();

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		if(!decorated) {
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
		}
		if(fullscreen) {
			// TODO: fullscreen stuff?
		}
		window = glfwCreateWindow(width, height, title, monitor, NULL);
		setIcon(icon); // TODO: catch this exception

		// This is where the callbacks are setup
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public IWindow setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(window, title);
		return this;
	}

	@Override
	public boolean getDecorated() {
		return this.decorated;
	}

	@Override
	public boolean getVisible() {
		return this.visible;
	}

	@Override
	public IWindow setVisible(boolean visible) {
		if(this.visible == visible) {
			return this;
		} else if(visible) {
			glfwShowWindow(window);
		} else {
			glfwHideWindow(window);
		}

		this.visible = visible;
		return this;
	}

	@Override
	public File getIcon() {
		return this.icon;
	}

	@Override
	public IWindow setIcon(File icon) {

		this.icon = icon;
		if(icon == null) {
			glfwSetWindowIcon(window, null);
			return this;
		}

		// TODO: add support for ICO files?
		try(MemoryStack stack = MemoryStack.stackPush()) {
			GLFWImage.Buffer imageBuffer = GLFWImage.callocStack(1, stack);
			IntBuffer x = stack.callocInt(1);
			IntBuffer y = stack.callocInt(1);
			IntBuffer comp = stack.callocInt(1);

			ByteBuffer pixleData = STBImage.stbi_load(icon.getAbsolutePath(), x, y, comp, 4);
			if(pixleData == null) {
				throw new IllegalArgumentException("Failed to load image data: " + STBImage.stbi_failure_reason());
			} else {
				imageBuffer.get().set(x.get(0), y.get(0), pixleData);
				glfwSetWindowIcon(window, imageBuffer.flip());
				STBImage.stbi_image_free(pixleData);
			}
		}

		return this;
	}

	@Override
	public boolean getFullscreen() {
		return this.fullscreen;
	}

	@Override
	public IWindow setFullscreen(boolean fullscreen) {
		if(this.fullscreen == fullscreen) {
			return this;
		} else if(fullscreen) {
			// TODO ??
		} else {
			// TODO ??
		}

		this.fullscreen = fullscreen;

		return this;
	}

	@Override
	public IWindow center() {
		if(fullscreen) { return this; }

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, ((vidmode.width() / 2) - (this.width / 2)), ((vidmode.height() / 2) - (this.height / 2)));

		return this;
	}

	@Override
	public IWindow setSize(int width, int height) {
		this.width = width;
		this.height = height;
		glfwSetWindowSize(window, width, height);
		return this;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}

	@Override
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	@Override
	public void close() {
		glfwDestroyWindow(window);
	}

	@Override
	public boolean supportPositionCallback() {
		return true;
	}

	@Override
	public ObjIntIntConsumer<IWindow> getPositionCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setPositionCallback(ObjIntIntConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportSizeCallback() {
		return true;
	}

	@Override
	public ObjIntIntConsumer<IWindow> getSizeCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setSizeCallback(ObjIntIntConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportCloseCallback() {
		return true;
	}

	@Override
	public Consumer<IWindow> getCloseCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setCloseCallback(Consumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportRefreshCallback() {
		return true;
	}

	@Override
	public Consumer<IWindow> getRefreshCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setRefreshCallback(Consumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportFocusCallback() {
		return true;
	}

	@Override
	public ObjBoolConsumer<IWindow> getFocusCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setFocusCallback(ObjBoolConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportIconifyCallback() {
		return true;
	}

	@Override
	public ObjBoolConsumer<IWindow> getIconifyCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setIconifyCallback(ObjBoolConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportFrameBufferSizeCallback() {
		return true;
	}

	@Override
	public ObjIntIntConsumer<IWindow> getFrameBufferSizeCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWindow setFrameBufferSizeCallback(ObjIntIntConsumer<IWindow> callback) {
		// TODO Auto-generated method stub
		return null;
	}
}
