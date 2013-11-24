package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * <p>Test Class for {@link TariffLookup}
 *
 * @author Shane Gibson
 */
public class TariffLookupTest {

    private TariffLookup underTest;

    @Mock
    private Tariff mockTariff1;

    @Mock
    private Tariff mockTariff2;

    @Mock
    private Tariff mockTariff3;

    @Before
    public void setUp() {
        initMocks(this);
        this.underTest = new TariffLookup(mockTariff1, mockTariff2, mockTariff3);
    }

    @Test
    public void shouldGetTariffForTheGivenTime() {

        // arrange
        final DateTime now = new DateTime();
        when(mockTariff1.applies(now)).thenReturn(false);
        when(mockTariff2.applies(now)).thenReturn(false);
        when(mockTariff3.applies(now)).thenReturn(true);

        // act
        final Tariff result = this.underTest.lookupTariff(now);

        // assert
        assertEquals(mockTariff3, result);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfNoTariffApplies() {

        // arrange
        final DateTime now = new DateTime();
        when(mockTariff1.applies(now)).thenReturn(false);
        when(mockTariff2.applies(now)).thenReturn(false);
        when(mockTariff3.applies(now)).thenReturn(false);

        // act
        final Tariff result = this.underTest.lookupTariff(now);

        // assert
        fail("IllegalStateException was expected but not thrown");
    }
}
