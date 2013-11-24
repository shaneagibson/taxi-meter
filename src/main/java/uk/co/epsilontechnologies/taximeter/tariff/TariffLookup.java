package uk.co.epsilontechnologies.taximeter.tariff;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Lookup class for the tariff based on the date time that has been provided and the rules for each of the tariffs.
 *
 * <p>This assumes:
 * <ul>
 *   <li>There are no overlaps between tariffs - a DateTime will only match one tariff.
 *   <li>There will always be a tariff for a given DateTime - no gaps exist between the rules of the registered tariffs.
 * </ul>
 *
 * @author Shane Gibson
 */
public class TariffLookup {

    /**
     * The tariffs to check.
     */
    private final List<Tariff> tariffs;

    /**
     * Constructs the tariff lookup for the given tariffs.
     *
     * @param tariffs The tariffs to check
     */
    public TariffLookup(final Tariff... tariffs) {
        this.tariffs = Arrays.asList(tariffs);
    }

    /**
     * Looks up the correct tariff based on the given date time.
     *
     * @param dateTime the date time to check
     * @return the first matching tariff
     * @throws IllegalStateException No tariff is found for the given date time
     */
    public Tariff lookupTariff(final DateTime dateTime) {
        for (final Tariff tariff : tariffs) {
            if (tariff.applies(dateTime)) {
                return tariff;
            }
        }
        throw new IllegalStateException("Unable to resolve tariff for time: "+dateTime);
    }

}
