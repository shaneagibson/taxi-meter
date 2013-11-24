package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>Test Class for {@link Tariff3}
 *
 * @author Shane Gibson
 */
public class Tariff3Test {

    private Tariff3 underTest = new Tariff3();

    @Test
    public void shouldApplyTariffIfDateTimeIsExactly10PMAnyDay() {
        assertTrue(this.underTest.applies(new DateTime(2013, 11, 24, 22, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsExactly10PMAnyDay() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 24, 21, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsExactly6AMAnyDay() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 24, 6, 0)));
    }

    @Test
    public void shouldNotApplyTariffIfDateTimeIsAfter6AMAnyDay() {
        assertFalse(this.underTest.applies(new DateTime(2013, 11, 24, 6, 30)));
    }

    @Test
    public void shouldApplyTariffIfDateIsPublicHoliday() {
        assertTrue(this.underTest.applies(new DateTime(2014, 1, 1, 12, 30, 0, 0)));
    }

}