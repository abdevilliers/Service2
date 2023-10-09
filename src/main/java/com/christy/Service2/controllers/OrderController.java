package com.christy.Service2.controllers;


import com.christy.Service2.models.BookDto;
import com.christy.Service2.models.Order;
import com.christy.Service2.models.OrderStatus;
import com.christy.Service2.repositiories.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-service")
public class OrderController {


    private RestTemplate restTemplate;
    private OrderRepository orderRepository;
    public OrderController(RestTemplate restTemplate,OrderRepository orderRepository){
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public List<Order> getOrders(){
       return this.orderRepository.findAll();
    }


    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable Long orderId){
        Optional<Order> returnedOrder = this.orderRepository.findById(orderId);
        if(returnedOrder.isEmpty()){
            return null;
        }
        return returnedOrder.get();
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        Long bookId = order.getBookId();
        ResponseEntity<BookDto> returnedBook = this.restTemplate.getForEntity(
                String.format("http://serviceone/book-service/books/%s",Long.toString(bookId)),
                        BookDto.class
        );
        if(returnedBook.getBody() == null){
            return "book not available for purchase.Order creation failed!!";
        }
        order.setOrderStatus(OrderStatus.NEW);
        this.orderRepository.save(order);
        return "order placed succesfully!!";

    }

    @DeleteMapping("/orders/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        Optional<Order> returnedOrder = this.orderRepository.findById(orderId);
        if(returnedOrder.isEmpty()){
            return "order not present in db!!";
        }
        this.orderRepository.delete(returnedOrder.get());
        return "Order deleted successfully!!";

    }
}
