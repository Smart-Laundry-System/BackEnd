package com.SmartLaundry.laundry.Controller.Order;

import com.SmartLaundry.laundry.Dto.Order.OrderDto;
import com.SmartLaundry.laundry.Entity.Order.CustomerOrder;
import com.SmartLaundry.laundry.Entity.Order.OrderStatus;
import com.SmartLaundry.laundry.Service.Order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody CustomerOrder order){
        String message = service.registerOrder(order);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/retrieveUserOrder")
    public ResponseEntity<?> retrieveOrder(@RequestParam String email) {
        try {
            List<OrderDto> orderList = service.retriveUserOrders(email);

            return ResponseEntity.ok(orderList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error on retrieving orders");
        }
    }

    @GetMapping("/retriveLaundryRelatedOrder")
    public ResponseEntity<?> retriveLaundryRelatedOrder(@RequestParam String email){
        ResponseEntity<?> body = service.retriveLaundryRelatedOrder(email);
        return body;
    }

    @GetMapping("/retriveOrderById")
    public ResponseEntity<?> retriveOrderById(@RequestParam Long orderID){
        return service.retriveOrderById(orderID);
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam Long orderID, @RequestParam OrderStatus status){
        return service.updateStatus(orderID,status);
    }


}
