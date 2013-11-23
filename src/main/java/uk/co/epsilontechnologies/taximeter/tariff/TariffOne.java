package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import uk.co.epsilontechnologies.taximeter.model.Distance;
import uk.co.epsilontechnologies.taximeter.model.Money;
import uk.co.epsilontechnologies.taximeter.utils.CalendarUtils;

public class TariffOne {

    /**
     * <p>Tariff One applies: "For any hiring during Monday to Friday other than on a public holiday between 06:00 and
     * 20:00"
     *
     * @param queryTime time at which the check is being made for this tariff
     * @return true if the tariff is applicable for the query time, otherwise false
     */
    public boolean applies(final DateTime queryTime) {
        return queryTime.getDayOfWeek() >= DateTimeConstants.MONDAY
                && queryTime.getDayOfWeek() <= DateTimeConstants.FRIDAY
                && queryTime.getHourOfDay() >= 6
                && queryTime.getHourOfDay() < 20
                && !CalendarUtils.isPublicHoliday(queryTime);
    }




    public Money calculateFare(final Distance distance) {
        if (distance.lessThan(new Distance("254.6"))) {
            return new Money("2.40");
        } else {
            return calculate(new Money("2.40"), distance.subtract(new Distance("254.6")));
        }
    }

    private Money calculate(final Money currentFare, final Distance distance) {
        if (distance.lessThanOrEqualTo(new Distance("0.0"))) {
            return currentFare;
        }
        if (currentFare.greaterThanOrEqualTo(new Money("17.20"))) {
            return calculate(currentFare.add(new Money("0.20")), distance.subtract(new Distance("89.2")));
        } else {
            return calculate(currentFare.add(new Money("0.20")), distance.subtract(new Distance("127.3")));
        }
    }

}
