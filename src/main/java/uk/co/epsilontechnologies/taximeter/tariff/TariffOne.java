package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
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

}
