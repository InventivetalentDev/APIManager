package org.inventivetalent.apihelper.exception;

public class APIRegistrationException extends RuntimeException {
	public APIRegistrationException() {
	}

	public APIRegistrationException(String message) {
		super(message);
	}

	public APIRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public APIRegistrationException(Throwable cause) {
		super(cause);
	}

	public APIRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
