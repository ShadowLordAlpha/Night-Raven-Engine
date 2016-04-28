package sla.nightraven.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;

/**
 * Set up the Window System that Night-Raven uses.
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public class WindowModule {

	private GLFWErrorCallback errorCb;
	
	public WindowModule() {
		
	}

	public void setup() {

		// Create and set a method for errors.
		errorCb = GLFWErrorCallback.create((int error, long description) -> {
			System.err.printf("GLFW Error {}: {}\n", error, description);
		});
		GLFW.glfwSetErrorCallback(errorCb);

		// Initialize GLFW
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to Initialize GLFW!");
		}

		if (!GLFWVulkan.glfwVulkanSupported()) {
			throw new IllegalStateException("Cannot find a compatible Vulkan installable client driver (ICD)!");
		}
	}

	public void cleanup() {
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
}
