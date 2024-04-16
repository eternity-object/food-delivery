package org.eternity.food.domain.shop;

import jakarta.persistence.*;
import org.eternity.base.domain.ValueObject;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OperatingHours extends ValueObject<OperatingHours> {
    private LocalTime openTime;
    private LocalTime closeTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="SHOP_DAY_OFF", joinColumns = @JoinColumn(name="SHOP_ID"))
    @Column(name="DAY_OF_WEEK")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> dayOffs = new HashSet<>();

    public OperatingHours(LocalTime openTime, LocalTime closeTime, DayOfWeek ... dayOffs) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.dayOffs = new HashSet<>(Arrays.asList(dayOffs));
    }

    OperatingHours() {
    }

    public boolean isOpen(LocalDateTime time) {
        return time.toLocalTime().isAfter(openTime) &&
                time.toLocalTime().isBefore(closeTime) &&
                !dayOffs.contains(time.getDayOfWeek());
    }
}
