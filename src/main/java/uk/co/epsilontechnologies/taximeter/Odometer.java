package uk.co.epsilontechnologies.taximeter;

import java.math.BigDecimal;

/**
 * <p>Interface for Odometer implementation.
 *
 * <p>The implementation of this should provide access to the Odometer functions of the Taxi.
 *
 * @author Shane Gibson
 */
public interface Odometer {

    /**
     * Retrieves the distance travelled since the odometer was last reset.
     *
     * @return the current distance travelled
     */
    BigDecimal getDistance();

    /**
     * Resets the odometer to zero.
     */
    void reset();

}
