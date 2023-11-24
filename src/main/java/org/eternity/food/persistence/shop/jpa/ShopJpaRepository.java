package org.eternity.food.persistence.shop.jpa;

import org.eternity.food.domain.shop.Shop;
import org.eternity.food.domain.shop.ShopId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopJpaRepository extends JpaRepository<Shop, ShopId> {
}
