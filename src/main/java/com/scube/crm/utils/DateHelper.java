package com.scube.crm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {

	public static final Date beginningOfDay(Date date) {
		GregorianCalendar cal;

		cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static final Date endOfDay(Date date) {
		GregorianCalendar cal;

		cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 
	 * @param created
	 * @param lastDateIN
	 * @return 0: both dates are on the same day, 1: created is created after
	 *         lastDateIN, -1: created is created before lastDateIN
	 */
	public static final int testSameDay(GregorianCalendar created,
			GregorianCalendar lastDateIN) {

		if (created == null || lastDateIN == null) {
			throw new IllegalArgumentException();
		}

		final int dayCreated = created.get(Calendar.DAY_OF_YEAR);
		final int dayLastIn = lastDateIN.get(Calendar.DAY_OF_YEAR);

		final int yearCreated = created.get(Calendar.YEAR);
		final int yearLastIn = lastDateIN.get(Calendar.YEAR);

		if (yearCreated == yearLastIn) {
			if (dayCreated == dayLastIn) {
				return 0;
			} else if (dayCreated > dayLastIn) {
				return 1;
			} else {
				return -1;
			}
		} else if (yearCreated > yearLastIn) {
			return 1;
		} else {
			return -1;
		}
	}

	public static Date parseInputString(String dateStr) {
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		}
		SimpleDateFormat sdf;
		try {
			sdf = new SimpleDateFormat();
			return sdf.parse(dateStr);

		} catch (final ParseException e) {
			// Continue with the next format
		}
		try {
			sdf = new SimpleDateFormat("dd.MM.yyyy");
			return sdf.parse(dateStr);
		} catch (final ParseException e) {
			// Continue with the next format
		}
		try {
			sdf = new SimpleDateFormat("dd.MM.yy");
			return sdf.parse(dateStr);
		} catch (final ParseException e) {
			// Continue with the next format
		}

		try {
			sdf = new SimpleDateFormat("ddMMyy");
			return sdf.parse(dateStr);
		} catch (final ParseException e) {
			// Continue with the next format
		}
		try {
			sdf = new SimpleDateFormat("MMyy");
			return sdf.parse(dateStr);
		} catch (final ParseException e) {
			// Continue with the next format
		}

		return null;
	}
}
