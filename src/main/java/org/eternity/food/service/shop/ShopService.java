package org.eternity.food.service.shop;

import org.eternity.food.domain.shop.Shop;
import org.eternity.food.domain.shop.ShopId;
import org.eternity.food.persistence.shop.jpa.ShopJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {
    private ShopJpaRepository shopJpaRepository;

    public ShopService(ShopJpaRepository shopJpaRepository) {
        this.shopJpaRepository = shopJpaRepository;
    }

    @Transactional(readOnly = true)
    public MenuBoard getMenuBoard(Long shopId) {
        Shop shop = shopJpaRepository.findById(new ShopId(shopId)).orElseThrow(IllegalArgumentException::new);
        return new MenuBoard(shop);
    }
}
