package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.junit.Test;
import uk.co.epsilontechnologies.taximeter.model.Distance;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for {@link TariffOne}.
 *
 * @author Shane Gibson
 */
public class TariffOneTest {

    private final TariffOne underTest = new TariffOne();

    // applies(DateTime)

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


    // calculateRate(BigDecimal, DateTime, double, DateTime)

    @Test
    public void shouldCalculateRateOfGBP2_40ForDistanceOf254_5Meters() {
        assertBigDecimalEquals(new BigDecimal("2.40"), underTest.calculateFare(new Distance("254.5")).getAmount());
    }

    @Test
    public void shouldCalculateRateOfGBP2_40ForDistanceOf254_6Meters() {
        assertBigDecimalEquals(new BigDecimal("2.40"), underTest.calculateFare(new Distance("254.6")).getAmount());
    }

    @Test
    public void shouldCalculateRateOfGBP2_60ForDistanceOf254_7Meters() {
        assertBigDecimalEquals(new BigDecimal("2.60"), underTest.calculateFare(new Distance("254.7")).getAmount());
    }

    @Test
    public void shouldReturnGBP2_60ForDistanceOf381_8Meters() {
        assertBigDecimalEquals(new BigDecimal("2.60"), underTest.calculateFare(new Distance("381.8")).getAmount());
    }

    @Test
    public void shouldReturnGBP2_60ForDistanceOf381_9Meters() {
        assertBigDecimalEquals(new BigDecimal("2.60"), underTest.calculateFare(new Distance("381.9")).getAmount());
    }

    @Test
    public void shouldReturnGBP2_80ForDistanceOf382_0Meters() {
        assertBigDecimalEquals(new BigDecimal("2.80"), underTest.calculateFare(new Distance("382.0")).getAmount());
    }

    @Test
    public void shouldReturnGBP2_80ForDistanceOf509_2Meters() {
        assertBigDecimalEquals(new BigDecimal("2.80"), underTest.calculateFare(new Distance("509.2")).getAmount());
    }

    @Test
    public void shouldReturnGBP3_00ForDistanceOf636_5Meters() {
        assertBigDecimalEquals(new BigDecimal("3.00"), underTest.calculateFare(new Distance("636.5")).getAmount());
    }

    @Test
    public void shouldReturnGBP17_20ForDistanceOf9674_8Meters() {
        assertBigDecimalEquals(new BigDecimal("17.20"), underTest.calculateFare(new Distance("9674.8")).getAmount());
    }

    @Test
    public void shouldReturnGBP17_40ForDistanceOf9764_0Meters() {
        assertBigDecimalEquals(new BigDecimal("17.40"), underTest.calculateFare(new Distance("9764.0")).getAmount());
    }

    /**
     * Simplifies the comparison of two BigDecimal values, by throwing an AssertionError with a meaningful message when
     * the expected and actual are not equal.
     *
     * @param expected the expected big decimal value
     * @param actual the actual big decimal value
     * @throws AssertionError if the expected and actual values are not the same
     */
    private void assertBigDecimalEquals(final BigDecimal expected, final BigDecimal actual) {
        if (expected.compareTo(actual) != 0) {
            throw new AssertionError("expected "+expected+" but was "+actual);
        }
    }

}
