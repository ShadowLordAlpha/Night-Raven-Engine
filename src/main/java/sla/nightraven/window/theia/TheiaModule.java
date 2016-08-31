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

import static org.lwjgl.system.MemoryUtil.memUTF8;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sla.nightraven.window.IWindowBuilder;
import sla.nightraven.window.IWindowModule;

/**
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class TheiaModule implements IWindowModule {

	private static final Logger LOG = LoggerFactory.getLogger(TheiaModule.class);

	public TheiaModule() {

		GLFW.glfwSetErrorCallback((int error, long description) -> LOG.warn("GLFW Error {}: {} ", error, memUTF8(description)));

		if (!glfwInit()) { 
			LOG.error("Failed to Initilize GLFW: {}", 1001); 
			System.exit(1001);
		}

		// TODO: anything else?
	}

	@Override
	public IWindowBuilder newBuilder() {
		return new TheiaWindowBuilder();
	}

	@Override
	public void update() throws Exception {
		glfwPollEvents(); // Unfortunately GLFW key presses are here as well
	}

	@Override
	public void close() throws Exception {
		// FIXME: current implementation only allows for close to be called once no matter how many modules are open
		glfwTerminate();
	}
}
