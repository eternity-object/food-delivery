package org.eternity.food.persistence.shop;

import org.eternity.base.jpa.BaseRepository;
import org.eternity.food.domain.shop.Shop;
import org.eternity.food.domain.shop.ShopId;
import org.eternity.food.domain.shop.ShopRepository;
import org.eternity.food.persistence.shop.jpa.ShopJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultShopRepository extends BaseRepository<Shop, ShopId, ShopJpaRepository> implements ShopRepository {
    public DefaultShopRepository(ShopJpaRepository repository) {
        super(repository);
    }
}
