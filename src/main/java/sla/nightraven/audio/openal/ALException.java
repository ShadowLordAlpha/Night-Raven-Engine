package sla.nightraven.audio.openal;

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

	public static class ALInvalidNameException extends ALException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5341662510807282779L;

		/**
		 * 
		 */
		public ALInvalidNameException() {
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @param cause
		 */
		public ALInvalidNameException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 */
		public ALInvalidNameException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param cause
		 */
		public ALInvalidNameException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALInvalidEnumException extends ALException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1208323859349882343L;

		public ALInvalidEnumException() {
			// TODO Auto-generated constructor stub
		}

		public ALInvalidEnumException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALInvalidEnumException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALInvalidEnumException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALInvalidValueException extends ALException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8826216052601844243L;

		public ALInvalidValueException() {
			// TODO Auto-generated constructor stub
		}

		public ALInvalidValueException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALInvalidValueException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALInvalidValueException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALInvalidOperationException extends ALException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7631096093118140386L;

		public ALInvalidOperationException() {
			// TODO Auto-generated constructor stub
		}

		public ALInvalidOperationException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALInvalidOperationException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALInvalidOperationException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ALOutOfMemoryException extends ALException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5365364416949773390L;

		public ALOutOfMemoryException() {
			// TODO Auto-generated constructor stub
		}

		public ALOutOfMemoryException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ALOutOfMemoryException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ALOutOfMemoryException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}
}
