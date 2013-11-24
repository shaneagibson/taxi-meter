package uk.co.epsilontechnologies.taximeter.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>Utility Class for Date, Time and Calendar functions.
 *
 * @Author Shane Gibson
 */
public final class CalendarUtils {

    /**
     * List of UK public holidays for 2013, 2014, and 2015.
     */
    private static final List<LocalDate> PUBLIC_HOLIDAYS = loadPublicHolidays();

    /**
     * Loads the public holidays from the publicholidays.txt file into an in-memory list.
     */
    private static List<LocalDate> loadPublicHolidays() {
        try (final InputStream publicHolidaysTxtInputStream = CalendarUtils.class.getClassLoader().getResourceAsStream("publicholidays.txt")) {
            final List<String> lines = IOUtils.readLines(publicHolidaysTxtInputStream);
            final List<LocalDate> list = new ArrayList<>();
            for (final String line : lines) {
                final StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                final int year = Integer.parseInt(stringTokenizer.nextToken());
                final int month = Integer.parseInt(stringTokenizer.nextToken());
                final int day = Integer.parseInt(stringTokenizer.nextToken());
                list.add(new LocalDate(year, month, day));
            }
            return list;
        } catch (final IOException e) {
            throw new RuntimeException("Unable to load public holidays: "+e);
        }
    }

    /**
     * <p>Hidden default constructor
     */
    private CalendarUtils() {
        super();
    }

    /**
     * <p>Determines if the current date is a public holiday.
     *
     * @param dateTime the date to check
     * @return true if the date is a public holiday, otherwise false
     */
    public static boolean isPublicHoliday(final DateTime dateTime) {
        for (final LocalDate publicHoliday : PUBLIC_HOLIDAYS) {
            if (dateTime.toLocalDate().equals(publicHoliday)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Calculates the difference between the to and from date times, in seconds.
     *
     * @param to the date time to calculate from
     * @param from the date time to calculate to
     * @return the difference in seconds between the from and to date times, as a double
     */
    public static BigDecimal differenceInSeconds(final DateTime to, final DateTime from) {
        return new BigDecimal(from.getMillis()).subtract(new BigDecimal(to.getMillis())).divide(new BigDecimal("1000"));
    }

    /**
     * <p>Determines if the given date is a weekday (Monday to Friday).
     *
     * @param dateTime the date to check
     * @return true if given date is a weekday, false otherwise
     */
    public static boolean isWeekday(final DateTime dateTime) {
        return dateTime.getDayOfWeek() >= DateTimeConstants.MONDAY && dateTime.getDayOfWeek() <= DateTimeConstants.FRIDAY;
    }

    /**
     * <p>Determines if the given date is a weekend (Saturday or Sunday).
     *
     * @param dateTime the date to check
     * @return true if given date is a weekend, false otherwise
     */
    public static boolean isWeekend(final DateTime dateTime) {
        return dateTime.getDayOfWeek() == DateTimeConstants.SATURDAY || dateTime.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }

    /**
     * <p>Determines if the given date time is between the startHour and endHour (on the same day).
     *
     * @param dateTime the date/time to check
     * @return true if given date/time is between the startHour and endHour on the same day, false otherwise
     */
    public static boolean isBetweenHours(final DateTime dateTime, final int startHour, final int endHour) {
        return dateTime.getHourOfDay() >= startHour && dateTime.getHourOfDay() < endHour;
    }

}