package uk.co.epsilontechnologies.taximeter.util;

import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.*;

/**
 * Test Class for {@link CalendarUtilsTest}.
 *
 * @author Shane Gibson
 */
public class CalendarUtilsTest {

    @Test
    public void shouldReturnTrueIfDateIsPublicHoliday() {
        assertTrue(isPublicHoliday(new DateTime(2014, 1, 1, 0, 0)));
    }

    @Test
    public void shouldReturnFalseIfDateIsNotPublicHoliday() {
        assertFalse(isPublicHoliday(new DateTime(2014, 1, 2, 0, 0)));
    }

    @Test
    public void shouldCalculateDifferenceInSecondsBetweenTwoDateTimes() {
        final DateTime timeA = new DateTime(2000, 1, 1, 12, 0, 5, 250);
        final DateTime timeB = new DateTime(2000, 1, 1, 12, 1, 10, 500);
        final BigDecimal differenceInSeconds = differenceInSeconds(timeA, timeB);
        assertEquals(65.25, differenceInSeconds.doubleValue(), 0.001);
    }

    @Test
    public void shouldReturnFalseWhenIsWeekdayIsGivenWeekend() {
        assertFalse(isWeekday(new DateTime(2013, 11, 24, 6, 0)));
    }

    @Test
    public void shouldReturnTrueWhenIsWeekdayIsGivenWeekday() {
        assertTrue(isWeekday(new DateTime(2013, 11, 25, 6, 0)));
    }

    @Test
    public void shouldReturnFalseWhenIsWeekendIsGivenWeekday() {
        assertFalse(isWeekend(new DateTime(2013, 11, 25, 6, 0)));
    }

    @Test
    public void shouldReturnTrueWhenIsWeekendIsGivenWeekend() {
        assertTrue(isWeekend(new DateTime(2013, 11, 24, 6, 0)));
    }

    @Test
    public void shouldReturnTrueWhenGivenDateIsBetweenGivenHours() {
        assertTrue(isBetweenHours(new DateTime(2013, 11, 24, 7, 0), 6, 10));
    }

    @Test
    public void shouldReturnFalseWhenGivenDateIsNotBetweenGivenHours() {
        assertFalse(isBetweenHours(new DateTime(2013, 11, 24, 7, 0), 10, 15));
    }

}

