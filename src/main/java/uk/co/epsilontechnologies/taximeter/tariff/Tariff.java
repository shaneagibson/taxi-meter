package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import uk.co.epsilontechnologies.taximeter.model.Fare;

import java.math.BigDecimal;

/**
 * <p>Representation of a TfL Taxi Tariff.
 *
 * @author Shane Gibson
 */
public interface Tariff {

    boolean applies(DateTime dateTime);

    BigDecimal getFlagFallAmount();

    BigDecimal getFlagFallDistanceLimit();

    BigDecimal getFlagFallTimeLimit();

    SubTariff getLowFareSubTariff();

    SubTariff getHighFareSubTariff();

    BigDecimal getHighLowFareBoundary();

    boolean hasMinimumChargeBeenExceeded(BigDecimal journeyTime, BigDecimal journeyDistance);

    boolean isHighFare(final Fare fare);

    interface SubTariff {

        BigDecimal getDistanceLimit();

        BigDecimal getTimeLimit();

        BigDecimal getIncrementAmount();

    }

}
