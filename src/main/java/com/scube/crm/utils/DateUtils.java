package com.scube.crm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.Session;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;

/**
 * @author shiju.kurikesu
 * The Class DateUtils.
 */
public class DateUtils {

    /** The logger. */
	
	private static final MySalesLogger LOGGER = MySalesLogger
			.getLogger(DateUtils.class);
	
//    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    // Date format for backward compatibility
    /** The Constant DATE_FORMAT_BC. */
    private static final String DATE_FORMAT_BC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    // Current date format
    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /** The date formatter bc. */
    public static FastDateFormat dateFormatterBc = FastDateFormat
            .getInstance(DATE_FORMAT_BC);

    /** The date formatter. */
    public static FastDateFormat dateFormatter = FastDateFormat
            .getInstance(DATE_FORMAT);

    /** The date formatteryyyy m mdd. */
    public static FastDateFormat dateFormatteryyyyMMdd = FastDateFormat
            .getInstance("yyyy-MM-dd");

    /** The date formatteryyyy mm. */
    public static FastDateFormat dateFormatteryyyyMM = FastDateFormat
            .getInstance("yyyy-MM");

    /** The date formatteryy mm. */
    public static FastDateFormat dateFormatteryyMM = FastDateFormat
            .getInstance("yyMM");

    /** The date formatteryy m mdd. */
    public static FastDateFormat dateFormatteryyMMdd = FastDateFormat
            .getInstance("yyMMdd");

    /** The calendar. */
    private static GregorianCalendar calendar = new GregorianCalendar();

    /** The Constant DATABASE_DATE_FORMAT. */
    public static final String DATABASE_DATE_FORMAT = "yyyyMMdd";

    /** The Constant YEAR_MONTH_FORMAT. */
    public static final String YEAR_MONTH_FORMAT = "yyyyMM";
    
    public static final String TIME_FORMAT = "HH:mm a";

    /** The Constant DAILY. */
    private static final String DAILY = "DAILY";

    /** The Constant MONTHLY. */
    private static final String MONTHLY = "MONTHLY";

    /** The Constant QUARTERLY. */
    private static final String QUARTERLY = "QUARTERLY";

    /** The Constant YEARLY. */
    private static final String YEARLY = "YEARLY";

    /** The Constant CENTURY_CODE. */
    private static final String CENTURY_CODE = "1"; // if we want this code to
                                                    // work for next century
                                                    // also,
                                                    // then we can extract first
                                                    // two digits of year
                                                    // if first two digits are
                                                    // 19, then century code = 0
                                                    // if first two digits are
                                                    // 20, then century code = 1
                                                    // if first two digits are
                                                    // 21, then century code = 2

    // private static final SimpleDateFormat databaseDateFormatter = new
    // SimpleDateFormat(DATABASE_DATE_FORMAT);
    /** The year month date formatter. */
    private static FastDateFormat yearMonthDateFormatter = FastDateFormat
            .getInstance(YEAR_MONTH_FORMAT);

    
    private static final int HOUR_INDEX  = 0;
    private static final int MINUTE_INDEX = 1;
    private static final String POSTING_HOUR_MINUTE_SEPARATOR = ":";
    /**
     * Format date to db number.
     * @param date the date
     * @return the long
     */
    public static Long formatDateToDBNumber(Date date) {
        if (date != null) {
            SimpleDateFormat databaseDateFormatter =
                    new SimpleDateFormat(DATABASE_DATE_FORMAT);
            String stringFormattedDate = databaseDateFormatter.format(date);
            return new Long(stringFormattedDate);
        }
        return null;
    }

    /**
     * Format date to year month.
     * @param date the date
     * @return the long
     */
    public static Long formatDateToYearMonth(Date date) {
        if (date != null) {
            return new Long(yearMonthDateFormatter.format(date));
        }
        return null;
    }

    /**
     * Format db number to date.
     * @param date the date
     * @return the date
     */
    public static Date formatDBNumberToDate(Long date) {
        if (date != null) {
            try {
                SimpleDateFormat databaseDateFormatter =
                        new SimpleDateFormat(DATABASE_DATE_FORMAT);
                return databaseDateFormatter.parse(date + "");
            } catch (ParseException e) {
                LOGGER.error("Could not parse long:" + date
                        + " to a date. Expected format: "
                        + DATABASE_DATE_FORMAT);
            }
        }
        return null;
    }

    /**
     * Format db string to date.
     * @param strDate the str date
     * @return the date
     */
    public static Date formatDBStringToDate(String strDate) {
        if (strDate != null) {
            try {
                SimpleDateFormat databaseDateFormatter =
                        new SimpleDateFormat(DATABASE_DATE_FORMAT);
                return databaseDateFormatter.parse(strDate);
            } catch (ParseException e) {
                LOGGER.error("Could not parse string:" + strDate
                        + " to a date. Expected format: "
                        + DATABASE_DATE_FORMAT);
            }
        }
        return null;
    }

	public static Date convertTextToDate(String strDate, String format)
		throws ParseException {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat(format);
			date = (Date) formatter.parse(strDate);
		return date;
	}
    
    
    /**
     * Gets the date formatteryyyy m mdd format.
     * @param date the date
     * @return the date formatteryyyy m mdd format
     */
    public static String getDateFormatteryyyyMMddFormat(Date date) {
        return dateFormatteryyyyMMdd.format(date);
    }

    /**
     * Adds the one day.
     * @param date the date
     * @return the date
     */
    public static Date addOneDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1); // add 1 day
        return cal.getTime();
    }
    
    public static Date addTimeToDate(Date systemDate, String postingExtendTimeStr, String eveningBatchTime) {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(systemDate);
        
        String[] eveningBatchTimeArray = eveningBatchTime.split(POSTING_HOUR_MINUTE_SEPARATOR); 
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(eveningBatchTimeArray[HOUR_INDEX]));  
        cal.set(Calendar.MINUTE, Integer.parseInt(eveningBatchTimeArray[MINUTE_INDEX])); 
        
        String[] postingExtendTimeArray = postingExtendTimeStr.split(POSTING_HOUR_MINUTE_SEPARATOR);
        //int minutes = (Integer.parseInt(postingExtendTimeArray[HOUR_INDEX]) * 60) + Integer.parseInt(postingExtendTimeArray[MINUTE_INDEX]);
        cal.add(Calendar.HOUR,Integer.parseInt(postingExtendTimeArray[HOUR_INDEX]));
        cal.add(Calendar.MINUTE, Integer.parseInt(postingExtendTimeArray[MINUTE_INDEX])); 
        
		return cal.getTime();
	}
    
    /**
     * Checks if is end of month.
     * @param systemDate the system date
     * @return true, if is end of month
     */
    public static boolean isEndOfMonth(Date systemDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(systemDate);
        // int lastDayOfTheMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        int lastDayOfTheMonth = calendar.getActualMaximum(Calendar.DATE);
        if (lastDayOfTheMonth == calendar.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    /**
     * Convert date to string.
     * @param date the date
     * @return the string
     */
    public static String ConvertDateToString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormatter.format(date);
    }

    /**
     * Convert string to date.
     * @param dateStr the date str
     * @return the date
     */
    public static Date ConvertStringToDate(String dateStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat sdfBc = new SimpleDateFormat(DATE_FORMAT_BC);
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            boolean inDeprecatedFormat = false;
            try {
                date = sdfBc.parse(dateStr);
                inDeprecatedFormat = true;
            } catch (ParseException e1) {
            }

            if (inDeprecatedFormat) {
                LOGGER.debug("Date format for date time message in deprecated format. The date format should be "
                        + DATE_FORMAT.replace("'", ""));
            } else {
                throw new ConversionException(
                        "Invalid date format. Date format should be "
                                + DATE_FORMAT.replace("'", ""), e);
            }
        }

        return date;
    }
    
    public static int getHourFromTimeString(String timeString) {		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(TIME_FORMAT);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(timeString));
		} catch(Exception e) {
			throw new ConversionException("Invalid time format. Date format should be "+TIME_FORMAT, e);
		}
		int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
		return hour;
	}

    
    public static Date getLastDayOfPrevYear(Date date) {
  	  Calendar cal = Calendar.getInstance();
  	  cal.setTime(date);
  	  cal.set(Calendar.MONTH, 0);
  	  cal.set(Calendar.DAY_OF_MONTH, 1);
  	  cal.add(Calendar.DATE, -1);
  	  return cal.getTime();
  	 }
    
    
    /**
     * Gets the month diff.
     * @param date1 the date1
     * @param date2 the date2
     * @return the month diff
     */
    public static int getMonthDiff(Date date1, Date date2) {
        Calendar cal1 = null;
        Calendar cal2 = null;
        cal1 = Calendar.getInstance();
        cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        int monthDiff =
                yearDiff * 12 + cal2.get(Calendar.MONTH)
                        - cal1.get(Calendar.MONTH);
        return (monthDiff);
    }

    /**
     * Calculates the number of days between two calendar days in a manner which
     * is independent of the Calendar type used.
     * @param date1 the date1
     * @param date2 the date2
     * @return The number of days between the two dates. Zero is returned if the
     *         dates are the same, one if the dates are adjacent, etc. The order
     *         of the dates does not matter, the value returned is always >= 0.
     *         If Calendar types of d1 and d2 are different, the result may not
     *         be accurate.
     */
    public static int getDaysBetween(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d1.setTime(date1);
        d2.setTime(date2);

        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days =
                d2.get(java.util.Calendar.DAY_OF_YEAR)
                        - d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    } // getDaysBetween()

    /**
     * Gets the day diff.
     * @param date1 the date1
     * @param date2 the date2
     * @return the day diff
     */
    public static int getDayDiff(Date date1, Date date2) {
        Calendar cal1 = null;
        Calendar cal2 = null;
        cal1 = Calendar.getInstance();
        cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        return yearDiff / (24 * 60 * 60 * 1000);
    }

    /**
     * Gets the begin of month.
     * @param date the date
     * @return the begin of month
     */
    public static Date getBeginOfMonth(Date date) {
        calendar.setTime(date);
        calendar.set(GregorianCalendar.DAY_OF_MONTH,
                calendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
        calendar.set(GregorianCalendar.MINUTE,
                calendar.getActualMinimum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND,
                calendar.getActualMinimum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND,
                calendar.getActualMinimum(GregorianCalendar.MILLISECOND));
        calendar.set(GregorianCalendar.HOUR_OF_DAY,
                calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));

        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets the end of month.
     * @param date the date
     * @return the end of month
     */
    public static Date getEndOfMonth(Date date) {
        calendar.setTime(date);
        calendar.set(GregorianCalendar.DAY_OF_MONTH,
                calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        calendar.set(GregorianCalendar.MINUTE,
                calendar.getActualMaximum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND,
                calendar.getActualMaximum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND,
                calendar.getActualMaximum(GregorianCalendar.MILLISECOND));
        calendar.set(GregorianCalendar.HOUR_OF_DAY,
                calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets the last day of curr month.
     * @param date the date
     * @return the last day of curr month
     */
    public static Date getLastDayOfCurrMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * Gets the last day of curr year.
     * @param date the date
     * @return the last day of curr year
     */
    public static Date getLastDayOfCurrYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * Checks if is before ignore time.
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if is before ignore time
     */
    public static boolean isBeforeIgnoreTime(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = sdf.format(date1);
        String d2 = sdf.format(date2);

        try {
            date1 = sdf.parse(d1);
            date2 = sdf.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.before(date2)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is equal or before ignore time.
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if is equal or before ignore time
     */
    public static boolean isEqualOrBeforeIgnoreTime(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = sdf.format(date1);
        String d2 = sdf.format(date2);

        try {
            date1 = sdf.parse(d1);
            date2 = sdf.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date1.equals(date2)) {
            return true;
        }
        if (date1.before(date2)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the eOD period.
     * @param postDate the post date
     * @return the eOD period
     * @throws MySalesException 
     */
    public static String getEODPeriod(Date postDate) throws MySalesException {
        if (postDate == null) {
            throw new MySalesException("Date passed to getEODPeriod is null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(postDate);
        calendar.add(Calendar.DATE, 1);
        int nextDay = calendar.get(Calendar.DATE);
        if (nextDay == 1) { // either MONTHEND or QUARTEREND or YEAREND
            if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
                return YEARLY;
            } else if ((calendar.get(Calendar.MONTH) == Calendar.APRIL)
                    || (calendar.get(Calendar.MONTH) == Calendar.JULY)
                    || (calendar.get(Calendar.MONTH) == Calendar.OCTOBER)) {
                return QUARTERLY;
            } else {
                return MONTHLY;
            }
        } else {
            return DAILY;
        }
    }

    // returns a Long number in CYYMMDD format of the date
    // C = century: 2 = 2000
    // Currently, century is hardcoded to 2
    // May be we can get the first digit of the year as century. But what about
    // 2099? Will it be considered as C=2?

    /**
     * Gets the cYYMMDD.
     * @param date the date
     * @return the cYYMMDD
     * @throws MySalesException 
     */
    public static Long getCYYMMDD(Date date) throws MySalesException {
        if (date == null) {
            throw new MySalesException("Date passed to getCYYMMDD is null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dateStr = null;
        try {
            dateStr = sdf.format(date);
        } catch (Exception e) {
            throw new MySalesException("Error while converting date: "
                    + date + " to String");
        }
        dateStr = CENTURY_CODE + dateStr;
        return Long.valueOf(dateStr);
    }
    
    public static String getYYYYMMDDHHMMSS(Date date) throws MySalesException {
        if (date == null) {
            throw new MySalesException("Date passed to getCYYMMDD is null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String dateStr = null;
        try {
            dateStr = sdf.format(date);
        } catch (Exception e) {
            throw new MySalesException("Error while converting date: "
                    + date + " to String");
        }        
        return dateStr;
    }

    /**
     * Formats a given date based on the simpleDateFormatter object.
     * @param date the date
     * @return the month year
     */
    public static String getMonthYear(Date date) {
        return dateFormatteryyyyMM.format(date);
    }

    /**
     * Formats a given date based on the simpleDateFormatter object.
     * @param date the date
     * @return the swift header month year
     */
    public static String getSwiftHeaderMonthYear(Date date) {
        return dateFormatteryyMM.format(date);
    }

    /**
     * Formats a given date based on the simpleDateFormatter object.
     * @param date the date
     * @return the month year day
     */
    public static String getMonthYearDay(Date date) {
        return dateFormatteryyMMdd.format(date);
    }

    /**
     * Gets the system date.
     * @param session the session
     * @return the system date
     * @throws PersistenceException the persistence exception
     */
    public static Date getSystemDate(Session session)
            throws MySalesException {
        return (Date) session.createSQLQuery("SELECT CURR_DATE FROM SYSTEM")
                .uniqueResult();
    }

    /** The Constant YYYYMMDD_DATE__FORMAT. */
    private static final String YYYYMMDD_DATE__FORMAT = "yyyy-MM-dd";

    // private static SimpleDateFormat yyyyMMDDdateFormatter = new
    // SimpleDateFormat(YYYYMMDD_DATE__FORMAT);

    /**/
    /**
     * Convert yyyymmdd string to date.
     * @param dateStr the date str
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date convertYYYYMMDDStringToDate(String dateStr)
            throws ParseException {
        SimpleDateFormat yyyyMMDDdateFormatter =
                new SimpleDateFormat(YYYYMMDD_DATE__FORMAT);
        return yyyyMMDDdateFormatter.parse(dateStr);
    }

    /**
     * Convert yyyymmdd date to string.
     * @param date the date
     * @return the string
     */
    public static String convertYYYYMMDDDateToString(Date date) {
        SimpleDateFormat yyyyMMDDdateFormatter =
                new SimpleDateFormat(YYYYMMDD_DATE__FORMAT);
        return yyyyMMDDdateFormatter.format(date);
    }

    /**
     * Gets the transaction date.
     * @param messageDispatcher the message dispatcher
     * @return the transaction date
     */
  /*  public static Date getTransactionDate(MessageDispatcher messageDispatcher) {
        Date currentDate = messageDispatcher.getSystemVariables().getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.set(Calendar.DATE, 15);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return cal.getTime();
    }*/

    /**
     * Gets the first day of prev month.
     * @param date the date
     * @return the first day of prev month
     */
    public static Date getFirstDayOfPrevMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets the first day of next month.
     * @param date the date
     * @return the first day of next month
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets the second monday of month.
     * @param date the date
     * @return the second monday of month
     */
    public static Date getSecondMondayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 12);
        cal.set(Calendar.DAY_OF_WEEK, 2);// Set it to the 2nd day of the week
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Gets the last day of prev month.
     * @param date the date
     * @return the last day of prev month
     */
    public static Date getLastDayOfPrevMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * Gets the first day of curr qtr.
     * @param date the date
     * @return the first day of curr qtr
     */
    public static Date getFirstDayOfCurrQtr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets the last day of curr qtr.
     * @param date the date
     * @return the last day of curr qtr
     */
    public static Date getLastDayOfCurrQtr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 2 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * Gets the first day of prev qtr.
     * @param date the date
     * @return the first day of prev qtr
     */
    public static Date getFirstDayOfPrevQtr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -2 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets the last day of prev qtr.
     * @param date the date
     * @return the last day of prev qtr
     */
    public static Date getLastDayOfPrevQtr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * Gets the next business day.
     * @param date the date
     * @param holidays the holidays
     * @return the next business day
     */
    public static Date getNextBusinessDay(Date date, List<Long> holidays) {
        while (isWeekend(date) || isHoliday(date, holidays)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
        }
        return date;
    }

    /**
     * Gets the business days.
     * @param date the date
     * @return the business days
     */
    public static int getBusinessDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int nDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int bd = 0;
        for (int i = 1; i <= nDays; i++) {
            int day = (i + startDay - 1) % 7;
            if (day > 1) bd++;
        }
        return bd;
    }

    /**
     * Checks if is business day.
     * @param date the date
     * @param holidays the holidays
     * @return true, if is business day
     */
	public static boolean isBusinessDay(Date date, List<Long> holidays) {
        if (!isWeekend(date) && !isHoliday(date, holidays)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is holiday.
     * @param date the date
     * @param holidays the holidays
     * @return true, if is holiday
     */
    private static boolean isHoliday(Date date, List<Long> holidays) {
        Long lngDate = formatDateToDBNumber(date);
        if (holidays != null && holidays.size() > 0) {
            for (Long holiday : holidays) {
                if (lngDate.longValue() == holiday.longValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if is weekend.
     * @param date the date
     * @return true, if is weekend
     */
    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if is sunday.
     * @param date the date
     * @return true, if is sunday
     */
    public static boolean isSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * Adds the days.
     * @param currDate the curr date
     * @param numDays the num days
     * @return the date
     */
    public static Date addDays(Date currDate, Long numDays) {
        Calendar objCalendar = Calendar.getInstance();
        int year = currDate.getYear();
        int month = currDate.getMonth();
        int date = currDate.getDate();

        if (numDays < Integer.MIN_VALUE || numDays > Integer.MAX_VALUE) {
            LOGGER.debug("Range of long value exceds intgere limit for a casting ");
            return null;
        }
        int iNumDaysCasted = (int) numDays.longValue();

        objCalendar.set(year + 1900, month, date); // (year,month,date)
        objCalendar.add(Calendar.DATE, iNumDaysCasted);
        return objCalendar.getTime();
    }

    /**
     * Removes the days.
     * @param currDate the curr date
     * @param numberOfDays the number of days
     * @return the date
     */
    public static Date removeDays(Date currDate, Long numberOfDays) {
        Calendar objCalendar = Calendar.getInstance();
        int year = currDate.getYear();
        int month = currDate.getMonth();
        int date = currDate.getDate();

        if (numberOfDays < Integer.MIN_VALUE
                || numberOfDays > Integer.MAX_VALUE) {
            LOGGER.debug("Range of long value exceds intgere limit for a casting ");
            return null;
        }
        int iNumDaysCasted = (int) numberOfDays.longValue();

        objCalendar.set(year + 1900, month, date); // (year,month,date)
        objCalendar.add(Calendar.DATE, -iNumDaysCasted);
        return objCalendar.getTime();
    }

    /**
     * Adds the business days.
     * @param date the date
     * @param numberOfDays the number of days
     * @param holidays the holidays
     * @return the date
     */
    public static Date addBusinessDays(Date date, int numberOfDays,
            List<Long> holidays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int count = 0;
        while (count == numberOfDays) {
            cal.add(Calendar.DATE, 1);
            if (isBusinessDay(cal.getTime(), holidays)) {
                count++;
            }
        }
        return cal.getTime();
    }

    /**
     * Adds the business days within month.
     * @param date the date
     * @param numberOfDays the number of days
     * @param holidays the holidays
     * @return the date
     */
    public static Date addBusinessDaysWithinMonth(Date date, int numberOfDays,
            List<Long> holidays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int lastDayOfMonth = cal.getMaximum(Calendar.DAY_OF_MONTH);
        int count = 0;
        while (count == numberOfDays) {
            cal.add(Calendar.DATE, 1);
            if (isBusinessDay(cal.getTime(), holidays)) {
                count++;
            }
            if (cal.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth) {
                break;
            }
        }
        return cal.getTime();
    }

    /**
     * Removes the business days.
     * @param date the date
     * @param numberOfDays the number of days
     * @param holidays the holidays
     * @return the date
     */
    public static Date removeBusinessDays(Date date, int numberOfDays,
            List<Long> holidays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int count = 0;
        while (count < numberOfDays) {
            cal.add(Calendar.DATE, -1);
            if (isBusinessDay(cal.getTime(), holidays)) {
                count++;
            }
        }
        return cal.getTime();
    }

    /**
     * Checks if is same day.
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if is same day
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (formatDateToDBNumber(date1).longValue() ==  formatDateToDBNumber(date2).longValue()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is same or after day.
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if is same or after day
     */
    public static boolean isSameOrAfterDay(Date date1, Date date2) {
        if (formatDateToDBNumber(date1) >= formatDateToDBNumber(date2)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is last month of quarter.
     * @param date the date
     * @param interestBearing the interest bearing
     * @return true, if is last month of quarter
     */
    public static boolean isLastMonthOfQuarter(Date date,
            boolean interestBearing) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (interestBearing) {
            if (month == Calendar.FEBRUARY || month == Calendar.MAY
                    || month == Calendar.AUGUST || month == Calendar.NOVEMBER) {
                return true;
            }
        } else {
            if (month == Calendar.DECEMBER || month == Calendar.MARCH
                    || month == Calendar.JUNE || month == Calendar.SEPTEMBER) {
                return true;
            }
        }
        return false;
    }

    // Returns future date after adding numDays to the currDate
    /**
     * Gets the future date.
     * @param currDate the curr date
     * @param numDays the num days
     * @return the future date
     */
    public Date getFutureDate(Date currDate, int numDays) {

        Calendar objCalendar = Calendar.getInstance();
        int year = currDate.getYear();
        int month = currDate.getMonth();
        int date = currDate.getDate();

        objCalendar.set(year + 1900, month, date); // (year,month,date)
        objCalendar.add(Calendar.DATE, numDays);
        return objCalendar.getTime();
    }

    // Returns past date after subtracting numDays to the currDate
    /**
     * Gets the past date.
     * @param currDate the curr date
     * @param numDays the num days
     * @return the past date
     */
    public Date getPastDate(Date currDate, int numDays) {

        Calendar objCalendar = Calendar.getInstance();
        int year = currDate.getYear();
        int month = currDate.getMonth();
        int date = currDate.getDate();

        objCalendar.set(year + 1900, month, date); // (year,month,date)
        numDays = numDays * -1;
        objCalendar.add(Calendar.DATE, numDays);
        return objCalendar.getTime();
    }

    // returns number of days between two dates
    /**
     * Days between dates.
     * @param firstDate the first date
     * @param secondDate the second date
     * @return the long
     */
    public static long daysBetweenDates(Date firstDate, Date secondDate) {

        Calendar objFirstCal = Calendar.getInstance(); // new
                                                       // GregorianCalendar();
        Calendar objSecondCal = Calendar.getInstance(); // new
                                                        // GregorianCalendar();

        int year1 = firstDate.getYear();
        int month1 = firstDate.getMonth();
        int date1 = firstDate.getDate();
        objFirstCal.set(year1 + 1900, month1, date1);

        int year2 = secondDate.getYear();
        int month2 = secondDate.getMonth();
        int date2 = secondDate.getDate();
        objSecondCal.set(year2 + 1900, month2, date2);
        long nDays = 0;
        nDays =
                (objSecondCal.getTime().getTime() - objFirstCal.getTime()
                        .getTime()) / (24 * 3600 * 1000);
        return Math.abs(nDays);

    }

    /**
     * Checks if is within range.
     * @param currDate the curr date
     * @param startDate the start date
     * @param endDate the end date
     * @return boolean
     */
    public static boolean isWithinRange(Date currDate, Date startDate,
            Date endDate) {
        boolean withinRange = false;

        if (currDate.getTime() >= startDate.getTime()
                && currDate.getTime() <= endDate.getTime()) {
            withinRange = true;
        }
        return withinRange;

    }

    // returns date as number
    /**
     * Gets the number from date.
     * @param inputDate the input date
     * @return the number from date
     */
    public static long getNumberFromDate(Date inputDate) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(inputDate);
        long numFromDate = 0;
        String numFromDateStr = "";
        String dayStr = "";
        String monthStr = "";
        String yearStr = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        dayStr = formatter.format(inputDate);
        formatter = new SimpleDateFormat("MM");
        monthStr = formatter.format(inputDate);
        formatter = new SimpleDateFormat("yyyy");
        yearStr = formatter.format(inputDate);
        numFromDateStr = yearStr + monthStr + dayStr;
        numFromDate = Long.parseLong(numFromDateStr);

        return numFromDate;
    }
    
    public static boolean isDateBetweenTwoDays(Date fromDate, Date toDate, Date toCompare){
    	
    	boolean isBetween =toCompare.compareTo(fromDate) >= 0 && toCompare.compareTo(toDate) <= 0;
    	return isBetween;
    }
    
    public static void main(String[] args) throws ParseException {
        int year = 2003;
        int month = 12;
        int day = 12;

        String fromDate = "2003/12/09";
        
        String toDate = "2004/12/11";
        String currentDate ="2003/12/12";
        
        java.util.Date utilDate = null;
        java.util.Date utilDate2 = null;
        java.util.Date utilDate3 = null;

        try {
          SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
          utilDate = formatter.parse(fromDate);
          utilDate2 = formatter.parse(toDate);
          utilDate3 = formatter.parse(currentDate);
        } catch (ParseException e) {
          e.printStackTrace();
        }

      }
    public static  boolean isSameDateAndMonth(Date systemDate,Date inputDate){
    	
		java.util.Calendar systemDateCal = java.util.Calendar.getInstance();
		systemDateCal.setTime(systemDate);
		java.util.Calendar inputDateCalendar = java.util.Calendar.getInstance();
		inputDateCalendar.setTime(inputDate);
		if((systemDateCal.get(java.util.Calendar.DATE) == inputDateCalendar.get(java.util.Calendar.DATE))&&
				(systemDateCal.get(java.util.Calendar.MONTH) == inputDateCalendar.get(java.util.Calendar.MONTH))){					
				return true;
		}
    	return false;
    }
  public static  boolean isBetweenDateAndMonth(Date fromDate,Date toDate,Date inputDate){
    	
		java.util.Calendar fromDateCal = java.util.Calendar.getInstance();
		fromDateCal.setTime(fromDate);
		java.util.Calendar toDateCal = java.util.Calendar.getInstance();
		toDateCal.setTime(toDate);
		java.util.Calendar inputDateCalendar = java.util.Calendar.getInstance();
		inputDateCalendar.setTime(inputDate);
		
		if((fromDateCal.get(java.util.Calendar.DATE)<  inputDateCalendar.get(java.util.Calendar.DATE) &&toDateCal.get(java.util.Calendar.DATE)>= inputDateCalendar.get(java.util.Calendar.DATE))
				&& (fromDateCal.get(java.util.Calendar.MONTH) == inputDateCalendar.get(java.util.Calendar.MONTH))){
			return true;
		}
    	return false;
    }
  /**
	 * Subtract working days.
	 *
	 * @param currentDate the current date
	 * @param numOfDays the num of days
	 * @param session the session
	 * @return the date
	 */
	public static Date subtractWorkingDays(Date currentDate, Long numOfDays, Session session) {
		Date previousDate = currentDate;
		if (numOfDays != null) {
			for (int i = 1; i <= numOfDays.intValue(); i++) {
				previousDate = DateUtils.subtractDays(previousDate, 1);
				/*while (isHoliday(previousDate, session)) {
					previousDate = DateUtils.subtractDays(previousDate, 1);
				}*/
			}
		}
		return previousDate;
	}

	/**
	 * Subtract days.
	 *
	 * @param date the date
	 * @param numOfDays the num of days
	 * @return the date
	 */
	public static Date subtractDays(Date date, int numOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -(numOfDays)); // subtract x day
		return cal.getTime();
	}
	
	/**
	 * Subtract months.
	 *
	 * @param date the date
	 * @param numOfMonths the num of months
	 * @return the date
	 */
	public static Date subtractMonths(Date date, int numOfMonths) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -(numOfMonths)); // subtract x day
		return cal.getTime();
	}

	
}
