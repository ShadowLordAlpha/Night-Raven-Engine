package nightraven.audio;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioCore implements AutoCloseable, Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(AudioCore.class);
	
	public static final int MAX_NUM_BUFFERS = 128; 
	public static final int MAX_NUM_SOURCES = 16; 
	
	private boolean closed = false;
	
	private ByteBuffer bufferNames;
	private ByteBuffer sourceNames;
	
	public AudioCore() {
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
