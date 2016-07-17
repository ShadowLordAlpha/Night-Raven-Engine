package sla.nightraven.audio.rhea;

import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.openal.ALC10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sla.nightraven.audio.IAudioModule;

public class RheaModule implements IAudioModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(RheaModule.class);
	
	private long device;
	private long context;
	
	public RheaModule() {
		
		// TODO: load module settings?
		
		// TODO: allow picking of different devices?
		device = ALC10.alcOpenDevice((String) null);
		if(device == NULL) {
			LOG.error("Failed to open OpenAL Device");
			System.exit(1001);
		}
		
		int [] attrList = { 0 }; // TODO: this will need to be filled out with properties
		context = ALC10.alcCreateContext(device, attrList);
		if(context == NULL) {
			LOG.error("Failed to create OpenAL Context");
			System.exit(1002);
		}
		
		
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
