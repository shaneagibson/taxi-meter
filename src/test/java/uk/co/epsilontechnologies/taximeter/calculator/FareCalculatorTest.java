package uk.co.epsilontechnologies.taximeter.calculator;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.epsilontechnologies.taximeter.model.Fare;
import uk.co.epsilontechnologies.taximeter.tariff.Tariff1;
import uk.co.epsilontechnologies.taximeter.tariff.TariffLookup;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * <p>Test Class for {@link FareCalculator}
 *
 * @author Shane Gibson
 */
public class FareCalculatorTest {

    private FareCalculator underTest;

    @Mock
    private TariffLookup mockTariffLookup;

    @Before
    public void setUp() {
        initMocks(this);
        this.underTest = new FareCalculator(mockTariffLookup);
        when(mockTariffLookup.lookupTariff(any(DateTime.class))).thenReturn(new Tariff1());
    }

    @Test
    public void shouldGetFlagFallForTariff() {

        // act
        final Fare result = this.underTest.getFlagFall(new DateTime());

        // assert
        assertEquals(new BigDecimal("2.40"), result.getAmount());
        assertEquals(new BigDecimal("254.6"), result.getJourneyDistanceAccountedFor());
        assertEquals(new BigDecimal("54.8"), result.getJourneyDurationAccountedFor());
    }

    @Test
    public void shouldNotIncrementFareWhenMinimumChargeNotExceeded() {

        // arrange
        final Fare fare = new Fare(new BigDecimal("2.40"), new BigDecimal("254.6"), new BigDecimal("54.8"));

        // act
        final Fare result = this.underTest.calculateFare(fare, new BigDecimal("40"), new BigDecimal("100"), new DateTime());

        // assert
        assertEquals(new BigDecimal("2.40"), result.getAmount());
        assertEquals(new BigDecimal("254.6"), result.getJourneyDistanceAccountedFor());
        assertEquals(new BigDecimal("54.8"), result.getJourneyDurationAccountedFor());
    }

    @Test
    public void shouldIncrementFareWhenTimeLimitExceeded() {

        // arrange
        final Fare fare = new Fare(new BigDecimal("2.40"), new BigDecimal("254.6"), new BigDecimal("54.8"));

        // act
        final Fare result = this.underTest.calculateFare(fare, new BigDecimal("60"), new BigDecimal("100"), new DateTime());

        // assert
        assertEquals(new BigDecimal("2.60"), result.getAmount());
        assertEquals(new BigDecimal("381.9"), result.getJourneyDistanceAccountedFor());
        assertEquals(new BigDecimal("82.2"), result.getJourneyDurationAccountedFor());
    }

    @Test
    public void shouldIncrementFareWhenDistanceLimitExceeded() {

        // arrange
        final Fare fare = new Fare(new BigDecimal("2.40"), new BigDecimal("254.6"), new BigDecimal("54.8"));

        // act
        final Fare result = this.underTest.calculateFare(fare, new BigDecimal("40"), new BigDecimal("270"), new DateTime());

        // assert
        assertEquals(new BigDecimal("2.60"), result.getAmount());
        assertEquals(new BigDecimal("381.9"), result.getJourneyDistanceAccountedFor());
        assertEquals(new BigDecimal("82.2"), result.getJourneyDurationAccountedFor());
    }

    @Test
    public void shouldNotIncrementFareWhenTimeAndDistanceLimitNotExceeded() {

        // arrange
        final Fare fare = new Fare(new BigDecimal("2.60"), new BigDecimal("381.9"), new BigDecimal("82.2"));

        // act
        final Fare result = this.underTest.calculateFare(fare, new BigDecimal("80"), new BigDecimal("375"), new DateTime());

        // assert
        assertEquals(new BigDecimal("2.60"), result.getAmount());
        assertEquals(new BigDecimal("381.9"), result.getJourneyDistanceAccountedFor());
        assertEquals(new BigDecimal("82.2"), result.getJourneyDurationAccountedFor());
    }

    @Test
    public void shouldIncrementHighFareAccordingToHighFareSubTariff() {

        // arrange
        final Fare fare = new Fare(new BigDecimal("17.2"), new BigDecimal("9674.8"), new BigDecimal("2082.4"));

        // act
        final Fare result = this.underTest.calculateFare(fare, new BigDecimal("2082"), new BigDecimal("9700"), new DateTime());

        // assert
        assertEquals(new BigDecimal("17.40"), result.getAmount());
        assertEquals(new BigDecimal("9764.0"), result.getJourneyDistanceAccountedFor());
        assertEquals(new BigDecimal("2101.6"), result.getJourneyDurationAccountedFor());
    }

}
