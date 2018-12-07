package io.github.oliviercailloux.opendata.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Ziad & Sofian on 03/12/2017.
 */

@ApplicationScoped
public class DateUtils implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());

	/**
	 * return date with a specific format (formatOpt) if formatOpt is null
	 * (optional) we return with a specific format : dd/MM/yyyy HH:mm
	 *
	 * @param date
	 * @param formatOpt
	 * @return
	 * @throws DateTimeException
	 */
	public static String transformDate(final Date date, final Optional<String> formatOpt) throws DateTimeException {
		final String format = formatOpt.isPresent() ? formatOpt.get() : "dd/MM/yyyy HH:mm";
		SimpleDateFormat dateFormat;
		try {
			dateFormat = new SimpleDateFormat(format);
		} catch (final IllegalArgumentException illegal) {
			throw illegal;
		}

		return dateFormat.format(date);
	}
}
