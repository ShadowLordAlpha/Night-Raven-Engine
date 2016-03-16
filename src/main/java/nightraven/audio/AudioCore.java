package nightraven.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALContext;
import org.lwjgl.system.jemalloc.JEmalloc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioCore implements AutoCloseable {
	
	private static final Logger LOG = LoggerFactory.getLogger(AudioCore.class);
	
	public static final int MAX_NUM_BUFFERS = 128; 
	public static final int MAX_NUM_SOURCES = 16; 
	
	private ALContext context;
	private ByteBuffer bufferNames;
	private ByteBuffer sourceNames;
	
	public AudioCore() {
		
		// Setup OpenAL and make it ready for use
		context = ALContext.create();
		context.makeCurrent();
		context.makeCurrentThread();
		
		LOG.info("OpenAL Vender: {}", AL10.alGetString(AL10.AL_VENDOR));
		LOG.info("OpenAL Version: {}", AL10.alGetString(AL10.AL_VERSION));
		LOG.info("OpenAL Renderer: {}", AL10.alGetString(AL10.AL_RENDERER));
		LOG.info("OpenAL Extensions: {}", AL10.alGetString(AL10.AL_EXTENSIONS));
		
		// Create Buffers of the proper size
		bufferNames = JEmalloc.je_calloc(MAX_NUM_BUFFERS, Integer.BYTES);
		sourceNames = JEmalloc.je_calloc(MAX_NUM_SOURCES, Integer.BYTES);
		
		// Pre-create the OpenAL Buffers and Sources we can use. We should never go over these sets.
		AL10.alGenBuffers(MAX_NUM_BUFFERS, bufferNames);
		AL10.alGenSources(MAX_NUM_SOURCES, sourceNames);
	}
	
	
	
	public int loadSound(File file) throws FileNotFoundException {
		
		int buffer = bufferNames.getInt(0);
		
		WaveData waveData = WaveData.create(new FileInputStream(file));
		AL10.alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
		waveData.dispose();
		
		return buffer;
	}
	
	public void setListenerData(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}

	@Override
	public void close() {
		AL10.alDeleteBuffers(MAX_NUM_BUFFERS, bufferNames);
		AL10.alDeleteSources(MAX_NUM_SOURCES, sourceNames);
		
		JEmalloc.je_free(bufferNames);
		JEmalloc.je_free(sourceNames);
		
		context.destroy();
		context.getDevice().close();
	}
}
