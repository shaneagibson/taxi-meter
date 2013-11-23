package uk.co.epsilontechnologies.taximeter.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Utility Class for Date, Time and Calendar functions.
 *
 * @Author Shane Gibson
 */
public final class CalendarUtils {

    /**
     * <p>Hidden default constructor
     */
    private CalendarUtils() {
        super();
    }

    /**
     * <p>List of UK public holidays for 2013, 2014, and 2015.
     */
    private static final List<LocalDate> PUBLIC_HOLIDAYS = new ArrayList<>();

    // TODO - might want to load these in from a properties file
    static {
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 1, 1));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 3, 28));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 3, 31));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 4, 1));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 5, 6));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 5, 27));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 8, 26));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 12, 25));
        PUBLIC_HOLIDAYS.add(new LocalDate(2013, 12, 26));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 1, 1));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 4, 18));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 4, 20));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 4, 21));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 5, 5));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 5, 26));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 8, 25));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 12, 25));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 12, 26));
        PUBLIC_HOLIDAYS.add(new LocalDate(2015, 1, 1));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 4, 3));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 4, 5));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 4, 6));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 5, 4));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 5, 25));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 8, 31));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 12, 25));
        PUBLIC_HOLIDAYS.add(new LocalDate(2014, 12, 28));
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
     * <p>Calculates the absolute difference between the to and from date times, in seconds.
     *
     * @param to the date time to calculate from
     * @param from the date time to calculate to
     * @return the difference in seconds between the from and to date times, as a double
     */
    public static double differenceInSeconds(final DateTime to, final DateTime from) {
        final double difference = (from.getMillis() - to.getMillis()) / 1000d;
        return Math.abs(difference);
    }
}
