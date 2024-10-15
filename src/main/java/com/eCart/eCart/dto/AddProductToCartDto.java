package com.eCart.eCart.dto;

import lombok.Data;

@Data
public class AddProductToCartDto {
    private Long userId;
    private Long productId;
}
