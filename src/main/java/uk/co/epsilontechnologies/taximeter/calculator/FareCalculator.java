package uk.co.epsilontechnologies.taximeter.calculator;

import org.joda.time.DateTime;
import uk.co.epsilontechnologies.taximeter.model.Fare;
import uk.co.epsilontechnologies.taximeter.tariff.Tariff;
import uk.co.epsilontechnologies.taximeter.tariff.TariffLookup;

import java.math.BigDecimal;

/**
 * <p>Calculates the taxi fare for the given journey details.
 *
 * @author Shane Gibson
 */
public class FareCalculator {

    /**
     * The tariff lookup to use when determining which tariff applies to the fare calculation.
     */
    private final TariffLookup tariffLookup;

    /**
     * Constructor for this fare calculator.
     *
     * @param tariffLookup the tarrif lookup to use
     */
    public FareCalculator(final TariffLookup tariffLookup) {
        this.tariffLookup = tariffLookup;
    }

    /**
     * Determines the flag fall fare for the given journey start time.
     *
     * @param startTime the start time for the journey
     * @return the flag fall fare
     */
    public Fare getFlagFall(final DateTime startTime) {
        final Tariff tariff = tariffLookup.lookupTariff(startTime);
        return new Fare(
                tariff.getFlagFallAmount(),
                tariff.getFlagFallDistanceLimit(),
                tariff.getFlagFallTimeLimit());
    }

    /**
     * <p>Calculates the latest fare for the current journey.
     *
     * <p>This implementation assumes it is called at least once between every distance or time range.
     *
     * @param currentFare The fare as was last calculated for the last invocation
     * @param journeyDuration The duration of the journey so far
     * @param journeyDistance The distance of the journey so far
     * @param currentTime The time at which the calculation is being invoked
     * @return the fare that has been calculated
     */
    public Fare calculateFare(
            final Fare currentFare,
            final BigDecimal journeyDuration,
            final BigDecimal journeyDistance,
            final DateTime currentTime) {

        final Tariff tariff = tariffLookup.lookupTariff(currentTime);

        if (!tariff.hasMinimumChargeBeenExceeded(journeyDuration, journeyDistance)) {
            return currentFare;
        }

        final Tariff.SubTariff subTariff = tariff.isHighFare(currentFare) ? tariff.getHighFareSubTariff() : tariff.getLowFareSubTariff();

        final BigDecimal distanceUnaccountedFor = journeyDistance.subtract(currentFare.getJourneyDistanceAccountedFor());
        final BigDecimal durationUnaccountedFor = journeyDuration.subtract(currentFare.getJourneyDurationAccountedFor());

        if (distanceUnaccountedFor.compareTo(BigDecimal.ZERO) > 0 || durationUnaccountedFor.compareTo(BigDecimal.ZERO) > 0) {
            return new Fare(
                    currentFare.getAmount().add(subTariff.getIncrementAmount()),
                    currentFare.getJourneyDistanceAccountedFor().add(subTariff.getDistanceLimit()),
                    currentFare.getJourneyDurationAccountedFor().add(subTariff.getTimeLimit()));
        }

        return currentFare;
    }

}