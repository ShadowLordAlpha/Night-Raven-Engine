package sla.nightraven.theia;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import com.shadowcs.nightraven.themis.EventManager;

public class Window {
	
	 private long window;
	 private long monitor;
	 private int width;
	 private int height;
	 
	 private boolean closeRequested = false;
	 
	 private EventManager manager; // ?
	
	public Window(WindowProperties properties) {
		GLFW.glfwDefaultWindowHints();
		
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API); // TODO: maybe allow this to change though i doubt it
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		
		GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
		
		width = mode.width();
		height = mode.height();
		monitor = MemoryUtil.NULL;
		
		switch(properties.getWindowMode()) {
			case FULLSCREEN:
				GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
				monitor = GLFW.glfwGetPrimaryMonitor(); // TODO: maybe allow a monitor other than the primary
				break;
			case WINDOWED:
				GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
				
				width = properties.getWidth();
				height = properties.getHeight();
				
				break;
			case WINDOWED_FULLSCREEN:
				GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
				// GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
				break;
			default:
				break;
		}
		
		System.out.println("Create Window");
		TheiaModule.getInstance().fireEvent(() -> window = GLFW.glfwCreateWindow(width, height, properties.getTitle(), monitor, MemoryUtil.NULL));
		GLFW.glfwPostEmptyEvent();
		// TODO: need window pointer verified
		
		// Window Specific callbacks
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetWindowCloseCallback(window, this::windowCloseCallback));
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetWindowSizeCallback(window, this::windowSizeCallback));
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetFramebufferSizeCallback(window, this::windowFramebufferSizeCallback));
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetWindowPosCallback(window, this::windowPosCallback));
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetWindowIconifyCallback(window, this::windowIconifyCallback));
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetWindowFocusCallback(window, this::windowFocusCallback));
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwSetWindowRefreshCallback(window, this::windowRefreshCallback)); // I don't think I actually need this one
		GLFW.glfwPostEmptyEvent(); // This is needed so that the thread will actually advance and these events get used
	}
	
	public void show() {
		System.out.println("Show Window");
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwShowWindow(window));
		GLFW.glfwPostEmptyEvent();
	}
	
	public void hide() {
		System.out.println("Hide Window");
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwHideWindow(window));
		GLFW.glfwPostEmptyEvent();
	}
	
	public void close() {
		// Should probably change this to something else
		TheiaModule.getInstance().fireEvent(() -> GLFW.glfwDestroyWindow(window));
		GLFW.glfwPostEmptyEvent();
	}
	
	// Window Callbacks
	
	private void windowCloseCallback(long window) {
		// TODO
		System.out.println("windowCloseCallback");
	}
	
	private void windowSizeCallback(long window, int width, int height) {
		// TODO
		System.out.println("windowSizeCallback");
	}
	
	private void windowFramebufferSizeCallback(long window, int width, int height) {
		// TODO
		System.out.println("windowFramebufferSizeCallback");
	}
	
	private void windowPosCallback(long window, int x, int y) {
		// TODO
		System.out.println("windowPosCallback");
	}
	
	private void windowIconifyCallback(long window, boolean iconified) {
		// TODO
		System.out.println("windowIconifyCallback");
	}
	
	private void windowFocusCallback(long window, boolean focused) {
		// TODO
		System.out.println("windowFocusCallback");
	}
	
	private void windowRefreshCallback(long window) {
		// TODO
		System.out.println("windowRefreshCallback");
	}
}
