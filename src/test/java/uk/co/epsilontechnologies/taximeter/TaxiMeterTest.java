package uk.co.epsilontechnologies.taximeter;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.epsilontechnologies.taximeter.calculator.FareCalculator;
import uk.co.epsilontechnologies.taximeter.model.Fare;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test Class for {@link TaxiMeter}.
 *
 * @author Shane Gibson
 */
public class TaxiMeterTest {

    private TaxiMeter underTest;

    @Mock
    private Poller mockPoller;

    @Mock
    private FareCalculator mockFareCalculator;

    @Mock
    private Odometer mockOdometer;

    @Before
    public void setUp() {
        initMocks(this);
        this.underTest = new TaxiMeter(mockPoller, mockFareCalculator, mockOdometer);
    }

    @Test
    public void shouldStartJourney() {

        // arrange
        final Fare fare = mock(Fare.class);
        when(mockFareCalculator.getFlagFall(any(DateTime.class))).thenReturn(fare);

        // act
        this.underTest.startJourney();

        // assert
        assertNotNull(this.underTest.startTime);
        assertNull(this.underTest.endTime);
        assertEquals(fare, this.underTest.fare);
        verify(mockOdometer).reset();
        verify(mockPoller).start(this.underTest);
    }

    @Test
    public void shouldEndJourney() {

        // arrange
        this.underTest.startTime = new DateTime();

        // act
        this.underTest.endJourney();

        // assert
        verify(mockPoller).stop();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailToEndJourneyOutOfSequence() {

        // arrange
        this.underTest.startTime = null;

        // act
        this.underTest.endJourney();

        // assert
        fail("IllegalStateException was expected but not thrown");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailToStartJourneyOutOfSequence() {

        // arrange
        this.underTest.startTime = new DateTime();

        // act
        this.underTest.startJourney();

        // assert
        fail("IllegalStateException was expected but not thrown");
    }

    @Test
    public void shouldResetJourney() {

        // arrange
        this.underTest.startTime = new DateTime();
        this.underTest.endTime = new DateTime();

        // act
        this.underTest.reset();

        // assert
        assertNull(this.underTest.startTime);
        assertNull(this.underTest.endTime);
        assertNull(this.underTest.fare);
        verify(this.mockOdometer).reset();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailToResetJourneyOutOfSequence() {

        // arrange
        this.underTest.startTime = new DateTime();

        // act
        this.underTest.reset();

        // assert
        fail("IllegalStateException was expected but not thrown");
    }

    @Test
    public void shouldGetFareAmount() {

        // arrange
        this.underTest.fare = new Fare(new BigDecimal("10.00"), new BigDecimal("100"), new BigDecimal("60"));

        // act
        final BigDecimal result = this.underTest.getFare();

        // assert
        assertEquals(new BigDecimal("10.00"), result);
    }

    @Test
    public void shouldGetFareAmountWhenNull() {

        // arrange
        this.underTest.fare = null;

        // act
        final BigDecimal result = this.underTest.getFare();

        // assert
        assertEquals(null, result);
    }

    @Test
    public void shouldCalculateFare() {

        // arrange
        final Fare existingFare = new Fare(new BigDecimal("10.00"), new BigDecimal("100"), new BigDecimal("60"));
        final Fare newFare = new Fare(new BigDecimal("100.00"), new BigDecimal("1000"), new BigDecimal("600"));
        final BigDecimal distance = new BigDecimal("123.00");
        this.underTest.fare = existingFare;
        this.underTest.startTime = new DateTime();
        when(mockOdometer.getDistance()).thenReturn(distance);
        when(mockFareCalculator.calculateFare(eq(existingFare), any(BigDecimal.class), eq(distance), any(DateTime.class))).thenReturn(newFare);

        // act
        this.underTest.run();

        // assert
        verify(mockFareCalculator).calculateFare(eq(existingFare), any(BigDecimal.class), eq(distance), any(DateTime.class));

    }

}