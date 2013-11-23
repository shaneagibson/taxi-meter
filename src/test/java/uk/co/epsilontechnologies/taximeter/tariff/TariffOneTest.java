package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for {@link TariffOne}.
 *
 * @author Shane Gibson
 */
public class TariffOneTest {

    private final TariffOne underTest = new TariffOne();

    @Test
    public void shouldNotApplyTariffIfDateTimeIsMondayToFridayBeforeSixAM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 25, 5, 30, 0, 0)));
    }

    @Test
    public void shouldApplyTariffIfDateTimeIsMondayToFridayAtExactlySixAM() {
        assertTrue(this.underTest.applies(new DateTime(2013, 11, 25, 6, 0, 0, 0)));
    }

    @Test
    public void shouldApplyTariffIfDateTimeIsMondayToFridayBetween6AMAnd8PM() {
        assertTrue(this.underTest.applies(new DateTime(2013, 11, 25, 10, 30, 10, 500)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsMondayToFridayAtExactly8PM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 25, 20, 00, 0, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsMondayToFridayAfter8PM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 25, 20, 30, 0, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsMondayToFridayAtSixAMOnPublicHoliday() {
        assertFalse(this.underTest.applies(new DateTime(2014, 1, 1, 6, 0, 0, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsSaturdayToSundayBetween6AMAnd8PM() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 23, 10, 30, 10, 500)));
    }


}
