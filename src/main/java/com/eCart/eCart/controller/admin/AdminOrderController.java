package com.eCart.eCart.controller.admin;

import com.eCart.eCart.dto.OrderDto;
import com.eCart.eCart.services.admin.adminOrder.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>>getAllPlaceOrders(){
        return  ResponseEntity.ok(adminOrderService.getAllPlacedOrders());
    }

    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId,@PathVariable String status){
        OrderDto orderDto=adminOrderService.changeOrderStatus(orderId,status);

        if(orderDto == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
