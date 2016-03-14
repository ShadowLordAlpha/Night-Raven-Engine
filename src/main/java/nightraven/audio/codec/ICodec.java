package nightraven.audio.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface ICodec {

	public default ICodec open(File file) throws FileNotFoundException {
		return open(new FileInputStream(file));
	}
	
	public default ICodec open(URL url) throws IOException {
		return open(url.openStream());
	}
	
	public ICodec open(InputStream stream);
	
	
	
	/**
	 * Get the OpenAL format of this sound.
	 * 
	 * @return The OpenAL format of this sound.
	 */
	public int getFormat();
	
	/**
	 * Get the frequency of this sound.
	 * 
	 * @return The frequency of this sound.
	 */
	public int getFrequency();
	
	
	
}
