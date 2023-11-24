package org.eternity;

import org.eternity.food.domain.cart.CartId;
import org.eternity.food.domain.shop.MenuId;
import org.eternity.food.domain.shop.OptionGroupId;
import org.eternity.food.domain.shop.ShopId;
import org.eternity.food.domain.user.UserId;
import org.eternity.food.service.cart.CartLineItemRequest;
import org.eternity.food.service.cart.CartLineItemRequest.CartOptionGroupRequest;
import org.eternity.food.service.cart.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class FoodApplication implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(FoodApplication.class);

    private CartService cartService;

    public FoodApplication(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void run(String... args) {
        CartLineItemRequest request = new CartLineItemRequest(
                new ShopId(1L),
                new CartId(1L),
                new MenuId(1L),
                2,
                new CartOptionGroupRequest(
                        new OptionGroupId(1L),
                        2,
                        Arrays.asList()));

        cartService.addCartLineItem(new UserId(1L), request);
    }

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(FoodApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }
}
