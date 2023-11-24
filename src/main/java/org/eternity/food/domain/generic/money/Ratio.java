package org.eternity.food.domain.generic.money;

import org.eternity.base.domain.ValueObject;

public class Ratio extends ValueObject<Ratio> {
    private double rate;

    public static Ratio valueOf(double rate) {
        return new Ratio(rate);
    }

    Ratio(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("비율은 0보다 커야합니다.");
        }
        this.rate = rate;
    }

    Ratio() {
    }

    public Money of(Money price) {
        return price.times(rate);
    }

    public double getRate() {
        return rate;
    }
}
