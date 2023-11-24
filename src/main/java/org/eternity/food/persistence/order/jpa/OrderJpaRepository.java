package org.eternity.food.persistence.order.jpa;

import org.eternity.food.domain.order.Order;
import org.eternity.food.domain.order.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, OrderId> {
}
