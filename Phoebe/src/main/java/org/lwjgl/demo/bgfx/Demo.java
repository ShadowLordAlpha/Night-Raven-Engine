/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.bgfx;

import org.lwjgl.bgfx.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;
import org.lwjgl.system.libc.LibCStdio;
import sla.nightraven.theia.TheiaModule;
import sla.nightraven.theia.window.Window;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.bgfx.BGFX.*;
import static org.lwjgl.bgfx.BGFXPlatform.bgfx_set_platform_data;
import static org.lwjgl.system.APIUtil.apiLog;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Abstract base class for all bgfx examples.
 */
public abstract class Demo {

	private String title;

	protected int renderer = BGFX_RENDERER_TYPE_COUNT;
	private int pciId = BGFX_PCI_ID_NONE;
	private int width = 1280;
	private int height = 720;
	private int debug = BGFX_DEBUG_TEXT;
	private int reset = BGFX_RESET_VSYNC;

	private boolean useCallbacks;
	private boolean useCustomAllocator;

	protected Demo(String title) {
		this.title = title;
	}

	protected abstract void create() throws IOException;

	protected abstract void frame(float time, float frameTime);

	protected abstract void dispose();

	protected void run(String[] args) {

		parseArgs(args);

		try (MemoryStack stack = MemoryStack.stackPush()) {

			Window window = new Window.Builder().setWidth(width).setHeight(height).setTitle(title).build();

			glfwSetFramebufferSizeCallback(window, this::resize);

			BGFXPlatformData platformData = BGFXPlatformData.callocStack(stack);

			platformData.ndt(TheiaModule.getNativeDisplayType());
			platformData.nwh(window.getNativeWindowHandle());
			platformData.context(NULL);
			platformData.backBuffer(NULL);
			platformData.backBufferDS(NULL);

			bgfx_set_platform_data(platformData);

			BGFXDemoUtil.reportSupportedRenderers();

			/* Initialize bgfx */

			if (!bgfx_init(renderer,
					pciId,
					0,
					useCallbacks ? createCallbacks(stack) : null,
					useCustomAllocator ? createAllocator(stack) : null)) {

				throw new RuntimeException("Error initializing bgfx renderer");
			}

			if (renderer == BGFX_RENDERER_TYPE_COUNT) {
				renderer = bgfx_get_renderer_type();
			}

			String rendererName = bgfx_get_renderer_name(renderer);
			if ("NULL".equals(rendererName)) {
				throw new RuntimeException("Error identifying bgfx renderer");
			}
			
			System.out.println("bgfx: using renderer '" + rendererName + "'");
			apiLog("bgfx: using renderer '" + rendererName + "'");

			BGFXDemoUtil.configure(renderer);

			bgfx_reset(width, height, reset);

			bgfx_set_debug(debug);

			bgfx_set_view_clear(0,
					BGFX_CLEAR_COLOR | BGFX_CLEAR_DEPTH,
					0x303030ff,
					1.0f,
					0);

			/* Initialize demo specific code */

			try {
				create();
			} catch (IOException e) {
				throw new RuntimeException("Error initializing bgfx demo", e);
			}

			/* Application loop */

			long lastTime;
			long startTime = lastTime = TheiaModule.getTimerValue();

			while (!window.shouldClose()) {

				TheiaModule.pollEvents();

				long now = TheiaModule.getTimerValue();
				long frameTime = now - lastTime;
				lastTime = now;

				double freq = TheiaModule.getTimerFrequency();
				double toMs = 1000.0 / freq;

				double time = (now - startTime) / freq;

				bgfx_set_view_rect(0, 0, 0, width, height);

				bgfx_touch(0);

				bgfx_dbg_text_clear(0, false);

				frame((float) time, (float) (frameTime * toMs));

				bgfx_frame(false);
			}

			/* Shutdown */

			dispose();

			bgfx_shutdown();

			window.close();
			TheiaModule.close();
		}
	}

	private void parseArgs(String[] args) {
		
		if (hasArg(args, "gl")) {
			renderer = BGFX_RENDERER_TYPE_OPENGL;
		} else if (hasArg(args, "vk")) {
			renderer = BGFX_RENDERER_TYPE_VULKAN;
		} else if (hasArg(args, "noop")) {
			renderer = BGFX_RENDERER_TYPE_NOOP;
		} else if (Platform.get() == Platform.WINDOWS) {
			if (hasArg(args, "d3d9")) {
				renderer = BGFX_RENDERER_TYPE_DIRECT3D9;
			} else if (hasArg(args, "d3d11")) {
				renderer = BGFX_RENDERER_TYPE_DIRECT3D11;
			} else if (hasArg(args, "d3d12")) {
				renderer = BGFX_RENDERER_TYPE_DIRECT3D12;
			}
		} else if (Platform.get() == Platform.MACOSX) {
			if (hasArg(args, "mtl")) {
				renderer = BGFX_RENDERER_TYPE_METAL;
			}
		}

		if (hasArg(args, "amd")) {
			pciId = BGFX_PCI_ID_AMD;
		} else if (hasArg(args, "nvidia")) {
			pciId = BGFX_PCI_ID_NVIDIA;
		} else if (hasArg(args, "intel")) {
			pciId = BGFX_PCI_ID_INTEL;
		} else if (hasArg(args, "sw")) {
			pciId = BGFX_PCI_ID_SOFTWARE_RASTERIZER;
		}

		if (hasArg(args, "hmd")) {
			reset = BGFX_RESET_HMD;
		} else if (hasArg(args, "hmd-debug")) {
			reset = BGFX_RESET_HMD_DEBUG;
		}

		useCallbacks = hasArg(args, "cb");
		useCustomAllocator = hasArg(args, "alloc");
	}

	private static boolean hasArg(String[] args, String arg) {
		String expandArg = "--" + arg;
		for (String a : args) {
			if (expandArg.equalsIgnoreCase(a)) {
				return true;
			}
		}
		return false;
	}

	private void resize(long window, int width, int height) {
		this.width = width;
		this.height = height;
		bgfx_reset(width, height, reset);
	}

	protected int getWindowWidth() {
		return width;
	}

	protected int getWindowHeight() {
		return height;
	}

	private static BGFXCallbackInterface createCallbacks(MemoryStack stack) {
		BGFXCallbackVtbl callbackVtbl = BGFXCallbackVtbl.callocStack(stack);
		callbackVtbl.fatal(fatalCallback);
		callbackVtbl.trace_vargs(traceVarArgsCallback);
		callbackVtbl.cache_read_size(cacheReadSizeCallback);
		callbackVtbl.cache_read(cacheReadCallback);
		callbackVtbl.cache_write(cacheWriteCallback);
		callbackVtbl.screen_shot(screenShotCallback);
		callbackVtbl.capture_begin(captureBeginCallback);
		callbackVtbl.capture_end(captureEndCallback);
		callbackVtbl.capture_frame(captureFrameCallback);

		BGFXCallbackInterface callbacks = BGFXCallbackInterface.callocStack(stack);
		callbacks.vtbl(callbackVtbl);

		return callbacks;
	}

	private static final BGFXFatalCallbackI fatalCallback =
			(_this, _code, _str) -> {

			};

	private static final BGFXTraceVarArgsCallbackI traceVarArgsCallback =
			(_this, _filePath, _line, _format, _argList) -> {
				synchronized (Demo.class) {
					try (MemoryStack stack = MemoryStack.stackPush()) {
						String filePath = (_filePath != NULL) ? memUTF8(_filePath) : "[n/a]";

						ByteBuffer buffer = stack.malloc(128); // arbitary size to store formatted message
						int length = LibCStdio.nvsnprintf(memAddress(buffer), buffer.remaining(), _format, _argList);

						if (length > 0) {
							String message = memASCII(buffer, length - 1); // bgfx log messages are terminated with the newline character
							apiLog("bgfx: [" + filePath + " (" + _line + ")] - " + message);
						} else {
							apiLog("bgfx: [" + filePath + " (" + _line + ")] - error: unable to format output: " + memASCII(_format));
						}
					}
				}
			};

	private static final BGFXCacheReadSizeCallbackI cacheReadSizeCallback =
			(_this, _id) -> {
				return 0;
			};

	private static final BGFXCacheReadCallbackI cacheReadCallback =
			(_this, _id, _data, _size) -> {
				return false;
			};

	private static final BGFXCacheWriteCallbackI cacheWriteCallback =
			(_this, _id, _data, _size) -> {

			};

	private static final BGFXScreenShotCallbackI screenShotCallback =
			(_this, _filePath, _width, _height, _pitch, _data, _size, _yflip) -> {

			};

	private static final BGFXCaptureBeginCallbackI captureBeginCallback =
			(_this, _width, _height, _pitch, _format, _yflip) -> {

			};

	private static final BGFXCaptureEndCallbackI captureEndCallback =
			_this -> {

			};

	private static final BGFXCaptureFrameCallbackI captureFrameCallback =
			(_this, _data, _size) -> {

			};

	private static BGFXAllocatorInterface createAllocator(MemoryStack stack) {
		BGFXAllocatorVtbl allocatorVtbl = BGFXAllocatorVtbl.callocStack(stack);
		allocatorVtbl.realloc(allocatorCallback);

		BGFXAllocatorInterface allocator = BGFXAllocatorInterface.callocStack(stack);
		allocator.vtbl(allocatorVtbl);

		return allocator;
	}

	private static final BGFXReallocCallbackI allocatorCallback =
			(_this, _ptr, _size, _align, _file, _line) -> {
				synchronized (Demo.class) {
					String file = (_file != NULL) ? memUTF8(_file) : "[n/a]";

					if (_ptr == 0 && _size > 0) {
						if (_align != 0) {
							_ptr = getAllocator().aligned_alloc(_align, _size);

							apiLog("bgfx: allocate " + _size + " [" + _align + "] bytes at address "
									+ Long.toHexString(_ptr) + " [" + file + " (" + _line + ")]");

							return _ptr;
						}
					}

					long ptr = getAllocator().realloc(_ptr, _size);

					apiLog("bgfx: (re-)allocate address " + _ptr + " with " + _size + " bytes at address "
							+ Long.toHexString(ptr) + " [" + file + " (" + _line + ")]");

					return ptr;
				}
			};

}
