package sla.nightraven;

import static org.lwjgl.system.MemoryUtil.memAllocPointer;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.nightraven.window.Window;
import sla.nightraven.window.WindowModule;

public class RavenCore implements Runnable {

	public static final Logger LOG = LoggerFactory.getLogger(RavenCore.class);

	private Application app;
	private Window window;

	public RavenCore(Application app) {
		this.app = app;

		WindowModule wm = new WindowModule();
		wm.setup();

		window = app.createCoreWindow();
		window.show();

		this.run();

		try {
			LOG.info("Closing Application.");
			window.close();
			wm.cleanup();
			app.close();
		} catch (Exception e) {
			LOG.warn("Ignoring uncaught closing exception: ", e);
		}
	}

	private void initFireGod() {
		MemoryStack stack = MemoryStack.stackPush();
		try {

			// Setup Application info
			VkApplicationInfo vkAppInfo = VkApplicationInfo.mallocStack(stack);
			vkAppInfo.sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO);
			vkAppInfo.pNext(MemoryUtil.NULL);
			vkAppInfo.pApplicationName(stack.UTF8("UNKNOWN"));
			vkAppInfo.applicationVersion(0); // TODO: Application Version
			vkAppInfo.pEngineName(stack.UTF8("Night-Raven"));
			vkAppInfo.engineVersion(0); // TODO: Engine Version
			vkAppInfo.apiVersion(VK10.VK_API_VERSION_1_0);

			// Instance creation info
			VkInstanceCreateInfo vkInstanceCreateInfo = VkInstanceCreateInfo.mallocStack(stack);
			vkInstanceCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
			vkInstanceCreateInfo.pNext(MemoryUtil.NULL);
			vkInstanceCreateInfo.flags(0);
			vkInstanceCreateInfo.pApplicationInfo(vkAppInfo);
			// We don't have enabledLayerCount?
			vkInstanceCreateInfo.ppEnabledLayerNames(null);
			// We don't have enabledExtensionCount?
			vkInstanceCreateInfo.ppEnabledExtensionNames(null);
			
			// I wish it was like this
			//VkInstance vkInstance = VkInstance.malloc();
			//int result = VK10.vkCreateInstance(vkInstanceCreateInfo, null, vkInstance);
			
			PointerBuffer pp = memAllocPointer(1);
			int result = VK10.vkCreateInstance(vkInstanceCreateInfo, null, pp);
			if(result != VK10.VK_SUCCESS) {
				LOG.error("Failed to create Vulkan Instance, Error Code: {}! ", result);
				// TODO: exit?
			}
			VkInstance vkInstance = new VkInstance(pp.get(0), vkInstanceCreateInfo);
			
			
			
			
			
			VK10.vkDestroyInstance(vkInstance, null);
		} finally {
			stack.pop();
		}

	}

	@Override
	public void run() {
		while (!window.shouldClose()) {
			GLFW.glfwPollEvents();
		}
	}
}
