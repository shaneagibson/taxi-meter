package uk.co.epsilontechnologies.taximeter.utils;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Class for {@link CalendarUtils}.
 *
 * @author Shane Gibson
 */
public class CalendarUtilsTest {

    @Test
    public void shouldReturnTrueIfDateIsPublicHoliday() {
        assertTrue(CalendarUtils.isPublicHoliday(new DateTime(2014, 1, 1, 0, 0)));
    }

    @Test
    public void shouldReturnFalseIfDateIsNotPublicHoliday() {
        assertFalse(CalendarUtils.isPublicHoliday(new DateTime(2014, 1, 2, 0, 0)));
    }

    @Test
    public void shouldCalculateDifferenceInSecondsBetweenTwoDateTimes() {
        final DateTime timeA = new DateTime(2000, 1, 1, 12, 0, 5, 250);
        final DateTime timeB = new DateTime(2000, 1, 1, 12, 1, 10, 500);
        final double differenceInSeconds =  CalendarUtils.differenceInSeconds(timeA, timeB);
        assertEquals(65.25, differenceInSeconds, 0.001);
    }

}
