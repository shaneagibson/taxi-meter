package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>Test Class for {@link Tariff2}
 *
 * @author Shane Gibson
 */
public class Tariff2Test {

    private Tariff2 underTest = new Tariff2();

    @Test
    public void shouldNotApplyTariffIfDateTimeIsMondayToFridayBeforeEightPM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 25, 19, 30, 0, 0)));
    }

    @Test
    public void shouldApplyTariffIfDateTimeIsMondayToFridayAtExactlyEightPM() {
        assertTrue(this.underTest.applies(new DateTime(2013, 11, 25, 20, 0, 0, 0)));
    }

    @Test
    public void shouldApplyTariffIfDateTimeIsMondayToFridayBetween8PMAnd10PM() {
        assertTrue(this.underTest.applies(new DateTime(2013, 11, 25, 20, 30, 10, 500)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsSaturdayBefore6AM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 23, 5, 00, 0, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsSundayAtExactly10PM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 24, 22, 0, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsSundayAfter10PM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 24, 22, 00, 0, 0)));
    }

    @Test
    public void shouldApplyTariffIfDateTimeIsSaturdayAtExactly6AM() {
        assertTrue(this.underTest.applies(new DateTime(2013, 11, 23, 6, 0, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateIsPublicHoliday() {
        assertFalse(this.underTest.applies(new DateTime(2014, 1, 1, 12, 30, 0, 0)));
    }

}
