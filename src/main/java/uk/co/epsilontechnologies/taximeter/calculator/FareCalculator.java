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
     * @param tariffLookup the tariff lookup to use
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

            final BigDecimal incrementedFare = currentFare.getAmount().add(subTariff.getIncrementAmount());

            /*
              If the distance (or duration) has surpassed the period that has been accounted for, we need to increment the distance
              from the start of the period that's been accounted for - otherwise we increment it from the distance (or duration)
              of the journey. This ensures there are no 'gaps' in the distance (or duration) that has been accounted for.
            */

            final BigDecimal incrementedDistanceAccountedFor =
                    ((distanceUnaccountedFor.compareTo(BigDecimal.ZERO) > 0) ? currentFare.getJourneyDistanceAccountedFor() : journeyDistance)
                            .add(subTariff.getDistanceLimit());

            final BigDecimal incrementedDurationAccountedFor =
                    ((durationUnaccountedFor.compareTo(BigDecimal.ZERO) > 0) ? currentFare.getJourneyDurationAccountedFor() : journeyDuration)
                            .add(subTariff.getTimeLimit());

            return new Fare(
                    incrementedFare,
                    incrementedDistanceAccountedFor,
                    incrementedDurationAccountedFor);
        }

        return currentFare;
    }

}