package com.eCart.eCart.controller.admin;

import com.eCart.eCart.dto.ProductDto;
import com.eCart.eCart.entity.Product;
import com.eCart.eCart.services.admin.adminproduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final AdminProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto>addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1=adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>>getAllProducts(){
        List<ProductDto>productDtos=adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>>getAllProductByName(@PathVariable String name){
        List<ProductDto>productDtos=adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void>deleteProduct(@PathVariable Long productId){
        boolean deleted= adminProductService.deleteProduct(productId);
        if(deleted){
            //Status 204->item deleted successfully
            return ResponseEntity.noContent().build();
        }
        // Status 404 (not-found)
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{productId}")
    public  ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productDto=adminProductService.getProductById(productId);
        if(productDto!=null){
            return ResponseEntity.ok(productDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable Long productId,@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto updatedProduct=adminProductService.updateProduct(productId,productDto);
        if(updatedProduct!=null){
            return ResponseEntity.ok(updatedProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
