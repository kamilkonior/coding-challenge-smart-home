package home.smart.domain.model.value;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price implements Comparable<Price> {
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final int SCALE = 2;

    private final BigDecimal value;

    private Price(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    public static Price of(BigDecimal value) {
        return new Price(value);
    }

    @Override
    public int compareTo(Price that) {
        return this.value.compareTo(that.value);
    }
}
