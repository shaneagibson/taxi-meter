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
 * <p>API for a the meter of a Taxi. This exposes the key features of a Taxi Journey's interaction with the Taxi Meter.
 *
 * @author Shane Gibson
 */
public interface TaxiMeter {

    /**
     * Starts a journey.
     */
    void startJourney();

    /**
     * Ends the journey.
     */
    void endJourney();

    /**
     * Resets the meter so it can accept a new passenger.
     */
    void reset();

    /**
     * Retrieves the fare for the in-progress or completed journey.
     * @return the fare amount
     */
    BigDecimal getFare();

}