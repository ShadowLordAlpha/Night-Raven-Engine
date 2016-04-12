package sla.nightraven.audio.openal;

import org.lwjgl.openal.AL10;

public class ALException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2921417890129658243L;

	public ALException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ALException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ALException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ALException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public static class ALInvalidNameException {
		
	}
	
	public static class ALInvalidEnumException {
		
	}
	
	public static class ALInvalidValueException {
		
	}
	
	public static class ALInvalidOperationException {
		
	}
	
	public static class ALOutOfMemoryException {
		
	}
	
	public static void checkALError() {
		int error = AL10.alGetError();
		switch(error) {
		// TODO
		}
	}
}
