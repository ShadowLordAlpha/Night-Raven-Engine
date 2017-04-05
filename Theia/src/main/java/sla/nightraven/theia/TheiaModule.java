package sla.nightraven.theia;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import com.shadowcs.nightraven.theia.event.PollEvent;
import com.shadowcs.nightraven.themis.EventManager;
import com.shadowcs.nightraven.themis.util.function.Procedure;

public class TheiaModule implements AutoCloseable {

	private static boolean errorCBSet = false;
	private static TheiaModule instance;
	private static EventManager staticEventManager;
	private static EventManager eventQueue; // This is the main thread
	private boolean init = false; // basically useless

	/**
	 * Private constructor to prevent instancing of this class from the outside
	 */
	private TheiaModule() {
		if(!errorCBSet) {
			staticEventManager = new EventManager();
			fireEvent(() -> GLFW.glfwSetErrorCallback(TheiaModule::GlfwErrorCallback));
		}

		
		Future<Procedure> f = fireEvent(() -> init = GLFW.glfwInit());
		try {
			init = false;
			f.get();
		} catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!init) {
			staticEventManager.fireEvent(new TheiaError(-1, "Failed to initilize GLFW!"));
		}
		
		fireEvent(new PollEvent());
	}

	public <T> Future<T> fireEvent(T event) {
		return eventQueue.fireEventAsync(event);
	}
	
	public <T> Future<T> fireEvent(Class<T> clazz, T event) {
		return eventQueue.fireEventAsync(clazz, event);
	}
	
	public Future<Procedure> fireEvent(Procedure event) {
		return fireEvent(Procedure.class, event);
	}

	@Override
	public void close() throws Exception {
		GLFW.glfwTerminate();
		instance = null;
	}
	
	public static synchronized void setMainEventHandler(EventManager mainThreadEventManager) {
		eventQueue = mainThreadEventManager;
	}

	public static synchronized TheiaModule getInstance() {
		if(TheiaModule.instance == null) {
			TheiaModule.instance = new TheiaModule();
		}

		return TheiaModule.instance;
	}

	private static void GlfwErrorCallback(int error, long description) {
		staticEventManager.fireEvent(new TheiaError(error, MemoryUtil.memUTF8(description)));
	}

	public static void addErrorListener(Consumer<TheiaError> listener) {
		staticEventManager.addListener(TheiaError.class, listener);
	}

	public static void removeErrorListener(Consumer<TheiaError> listener) {
		staticEventManager.removeListener(TheiaError.class, listener);
	}
}
