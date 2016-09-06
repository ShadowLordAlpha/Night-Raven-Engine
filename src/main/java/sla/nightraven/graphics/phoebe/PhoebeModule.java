package sla.nightraven.graphics.phoebe;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memUTF8;
import static org.lwjgl.vulkan.VK10.*;
import java.nio.LongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkDebugReportCallbackCreateInfoEXT;
import org.lwjgl.vulkan.VkDebugReportCallbackEXT;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(PhoebeModule.class);

	private String[] validationLayers = { "VK_LAYER_LUNARG_standard_validation"
	};

	VkDebugReportCallbackEXT debugCallback = VkDebugReportCallbackEXT.create((int flags, int objectType, long object, long location, int messageCode, long pLayerPrefix, long pMessage, long pUserData) -> {
		LOG.debug("{}: {}", memUTF8(pLayerPrefix), memUTF8(pMessage));
		return VK10.VK_FALSE;
	});
	VkInstance instance;

	public PhoebeModule(String appName, Version appVersion) {
		if(appName == null) {
			appName = "UNKNOWN";
		}

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

			VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.mallocStack(stack);
			createInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
			createInfo.pNext(NULL);
			createInfo.flags(0);
			createInfo.pApplicationInfo(appInfo);
			if(LOG.isDebugEnabled() && checkValidationLayerSupport(validationLayers)) {

				PointerBuffer layerNames = stack.callocPointer(validationLayers.length);
				for(String layer : validationLayers) {
					layerNames.put(stack.UTF8(layer));
				}
				layerNames.flip();
				createInfo.ppEnabledLayerNames(layerNames);
			} else {
				createInfo.ppEnabledLayerNames(null);
			}
			createInfo.ppEnabledExtensionNames(getRequiredExtensions());

			PointerBuffer instanceId = stack.callocPointer(1);
			vkCheck(vkCreateInstance(createInfo, null, instanceId), "Failed to Create Vulkan Instance");
			instance = new VkInstance(instanceId.get(0), createInfo);
			LOG.debug("VK_EXT_debug_report: {}", instance.getCapabilities().VK_EXT_debug_report);
			setupDebugCallback();

			VK10.vkDestroyInstance(instance, null);
		}
	}

	void setupDebugCallback() {
		if(!LOG.isDebugEnabled()) return;

		try(MemoryStack stack = MemoryStack.stackPush()) {
			VkDebugReportCallbackCreateInfoEXT createInfo = VkDebugReportCallbackCreateInfoEXT.callocStack(stack);
			createInfo.sType(EXTDebugReport.VK_STRUCTURE_TYPE_DEBUG_REPORT_CALLBACK_CREATE_INFO_EXT);
			createInfo.flags(EXTDebugReport.VK_DEBUG_REPORT_ERROR_BIT_EXT | EXTDebugReport.VK_DEBUG_REPORT_WARNING_BIT_EXT);
			createInfo.pfnCallback(debugCallback);
			LongBuffer pCallback = stack.callocLong(1);
			if(EXTDebugReport.vkCreateDebugReportCallbackEXT(instance, createInfo, null, pCallback) != VK10.VK_SUCCESS) {
				LOG.error("Failed to set up Debug Callback");
				// TODO: something like exit or whatnot
			}
		}
	}

	PointerBuffer getRequiredExtensions() {
		try(MemoryStack stack = MemoryStack.stackPush()) {

			PointerBuffer pointerBuffer = GLFWVulkan.glfwGetRequiredInstanceExtensions();

			if(LOG.isDebugEnabled()) {
				LOG.debug("adding debug report");
				PointerBuffer validationBuffer = stack.callocPointer(pointerBuffer.remaining() + 1);
				validationBuffer.put(pointerBuffer);
				validationBuffer.put(stack.UTF8("VK_EXT_debug_report")); // TODO: this probably should not be a direct
																			// string
				return validationBuffer;
			}

			return pointerBuffer;
		}
	}

	boolean checkValidationLayerSupport(String[] validationLayers) {

		try(MemoryStack stack = MemoryStack.stackPush()) {
			int[] pPropertyCount = new int[1];
			VK10.vkEnumerateInstanceLayerProperties(pPropertyCount, null);

			VkLayerProperties.Buffer pProperties = VkLayerProperties.callocStack(pPropertyCount[0], stack);
			VK10.vkEnumerateInstanceLayerProperties(pPropertyCount, pProperties);

			for(String layer : validationLayers) {

				boolean layerFound = false;

				for(int i = 0; i < pPropertyCount[0]; i++) {
					if(layer.equalsIgnoreCase(pProperties.get(i).layerNameString())) {
						LOG.debug("Validation Layer Found: {}", pProperties.get(i).layerNameString());
						layerFound = true;
						break;
					}
				}

				if(!layerFound) {
					LOG.warn("Validation Layer Requested but not Available: {}", layer);
					return false;
				}
			}
		}
		return true;
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
