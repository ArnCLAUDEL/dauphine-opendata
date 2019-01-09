package io.github.oliviercailloux.opendata.dao;

public class EntityDoesNotExistDaoException extends DaoException {

	private static final long serialVersionUID = -6741231667108884041L;

	public EntityDoesNotExistDaoException() {
		super();
	}

	public EntityDoesNotExistDaoException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityDoesNotExistDaoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public EntityDoesNotExistDaoException(final String message) {
		super(message);
	}

	public EntityDoesNotExistDaoException(final Throwable cause) {
		super(cause);
	}

}
