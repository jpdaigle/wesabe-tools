package ca.softwareengineering.wesabetools.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Parse ISO8601 date into standard date format. Warning, SimpleDateFormat
	 * probably isn't thread safe.
	 */
	public static long getDateFromIso8601Day(String strDate) throws ParseException {
		return sdf.parse(strDate).getTime();
	}

	/**
	 * Format a date string. Warning, SimpleDateFormat probably isn't thread
	 * safe.
	 */
	public static String getDateString(long time) {
		return sdf.format(new Date(time));
	}
}
