package org.eternity.food.domain.shop;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.eternity.base.domain.AggregateRoot;
import org.eternity.food.domain.generic.money.Money;
import org.eternity.food.domain.shop.ShopId.ShopIdJavaType;
import org.hibernate.annotations.JavaType;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Shop extends AggregateRoot<Shop, ShopId> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JavaType(ShopIdJavaType.class)
    private ShopId id;

    @Version
    private Long version;

    @Column(name="NAME")
    private String name;

    @Column(name="MIN_ORDER_AMOUNT")
    private Money minOrderPrice;

    @Embedded
    private OperatingHours operatingHours;

    public Shop(String name, boolean open, Money minOrderPrice, OperatingHours operatingHours) {
        this(null, name, open, minOrderPrice, operatingHours);
    }

    @Builder
    public Shop(ShopId id, String name, boolean open, Money minOrderPrice, OperatingHours operatingHours) {
        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.operatingHours = operatingHours;
    }

    Shop() {
    }

    public boolean isOpen() {
        return operatingHours.isOpen(LocalDateTime.now());
    }
}
