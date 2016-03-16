package nightraven.audio;

import java.nio.ByteBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.system.jemalloc.JEmalloc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ALBuffer implements AutoCloseable {

private static final Logger LOG = LoggerFactory.getLogger(ALBuffer.class);
	
	private int bufferName;
	// TODO cashing of last know values? need to test if always looking up is a bottleneck
	
	public ALBuffer() {
		this(0);
	}

	private ALBuffer(int bufferName) {
		this.bufferName = bufferName;
	}
	
	public static ALBuffer[] generateBuffers(int n) {
		ALBuffer[] buffers = new ALBuffer[n];
		ByteBuffer buffer = JEmalloc.je_malloc(n * Integer.BYTES); // TODO: maybe not create and destroy objects so often
		AL10.alGenBuffers(n, buffer);
		
		for(int i = 0; i < n; i++) {
			buffers[i] = new ALBuffer(buffer.getInt(i));
		}
		
		JEmalloc.je_free(buffer);
		return buffers;
	}
	
	public ALBuffer reset() {
		if(bufferName != 0 && this.isBuffer()) {
			this.close();
		}
		bufferName = AL10.alGenBuffers();
		
		return this;
	}
	
	public int getBufferName() {
		return this.bufferName;
	}

	public boolean isBuffer() {
		return AL10.alIsBuffer(bufferName);
	}
	
	@Override
	public void close() {

		if(bufferName != 0 && this.isBuffer()) {
			AL10.alDeleteBuffers(bufferName);
			bufferName = 0;
			return;
		}
		
		LOG.warn("Failed to close {}! Has it already been closed", this);
	}

	@Override
	public String toString() {
		return "ALBuffer [bufferName=" + bufferName + "]";
	}
}
