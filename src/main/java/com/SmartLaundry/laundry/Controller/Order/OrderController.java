package com.SmartLaundry.laundry.Controller.Order;

import com.SmartLaundry.laundry.Entity.Dto.Order.OrderDto;
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

    @GetMapping("/retriveCustomerRelatedOrder")
    public ResponseEntity<?> retriveCustomerRelatedOrder(@RequestParam String email){
        ResponseEntity<?> body = service.retriveCustomerRelatedOrder(email);
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

    @PostMapping("/order/acceptNewDate")
    public ResponseEntity<?> acceptNewDate(@RequestParam Long orderID) {
        return ResponseEntity.ok(service.acceptProposedDate(orderID));
    }

    // customer taps "Reject the new date"
    @PostMapping("/order/rejectNewDate")
    public ResponseEntity<?> rejectNewDate(@RequestParam Long orderID) {
        return ResponseEntity.ok(service.rejectProposedDate(orderID));
    }

    @PutMapping("/order/updateEstimatedDate")
    public ResponseEntity<?> updateEstimatedDate(@RequestParam Long orderID,
                                                 @RequestParam String date) {
        return service.updateEstimatedDate(orderID, date);
    }

    @PutMapping("/order/requestEstimatedDate")
    public ResponseEntity<?> requestEstimatedDate(@RequestParam Long orderID,
                                                 @RequestParam String date) {
        return service.requestEstimatedDate(orderID, date);
    }

}
