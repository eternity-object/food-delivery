package org.eternity.food.persistence.shop;

import org.eternity.base.jpa.BaseRepository;
import org.eternity.food.domain.shop.Menu;
import org.eternity.food.domain.shop.MenuId;
import org.eternity.food.domain.shop.MenuRepository;
import org.eternity.food.domain.shop.ShopId;
import org.eternity.food.persistence.shop.jpa.MenuJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultMenuRepository extends BaseRepository<Menu, MenuId, MenuJpaRepository> implements MenuRepository {
    public DefaultMenuRepository(MenuJpaRepository repository) {
        super(repository);
    }

    @Override
    public List<Menu> findOpenMenusIn(ShopId shopId) {
        return repository.findByShopIdAndOpenIsTrue(shopId);
    }
}
