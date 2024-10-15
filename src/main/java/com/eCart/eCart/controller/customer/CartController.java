package com.eCart.eCart.controller.customer;

import com.eCart.eCart.dto.AddProductToCartDto;
import com.eCart.eCart.dto.OrderDto;
import com.eCart.eCart.dto.PlaceOrderDto;
import com.eCart.eCart.services.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?>addProductToCart(@RequestBody AddProductToCartDto addProductToCartDto){
        System.out.println("Add to cart (cartController) : " + addProductToCartDto);
        return cartService.addProductToCart(addProductToCartDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?>getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto=cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto>increaseProductQuantity(@RequestBody AddProductToCartDto addProductToCartDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.increaseProductQuantity(addProductToCartDto));
    }
    @PostMapping("/deduction")
    public ResponseEntity<OrderDto>decreaseProductQuantity(@RequestBody AddProductToCartDto addProductToCartDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.decreaseProductQuantity(addProductToCartDto));
    }
    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto>placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.placeOrder(placeOrderDto));
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderDto>>getMyPlacedOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
    }
}
