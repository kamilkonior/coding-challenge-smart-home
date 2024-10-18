package home.smart.domain.model.value;

import java.math.BigDecimal;

public record Price(BigDecimal value) implements Comparable<Price> {

    public Price {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    @Override
    public int compareTo(Price that) {
        return this.value.compareTo(that.value);
    }
}
