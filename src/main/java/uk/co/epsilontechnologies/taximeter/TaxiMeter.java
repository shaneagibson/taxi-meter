package uk.co.epsilontechnologies.taximeter;

import org.joda.time.DateTime;
import uk.co.epsilontechnologies.taximeter.calculator.FareCalculator;
import uk.co.epsilontechnologies.taximeter.model.Fare;

import java.math.BigDecimal;

import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.differenceInSeconds;

/**
 * <p>API for a the meter of a TfL Taxi. This exposes the key features of a Taxi Journey's interaction with the Taxi Meter.
 *
 * @author Shane Gibson
 */
public class TaxiMeter implements Runnable {

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
     * Constructs the Taxi Meter for the given Poller, Fare Calculator and Odometer.
     *
     * @param poller the poller to use
     * @param fareCalculator the fare calculator to use
     * @param odometer the odometer to use
     */
    public TaxiMeter(final Poller poller, final FareCalculator fareCalculator, final Odometer odometer) {
        this.poller = poller;
        this.fareCalculator = fareCalculator;
        this.odometer = odometer;
    }

    /**
     * Starts a journey, triggering the flag fall and starts the meter counting.
     */
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
     * Ends the journey, stopping the meter from counting.
     */
    public void endJourney() {
        if (startTime == null || endTime != null) {
            throw new IllegalStateException("Journey not in progress");
        }
        this.poller.stop();
    }

    /**
     * Resets the odometer, journey start time and fare.
     */
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
     * Retrieves the fare for the in-progress or completed journey.
     * @return
     */
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