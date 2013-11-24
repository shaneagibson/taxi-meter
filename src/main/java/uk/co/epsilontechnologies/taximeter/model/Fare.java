package uk.co.epsilontechnologies.taximeter.model;

import java.math.BigDecimal;

/**
 * <p>Model object for representing the current fare.
 *
 * <p>The fare is represented as both the amount, and the distance / duration that has been accounted for by the current
 * fare. The journeyDistanceAccountedFor and journeyDurationAccountedFor represent the time and distance at which the
 * current fare amount will expire and the rate will need to be incremented according the the rules of the appropriate
 * tariff.
 *
 * @author Shane Gibson
 */
public class Fare {

    /**
     * The current fare amount for the journey.
     */
    private final BigDecimal amount;

    /**
     * The journey distance that has been currently accounted for by the the fare amount.
     */
    private final BigDecimal journeyDistanceAccountedFor;

    /**
     * The journey duration (time in seconds) that has been currently accounted for by the fare amount.
     */
    private final BigDecimal journeyDurationAccountedFor;

    /**
     * Constructor for the fare.
     *
     * @param amount the fare amount
     * @param journeyDistanceAccountedFor the distance that has been accounted for in the current fare
     * @param journeyDurationAccountedFor the duration that has been accounted for in the current fare
     */
    public Fare(
            final BigDecimal amount,
            final BigDecimal journeyDistanceAccountedFor,
            final BigDecimal journeyDurationAccountedFor) {
        this.amount = amount;
        this.journeyDistanceAccountedFor = journeyDistanceAccountedFor;
        this.journeyDurationAccountedFor = journeyDurationAccountedFor;
    }

    /**
     * Getter for the fare amount
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Getter for the journey distance accounted for
     * @return the journey distance accounted for
     */
    public BigDecimal getJourneyDistanceAccountedFor() {
        return journeyDistanceAccountedFor;
    }

    /**
     * Getter for the journey duration accounted for
     * @return the journey duration accounted for
     */
    public BigDecimal getJourneyDurationAccountedFor() {
        return journeyDurationAccountedFor;
    }

}