package com.eCart.eCart.services.customer.cart;

import com.eCart.eCart.dto.AddProductToCartDto;
import com.eCart.eCart.dto.CartItemsDto;
import com.eCart.eCart.dto.OrderDto;
import com.eCart.eCart.dto.PlaceOrderDto;
import com.eCart.eCart.entity.CartItems;
import com.eCart.eCart.entity.Order;
import com.eCart.eCart.entity.Product;
import com.eCart.eCart.entity.User;
import com.eCart.eCart.enums.OrderStatus;
import com.eCart.eCart.repository.CartItemsRepository;
import com.eCart.eCart.repository.OrderRepository;
import com.eCart.eCart.repository.ProductRepository;
import com.eCart.eCart.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?>addProductToCart(AddProductToCartDto addProductToCartDto){

        // System.out.println("Product ID: " + addProductToCartDto.getProductId());
        // System.out.println("User ID: " + addProductToCartDto.getUserId());

        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addProductToCartDto.getUserId(),OrderStatus.Pending);

        Optional<CartItems>optionalCartItems=cartItemsRepository
                .findByProductIdAndOrderIdAndUserId(addProductToCartDto.getProductId(),activeOrder.getId(),addProductToCartDto.getUserId());

        if(optionalCartItems.isPresent()){
            return(ResponseEntity.status(HttpStatus.CONFLICT)).body(null);
        }
        else{
            // If Item is not present in Cart,then creating a cart
            Optional<Product>optionalProduct=productRepository.findById(addProductToCartDto.getProductId());
            Optional<User>optionalUser=userRepository.findById(addProductToCartDto.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                //Creating new Cart
                CartItems cart=new CartItems();

                //Initializing the newly created cart
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                //Saving new cart
                CartItems updatedCart=cartItemsRepository.save(cart);

                //Updating the price
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());

                //Adding this cart in user's order
                activeOrder.getCartItems().add(cart);

                //Updating Order Table
                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }else{
                return(ResponseEntity.status(HttpStatus.NOT_FOUND)).body("Product or Product not found !!!");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.Pending);

        List<CartItemsDto>cartItemsDtoList=activeOrder.getCartItems().stream()
                .map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto=new OrderDto();
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setCartItems(cartItemsDtoList);

        return orderDto;
    }

    public OrderDto increaseProductQuantity(AddProductToCartDto addProductToCartDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addProductToCartDto.getUserId(),OrderStatus.Pending);

        Optional<Product>optionalProduct=productRepository.findById(addProductToCartDto.getUserId());

        Optional<CartItems>optionalCartItems=cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductToCartDto.getProductId(), activeOrder.getId(), addProductToCartDto.getUserId()
        );

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems=optionalCartItems.get();
            Product product=optionalProduct.get();

            cartItems.setQuantity(cartItems.getQuantity() + 1);
            cartItemsRepository.save(cartItems);

            recalculateTotalAmount(activeOrder);

            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }

        //This will represent bad request
        return null;
    }
    public OrderDto decreaseProductQuantity(AddProductToCartDto addProductToCartDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addProductToCartDto.getUserId(),OrderStatus.Pending);

        Optional<Product>optionalProduct=productRepository.findById(addProductToCartDto.getUserId());

        Optional<CartItems>optionalCartItems=cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductToCartDto.getProductId(), activeOrder.getId(), addProductToCartDto.getUserId()
        );

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems=optionalCartItems.get();
            Product product=optionalProduct.get();

            cartItems.setQuantity(cartItems.getQuantity() - 1);
            cartItemsRepository.save(cartItems);

            recalculateTotalAmount(activeOrder);

            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }

        //This will represent bad request
        return null;
    }
    private void recalculateTotalAmount(Order activeOrder) {
        long newTotalAmount = 0L;

        for (CartItems item : activeOrder.getCartItems()) {
            newTotalAmount += item.getQuantity() * item.getProduct().getPrice();
        }
        // Set the recalculated total amount to the order
        activeOrder.setTotalAmount(newTotalAmount);
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(),OrderStatus.Pending);
        Optional<User>optionalUser=userRepository.findById(placeOrderDto.getUserId());

        if(optionalUser.isPresent()){
            activeOrder.setOrderDescription(placeOrderDto.getDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setPayment(placeOrderDto.getPaymentMethod());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            //Creating a new active order for user as previous order has been placed
            Order order=new Order();
            order.setTotalAmount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId){

        List<OrderStatus>orderStatusList=List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered);

        return orderRepository.findAllByUserIdAndOrderStatusIn(userId,orderStatusList)
                .stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

}
