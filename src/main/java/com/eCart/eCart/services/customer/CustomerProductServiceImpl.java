package com.eCart.eCart.services.customer;

import com.eCart.eCart.dto.ProductDto;
import com.eCart.eCart.entity.Product;
import com.eCart.eCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{

    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts(){
        List<Product>products=productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> searchProductByTitle(String title){
        List<Product>products=productRepository.findAllByNameContaining(title);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
