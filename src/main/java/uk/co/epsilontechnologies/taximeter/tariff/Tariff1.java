package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import uk.co.epsilontechnologies.taximeter.utils.CalendarUtils;

import java.math.BigDecimal;

import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.isBetweenHours;
import static uk.co.epsilontechnologies.taximeter.utils.CalendarUtils.isWeekday;

/**
 * @see Tariff
 *
 * <p>For any hiring during Monday to Friday other than on a public holiday between 06:00 and 20:00:
 *
 * <ul>
 *  <li>For the first 254.6 metres or 54.8 seconds (whichever is reached first) there is a minimum charge of £2.40
 *  <li>For each additional 127.3 metres or 27.4 seconds (whichever is reached first), or part thereof, if the fare is less than £17.20 then there is a charge of 20p
 *  <li>Once the fare is £17.20 or greater then there is a charge of 20p for each additional 89.2 metres or 19.2 seconds (whichever is reached first), or part thereof
 * </ul>
 *
 * @author Shane Gibson
 */
public class Tariff1 extends AbstractTariff {

    /**
     * @see Tariff#applies(DateTime)
     */
    @Override
    public boolean applies(final DateTime dateTime) {
        return isWeekday(dateTime) && isBetweenHours(dateTime, 6, 20) && !CalendarUtils.isPublicHoliday(dateTime);
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
        return new BigDecimal("254.6");
    }

    /**
     * @see Tariff#getFlagFallTimeLimit()
     */
    @Override
    public BigDecimal getFlagFallTimeLimit() {
        return new BigDecimal("54.8");
    }

    /**
     * @see Tariff#getHighLowFareBoundary()
     */
    @Override
    public BigDecimal getHighLowFareBoundary() {
        return new BigDecimal("17.20");
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
                return new BigDecimal("127.3");
            }

            /**
             * @see SubTariff#getTimeLimit()
             */
            @Override
            public BigDecimal getTimeLimit() {
                return new BigDecimal("27.4");
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