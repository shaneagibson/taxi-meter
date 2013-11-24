package uk.co.epsilontechnologies.taximeter.tariff;

import uk.co.epsilontechnologies.taximeter.model.Fare;

import java.math.BigDecimal;

/**
 * @see Tariff
 *
 * <p>Encapsulates common Tariff logic
 *
 * @author Shane Gibson
 */
public abstract class AbstractTariff implements Tariff {

    /**
     * @see Tariff#isHighFare(Fare)
     */
    @Override
    public boolean isHighFare(final Fare fare) {
        return fare.getAmount().compareTo(this.getHighLowFareBoundary()) >= 0;
    }

    /**
     * @see Tariff#hasMinimumChargeBeenExceeded(BigDecimal, BigDecimal)
     */
    @Override
    public boolean hasMinimumChargeBeenExceeded(final BigDecimal journeyTime, final BigDecimal journeyDistance) {
        return journeyTime.compareTo(this.getFlagFallTimeLimit()) > 0 || journeyDistance.compareTo(this.getFlagFallDistanceLimit()) > 0;
    }

}