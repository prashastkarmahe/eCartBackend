package com.eCart.eCart.entity;

import com.eCart.eCart.dto.OrderDto;
import com.eCart.eCart.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private UUID trackingId;

    @ManyToOne(cascade = CascadeType.MERGE) //One user can have many orders
    @JoinColumn(name="user_id",referencedColumnName = "id") //User.user_id <-> Order.id(join)
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItems> cartItems;

    public OrderDto getOrderDto(){
        OrderDto orderDto=new OrderDto();

        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setDate(date);
        orderDto.setTrackingId(trackingId);
        orderDto.setUserName(user.getName());

        return orderDto;
    }
}
