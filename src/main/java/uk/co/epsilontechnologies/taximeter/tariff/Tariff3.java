package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import uk.co.epsilontechnologies.taximeter.utils.CalendarUtils;

import java.math.BigDecimal;

import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.isBetweenHours;
import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.isWeekday;
import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.isWeekend;

/**
 * @see Tariff
 *
 * <p>For any hiring between 22:00 on any day and 06:00 the following day or at any time on a public holiday:
 *
 * <ul>
 *  <li>For the first 166.8 metres or 35.8 seconds (whichever is reached first) there is a minimum charge of £2.40
 *  <li>For each additional 83.4 metres or 17.9 seconds (whichever is reached first), or part thereof, if the fare is less than £25.20 there is a charge of 20p
 *  <li>Once the fare is £25.20 or greater then there is a charge of 20p for each additional 89.2 metres or 19.2 seconds (whichever is reached first)
 * </ul>
 *
 * @author Shane Gibson
 */
public class Tariff3 extends AbstractTariff {

    /**
     * @see Tariff#applies(DateTime)
     */
    @Override
    public boolean applies(DateTime dateTime) {
        return dateTime.getHourOfDay() < 6 || dateTime.getHourOfDay() >= 22 || CalendarUtils.isPublicHoliday(dateTime);
    }

    /**
     * @see Tariff#getFlagFallAmount()
     */
    @Override
    public BigDecimal getFlagFallAmount() {
        return new BigDecimal("2.40");
    }

    /**
     * @see Tariff#getFlagFallDistanceLimit()
     */
    @Override
    public BigDecimal getFlagFallDistanceLimit() {
        return new BigDecimal("166.8");
    }

    /**
     * @see Tariff#getFlagFallTimeLimit()
     */
    @Override
    public BigDecimal getFlagFallTimeLimit() {
        return new BigDecimal("35.8");
    }

    /**
     * @see Tariff#getHighLowFareBoundary()
     */
    @Override
    public BigDecimal getHighLowFareBoundary() {
        return new BigDecimal("25.20");
    }

    /**
     * @see Tariff#getLowFareSubTariff()
     */
    @Override
    public SubTariff getLowFareSubTariff() {
        return new SubTariff() {

            /**
             * @see SubTariff#getDistanceLimit()
             */
            @Override
            public BigDecimal getDistanceLimit() {
                return new BigDecimal("83.4");
            }

            /**
             * @see SubTariff#getTimeLimit()
             */
            @Override
            public BigDecimal getTimeLimit() {
                return new BigDecimal("17.9");
            }

            /**
             * @see SubTariff#getIncrementAmount()
             */
            @Override
            public BigDecimal getIncrementAmount() {
                return new BigDecimal("0.20");
            }

        };
    }

    /**
     * @see Tariff#getHighFareSubTariff()
     */
    @Override
    public SubTariff getHighFareSubTariff() {
        return new SubTariff() {

            /**
             * @see SubTariff#getIncrementAmount()
             */
            @Override
            public BigDecimal getIncrementAmount() {
                return new BigDecimal("0.20");
            }

            /**
             * @see SubTariff#getDistanceLimit()
             */
            @Override
            public BigDecimal getDistanceLimit() {
                return new BigDecimal("89.2");
            }

            /**
             * @see SubTariff#getTimeLimit()
             */
            @Override
            public BigDecimal getTimeLimit() {
                return new BigDecimal("19.2");
            }

        };
    }

}