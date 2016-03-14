package nightraven.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALContext;
import org.lwjgl.system.jemalloc.JEmalloc;

public class AudioCore implements AutoCloseable {
	
	public static final int MAX_NUM_BUFFERS = 128; 
	public static final int MAX_NUM_SOURCES = 16; 
	
	public final boolean VORBIS_EXT_PRESENT;
	
	private ALContext context;
	private ByteBuffer bufferNames;
	private ByteBuffer sourceNames;

	public AudioCore() {
		
		// Setup OpenAL and make it ready for use
		context = ALContext.create();
		context.makeCurrent();
		
		// Create Buffers of the proper size
		bufferNames = JEmalloc.je_calloc(MAX_NUM_BUFFERS, 4);
		sourceNames = JEmalloc.je_calloc(MAX_NUM_SOURCES, 4);
		
		// Pre-create the OpenAL Buffers and Sources we can use. We should never go over these sets.
		AL10.alGenBuffers(MAX_NUM_BUFFERS, bufferNames);
		AL10.alGenSources(MAX_NUM_SOURCES, sourceNames);
		
		// Other data / extensions used
		VORBIS_EXT_PRESENT = AL10.alIsExtensionPresent("AL_EXT_vorbis");
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
