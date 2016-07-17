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
package sla.nightraven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ShutdownThread class is in charge of cleaning up resources after the engine has finished and is in the processes
 * of shutting down properly. In the case of an unexpected shutdown of the program it is undefined what happens to the
 * allocated native resources however they should be cleaned up by the underlying OS or it does not matter in the case
 * of a system shutdown.
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class ShutdownThread implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ShutdownThread.class);
	
	public ShutdownThread() {}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LOG.debug("Cleaning up Resources");
		
		LOG.debug("Shutting Down");
	}
}
