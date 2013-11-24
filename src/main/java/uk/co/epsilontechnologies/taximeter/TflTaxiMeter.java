package uk.co.epsilontechnologies.taximeter;

import org.joda.time.DateTime;
import uk.co.epsilontechnologies.taximeter.calculator.FareCalculator;
import uk.co.epsilontechnologies.taximeter.model.Fare;
import uk.co.epsilontechnologies.taximeter.tariff.Tariff1;
import uk.co.epsilontechnologies.taximeter.tariff.Tariff2;
import uk.co.epsilontechnologies.taximeter.tariff.Tariff3;
import uk.co.epsilontechnologies.taximeter.tariff.TariffLookup;

import java.math.BigDecimal;

import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.differenceInSeconds;

/**
 * <p>API for a the meter of a TfL Taxi. This exposes the key features of a Taxi Journey's interaction with the Taxi Meter.
 *
 * @author Shane Gibson
 */
public class TflTaxiMeter implements Runnable, TaxiMeter {

    /**
     * The poller that will be used to update the fare consistently during the journey.
     */
    private final Poller poller;

    /**
     * The calculator that will be used to calculate the fare consistently during the journey.
     */
    private final FareCalculator fareCalculator;

    /**
     * The odometer that will be used to determine the distance travelled during the journey.
     */
    private final Odometer odometer;

    /**
     * The fare at any given point of the journey.
     */
    protected Fare fare;

    /**
     * The start time fo the journey.
     */
    protected DateTime startTime;

    /**
     * The end time fo the journey.
     */
    protected DateTime endTime;


    /**
     * Constructs the Taxi Meter for the given Odometer, using the standard TariffLookup (with Tariff1, Tariff2 and
     * Tariff3) and standard Poller.
     *
     * @param odometer the odometer to use
     */
    public TflTaxiMeter(final Odometer odometer) {
        this(new Poller(), new FareCalculator(new TariffLookup(new Tariff1(), new Tariff2(), new Tariff3())), odometer);
    }

    /**
     * Constructs the Taxi Meter for the given Poller, Fare Calculator and Odometer.
     *
     * @param poller the poller to use
     * @param fareCalculator the fare calculator to use
     * @param odometer the odometer to use
     */
    protected TflTaxiMeter(final Poller poller, final FareCalculator fareCalculator, final Odometer odometer) {
        this.poller = poller;
        this.fareCalculator = fareCalculator;
        this.odometer = odometer;
    }

    /**
     * @see TaxiMeter#startJourney()
     */
    @Override
    public void startJourney() {
        if (startTime != null || endTime != null) {
            throw new IllegalStateException("Journey already in progress");
        }
        this.odometer.reset();
        this.startTime = new DateTime();
        this.poller.start(this);
        this.fare = fareCalculator.getFlagFall(startTime);
    }

    /**
     * @see TaxiMeter#endJourney()
     */
    @Override
    public void endJourney() {
        if (startTime == null || endTime != null) {
            throw new IllegalStateException("Journey not in progress");
        }
        this.poller.stop();
    }

    /**
     * @see TaxiMeter#reset()
     */
    @Override
    public void reset() {
        if (startTime != null && endTime == null) {
            throw new IllegalStateException("Journey still in progress");
        }
        this.endTime = null;
        this.startTime = null;
        this.fare = null;
        this.odometer.reset();
    }

    /**
     * @see TaxiMeter#getFare()
     */
    @Override
    public BigDecimal getFare() {
        if (fare != null) {
            return fare.getAmount();
        }
        return null;
    }

    /**
     * Updates the fare according to the current time and / or distance.
     */
    @Override
    public void run() {
        final DateTime now = new DateTime();
        this.fare = fareCalculator.calculateFare(fare, differenceInSeconds(now, startTime), odometer.getDistance(), now);
    }

}