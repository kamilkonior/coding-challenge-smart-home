package home.smart.domain.model.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Accessors(fluent = true)
@Getter
@ToString
@EqualsAndHashCode
public class Price implements Comparable<Price> {
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final int SCALE = 2;

    public static final Price ZERO = Price.of(BigDecimal.ZERO);

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

    public static Price of(long value) {
        return new Price(BigDecimal.valueOf(value));
    }

    public Price add(Price price) {
        return new Price(value.add(price.value));
    }

    @Override
    public int compareTo(Price that) {
        return this.value.compareTo(that.value);
    }
}
