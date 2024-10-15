package com.eCart.eCart.services.customer;

import com.eCart.eCart.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProductByTitle(String title);
}
