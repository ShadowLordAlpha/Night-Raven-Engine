package sla.nightraven.graphics.phoebe;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.VK10.*;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import sla.nightraven.RavenCore;
import sla.nightraven.Version;
import sla.nightraven.graphics.IGraphicsModule;

/**
 * 
 * WARNING: this module is built to work specifically with GLFW and the Theia module as they may be partially dependent.
 * This may be fixed at some point along with Input data however it is unlikely that it will be fixed for the time being
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class PhoebeModule implements IGraphicsModule {
	
	VkInstance instance;

	public PhoebeModule(String appName, Version appVersion) {
		if(appVersion == null) {
			appVersion = new Version(0);
		}
		
		try(MemoryStack stack = MemoryStack.stackPush()) {

			VkApplicationInfo appInfo = VkApplicationInfo.mallocStack(stack);
			appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);
			appInfo.pNext(NULL);
			appInfo.pApplicationName(stack.UTF8(appName));
			appInfo.applicationVersion(appVersion.getVersion());
			appInfo.pEngineName(stack.UTF8(RavenCore.getEngineName()));
			appInfo.engineVersion(RavenCore.getEngineVersion().getVersion());
			appInfo.apiVersion(VK_API_VERSION_1_0);

			VkInstanceCreateInfo instanceInfo = VkInstanceCreateInfo.mallocStack(stack);
			instanceInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
			instanceInfo.pNext(NULL);
			instanceInfo.flags(0);
			instanceInfo.pApplicationInfo(appInfo);
			instanceInfo.ppEnabledLayerNames(null);
			instanceInfo.ppEnabledExtensionNames(null);

			
			
			PointerBuffer instanceId = stack.callocPointer(1);
			vkCheck(vkCreateInstance(instanceInfo, null, instanceId), "Failed to Create Vulkan Instance");
			instance = new VkInstance(instanceId.get(0), instanceInfo);
			
			 VK10.vkDestroyInstance(instance, null);
		}
	}

	public static void vkCheck(int result, String code) {
		// TODO change this to something better
		assert result == VK_SUCCESS : code;
	}

	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}
}
