package com.eCart.eCart.services.customer.cart;

import com.eCart.eCart.dto.AddProductToCartDto;
import com.eCart.eCart.dto.OrderDto;
import com.eCart.eCart.dto.PlaceOrderDto;
import com.eCart.eCart.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductToCartDto addProductToCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto increaseProductQuantity(AddProductToCartDto addProductToCartDto);
    OrderDto decreaseProductQuantity(AddProductToCartDto addProductToCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getMyPlacedOrders(Long userId);
}
