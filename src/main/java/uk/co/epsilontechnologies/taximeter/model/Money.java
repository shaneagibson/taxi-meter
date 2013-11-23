package uk.co.epsilontechnologies.taximeter.model;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;

    public Money(final String amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money(final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(final Money fare) {
        return new Money(this.amount.add(fare.getAmount()));
    }

    public boolean greaterThanOrEqualTo(final Money money) {
        return this.getAmount().compareTo(money.getAmount()) >= 0;
    }
}
