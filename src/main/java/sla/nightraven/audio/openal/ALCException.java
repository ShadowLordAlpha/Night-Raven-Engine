package sla.nightraven.audio.openal;

import org.lwjgl.openal.ALC10;

/**
 * TODO: Documentation
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class ALCException extends RuntimeException {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 8827585636992844978L;

	public ALCException() {
		super();
	}

	public ALCException(String message, Throwable cause) {
		super(message, cause);
	}

	public ALCException(String message) {
		super(message);
	}

	public ALCException(Throwable cause) {
		super(cause);
	}

	public static class ALCInvalidDeviceException extends ALCException {

		/**
		 * Generated Serial Version UID
		 */
		private static final long serialVersionUID = 8376207456027742579L;

		public ALCInvalidDeviceException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidDeviceException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidDeviceException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidDeviceException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALCInvalidContextException extends ALCException {

		/**
		 * Generated Serial Version UID
		 */
		private static final long serialVersionUID = -6762523744733266808L;

		public ALCInvalidContextException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidContextException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidContextException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidContextException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALCInvalidEnumException extends ALCException {

		/**
		 * Generated Serial Version UID
		 */
		private static final long serialVersionUID = -6449941954112719882L;

		public ALCInvalidEnumException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidEnumException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidEnumException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidEnumException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALCInvalidValueException extends ALCException {

		/**
		 * Generated Serial Version UID
		 */
		private static final long serialVersionUID = 6347042884839215103L;

		public ALCInvalidValueException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidValueException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidValueException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALCInvalidValueException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALCOutOfMemoryException extends ALCException {

		/**
		 * Generated Serial Version UID
		 */
		private static final long serialVersionUID = 2771825625999195781L;

		public ALCOutOfMemoryException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ALCOutOfMemoryException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALCOutOfMemoryException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALCOutOfMemoryException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	// TODO: Documentation
	public static void checkALCError(long device) {
		int error = ALC10.alcGetError(device);
		switch (error) {
		case ALC10.ALC_NO_ERROR:
			break;
		case ALC10.ALC_INVALID_DEVICE:
			throw new ALCInvalidDeviceException("A bad device was passed to an OpenAL function.");
		case ALC10.ALC_INVALID_CONTEXT:
			throw new ALCInvalidContextException("A bad context was passed to an OpenAL function.");
		case ALC10.ALC_INVALID_ENUM:
			throw new ALCInvalidEnumException("An unknown enum value was passed to an OpenAL function.");
		case ALC10.ALC_INVALID_VALUE:
			throw new ALCInvalidValueException("An invalid value was passed to an OpenAL function.");
		case ALC10.ALC_OUT_OF_MEMORY:
			throw new ALCOutOfMemoryException("The requested operation resulted in OpenAL running out of memory.");
		default:
			throw new ALCException(ALC10.alcGetString(device, error));
		}
	}
}
