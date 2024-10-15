package com.eCart.eCart.services.admin.adminOrder;

import com.eCart.eCart.dto.OrderDto;
import com.eCart.eCart.entity.Order;
import com.eCart.eCart.enums.OrderStatus;
import com.eCart.eCart.repository.OrderRepository;
import io.jsonwebtoken.security.Jwks;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrders(){
        List<Order>orderList=orderRepository.findAllByOrderStatusIn(
                List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered)
        );
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }
    public OrderDto changeOrderStatus(Long orderId,String status){

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isPresent()){

            Order order = optionalOrder.get();

            if(Objects.equals(status,"Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            }else{
                order.setOrderStatus(OrderStatus.Delivered);
            }

            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }
}
