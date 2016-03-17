/**
 * 
 */
package nightraven.audio;

import java.nio.ByteBuffer;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;
import org.lwjgl.system.jemalloc.JEmalloc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public class ALSource implements AutoCloseable {
	
	private static final Logger LOG = LoggerFactory.getLogger(ALSource.class);
	
	private int sourceName;
	// TODO cashing of last know values? need to test if always looking up is a bottleneck
	
	public ALSource() {
		this(0);
	}

	private ALSource(int sourceName) {
		this.sourceName = sourceName;
	}
	
	public static ALSource[] generateSources(int n) {
		ALSource[] sources = new ALSource[n];
		ByteBuffer buffer = JEmalloc.je_malloc(n * Integer.BYTES); // TODO: maybe not create and destroy objects so often
		AL10.alGenSources(n, buffer);
		
		for(int i = 0; i < n; i++) {
			sources[i] = new ALSource(buffer.getInt(i));
		}
		
		JEmalloc.je_free(buffer);
		return sources;
	}
	
	public ALSource regen() {
		if(sourceName != 0 && this.isSource()) {
			this.close();
		}
		sourceName = AL10.alGenSources();
		
		return this;
	}
	
	public int getSourceName() {
		return this.sourceName;
	}
	
	public float getGain() {
		return AL10.alGetSourcef(sourceName, AL10.AL_GAIN);
	}
	
	public ALSource setGain(float gain) {
		AL10.alSourcef(sourceName, AL10.AL_GAIN, gain);
		return this;
	}
	
	public float getPitch() {
		return AL10.alGetSourcef(sourceName, AL10.AL_PITCH);
	}
	
	public ALSource setPitch(float pitch) {
		AL10.alSourcef(sourceName, AL10.AL_PITCH, pitch);
		return this;
	}
	
	public boolean isLooping() {
		return AL10.alGetSourcei(sourceName, AL10.AL_LOOPING) == AL10.AL_TRUE;
	}
	
	public ALSource setLooping(boolean looping) {
		AL10.alSourcei(sourceName, AL10.AL_LOOPING, (looping) ? AL10.AL_TRUE : AL10.AL_FALSE);
		return this;
	}
	
	public Vector3f getPosition() {
		return getPosition(new Vector3f());
	}
	
	public Vector3f getPosition(Vector3f dest) {
		ByteBuffer buffer = JEmalloc.je_malloc(3 * Float.BYTES); // TODO: maybe not create and destroy objects so often
		AL10.alGetSourcefv(sourceName, AL10.AL_POSITION, buffer);
		dest.set(buffer);
		JEmalloc.je_free(buffer);
		return dest;
	}
	
	public ALSource setPosition(Vector3f vec) {
		return setPosition(vec.x, vec.y, vec.z);
	}
	
	public ALSource setPosition(float x, float y, float z) {
		AL10.alSource3f(sourceName, AL10.AL_POSITION, x, y, z);
		return this;
	}
	
	public Vector3f getVelocity() {
		return getVelocity(new Vector3f());
	}
	
	public Vector3f getVelocity(Vector3f dest) {
		ByteBuffer buffer = JEmalloc.je_malloc(3 * Float.BYTES); // TODO: maybe not create and destroy objects so often
		AL10.alGetSourcefv(sourceName, AL10.AL_VELOCITY, buffer);
		dest.set(buffer);
		JEmalloc.je_free(buffer);
		return dest;
	}
	
	public ALSource setVelocity(Vector3f vec) {
		return setPosition(vec.x, vec.y, vec.z);
	}
	
	public ALSource setVelocity(float x, float y, float z) {
		AL10.alSource3f(sourceName, AL10.AL_VELOCITY, x, y, z);
		return this;
	}
	
	// TODO: buffer methods
	
	public ALSource play() {
		AL10.alSourcePlay(sourceName);
		return this;
	}
	
	public ALSource pause() {
		AL10.alSourcePause(sourceName);
		return this;
	}
	
	public ALSource stop() {
		AL10.alSourceStop(sourceName);
		return this;
	}
	
	public boolean isSource() {
		return AL10.alIsSource(sourceName);
	}

	@Override
	public void close() {
		
		if(this.isSource()) {
			AL10.alDeleteSources(sourceName);
			sourceName = 0;
			return;
		}
		
		LOG.warn("Failed to close {}! Has it already been closed", this);
	}

	@Override
	public String toString() {
		return "ALSource [sourceName=" + sourceName + "]";
	}
}
