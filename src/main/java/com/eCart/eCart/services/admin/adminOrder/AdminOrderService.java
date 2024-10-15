package com.eCart.eCart.services.admin.adminOrder;

import com.eCart.eCart.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long orderId,String status);
}
