package uk.co.epsilontechnologies.taximeter.model;

import java.math.BigDecimal;

public class Distance {

    private final BigDecimal meters;

    public Distance(final BigDecimal meters) {
        this.meters = meters;
    }

    public Distance(final String meters) {
        this.meters = new BigDecimal(meters);
    }

    public BigDecimal getMeters() {
        return meters;
    }

    public Distance add(final Distance distance) {
        return new Distance(this.meters.add(distance.getMeters()));
    }

    public Distance subtract(final Distance distance) {
        return new Distance(this.meters.subtract(distance.getMeters()));
    }

    public boolean lessThan(final Distance distance) {
        return this.getMeters().compareTo(distance.getMeters()) < 0;
    }

    public boolean lessThanOrEqualTo(Distance distance) {
        return this.getMeters().compareTo(distance.getMeters()) <= 0;
    }
}
