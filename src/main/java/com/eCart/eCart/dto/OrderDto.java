package com.eCart.eCart.dto;

import com.eCart.eCart.entity.CartItems;
import com.eCart.eCart.entity.User;
import com.eCart.eCart.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;
    private String orderDescription;
    private Date date;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private UUID trackingId;
    private String userName;
    private List<CartItemsDto> cartItems;
}
