package com.eCart.eCart.services.admin.adminproduct;

import com.eCart.eCart.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {
    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductByName(String name);
    boolean deleteProduct(Long id);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(Long productId,ProductDto productDto) throws IOException;
}
