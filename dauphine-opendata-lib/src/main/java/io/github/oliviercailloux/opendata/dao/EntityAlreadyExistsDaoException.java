package io.github.oliviercailloux.opendata.dao;

public class EntityAlreadyExistsDaoException extends DaoException {

	private static final long serialVersionUID = -6741231667108884041L;

	public EntityAlreadyExistsDaoException() {
		super();
	}

	public EntityAlreadyExistsDaoException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityAlreadyExistsDaoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public EntityAlreadyExistsDaoException(final String message) {
		super(message);
	}

	public EntityAlreadyExistsDaoException(final Throwable cause) {
		super(cause);
	}

}
