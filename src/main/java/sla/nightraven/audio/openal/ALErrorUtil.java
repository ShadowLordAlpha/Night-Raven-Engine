package sla.nightraven.audio.openal;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;

import sla.nightraven.audio.openal.ALCException.ALCInvalidContextException;
import sla.nightraven.audio.openal.ALCException.ALCInvalidDeviceException;
import sla.nightraven.audio.openal.ALCException.ALCInvalidEnumException;
import sla.nightraven.audio.openal.ALCException.ALCInvalidValueException;
import sla.nightraven.audio.openal.ALCException.ALCOutOfMemoryException;
import sla.nightraven.audio.openal.ALException.ALInvalidEnumException;
import sla.nightraven.audio.openal.ALException.ALInvalidNameException;
import sla.nightraven.audio.openal.ALException.ALInvalidOperationException;
import sla.nightraven.audio.openal.ALException.ALInvalidValueException;
import sla.nightraven.audio.openal.ALException.ALOutOfMemoryException;

public class ALErrorUtil {

	public static void checkALError() {
		int error = AL10.alGetError();
		switch (error) {
		case AL10.AL_NO_ERROR:
			break;
		case AL10.AL_INVALID_NAME:
			throw new ALInvalidNameException("Invalid name parameter.");
		case AL10.AL_INVALID_ENUM:
			throw new ALInvalidEnumException("Invalid enum parameter.");
		case AL10.AL_INVALID_VALUE:
			throw new ALInvalidValueException("Invalid value parameter.");
		case AL10.AL_INVALID_OPERATION:
			throw new ALInvalidOperationException("Illegal call.");
		case AL10.AL_OUT_OF_MEMORY:
			throw new ALOutOfMemoryException("Unable to allocate memory.");
		default:
			throw new ALException(AL10.alGetString(error));
		}
	}

	public static void checkALCError(long device) {
		int error = ALC10.alcGetError(device);
		switch (error) {
		case ALC10.ALC_NO_ERROR:
			break;
		case ALC10.ALC_INVALID_DEVICE:
			throw new ALCInvalidDeviceException("Invalid device handle or specifier name.");
		case ALC10.ALC_INVALID_CONTEXT:
			throw new ALCInvalidContextException("Invalid context parameter.");
		case ALC10.ALC_INVALID_ENUM:
			throw new ALCInvalidEnumException("Invalid enum parameter.");
		case ALC10.ALC_INVALID_VALUE:
			throw new ALCInvalidValueException("Invalid value parameter.");
		case ALC10.ALC_OUT_OF_MEMORY:
			throw new ALCOutOfMemoryException("Unable to allocate memory.");
		default:
			throw new ALCException(ALC10.alcGetString(device, error));
		}
	}
}
