package com.SmartLaundry.laundry.Service.Order;

import com.SmartLaundry.laundry.API.Mappers;
import com.SmartLaundry.laundry.Dto.Order.OrderDto;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.Order.CustomerOrder;
import com.SmartLaundry.laundry.Entity.Order.OrderStatus;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.OrderRepository.OrderRepository;
import com.SmartLaundry.laundry.Repository.Services.ServicesRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final Mappers mappers;

    private final ServicesRepository servicesRepository;
    private final OrderRepository repository;

    private final UserRepository userRepository;
    private final LaundryRepository laundryRepository;

    public OrderService(OrderRepository repository, UserRepository userRepository, LaundryRepository laundryRepository, ServicesRepository servicesRepository, Mappers mappers) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.laundryRepository = laundryRepository;
        this.servicesRepository = servicesRepository;
        this.mappers = mappers;
    }

//    public String registerOrder(CustomerOrder order) {
//        if (order.getLaundryEmail() == null || order.getLaundryEmail().isBlank()) {
//            return "Order error: missing laundry owner email.";
//        }
//        if (order.getServiceIds() == null || order.getServiceIds().isEmpty()) {
//            return "Order error: no services selected.";
//        }
//
//        // 1) find Laundry by OWNER email
//        Laundry laundry = laundryRepository.findFirstByOwner_Email(order.getLaundryEmail())
//                .orElse(null);
//        if (laundry == null) return "Order error: laundry not found for owner email.";
//
//        User customer = userRepository.findByEmail(order.getCustomerEmail()).orElse(null);
//        if (customer == null) {
//            return "Order error: customer not found for customer email.";
//        }
//
//        order.setLaundryName(laundry.getName());
//        // 2) copy display info (image) from laundry
//        order.setLaundryImg(laundry.getLaundryImg());
//
//        if (order.getCustomerAddress() == null || order.getCustomerAddress().isBlank()) {
//            order.setCustomerAddress(customer.getAddress());
//        }
//
//        order.setLaundryAddress(laundry.getAddress());
//        // 3) fetch services that belong to THIS laundry and compute total
//        List<Services> services = servicesRepository.findAllByIdInAndLaundry_Id(
//                order.getServiceIds(), laundry.getId()
//        );
//
//        // Safety: ensure all requested IDs actually belong to this laundry
//        if (services.size() != order.getServiceIds().size()) {
//            return "Order error: one or more selected services do not belong to this laundry.";
//        }
//
//        double total = 0.0;
//        for (Services s : services) {
//            total += parsePrice(s.getPrice());
//        }
//        order.setTotPrice(total);
//
//        // 4) (optional) link the order to the customer User entity (if you want)
//        if (order.getCustomerEmail() != null && !order.getCustomerEmail().isBlank()) {
//            userRepository.findByEmail(order.getCustomerEmail()).ifPresent(order::setUsers);
//        }
//
//        // 5) persist
//        repository.save(order);
//        return "Order completed";
//    }

    @Transactional
    public String registerOrder(CustomerOrder order) {
        // basic validations
        if (order.getLaundryEmail() == null || order.getLaundryEmail().isBlank()) {
            return "Order error: missing laundry owner email.";
        }
        if (order.getServiceIds() == null || order.getServiceIds().isEmpty()) {
            return "Order error: no services selected.";
        }
        if (order.getCustomerEmail() == null || order.getCustomerEmail().isBlank()) {
            return "Order error: missing customer email.";
        }

        // 1) resolve Laundry by OWNER email
        Laundry laundry = laundryRepository.findFirstByOwner_Email(order.getLaundryEmail()).orElse(null);
        if (laundry == null) {
            return "Order error: laundry not found for owner email.";
        }

        // 2) resolve Customer User by email and copy address
        User customer = userRepository.findByEmail(order.getCustomerEmail()).orElse(null);
        if (customer == null) {
            return "Order error: customer not found for customer email.";
        }

        // Write-through convenience info
        order.setLaundryName(laundry.getName());
        order.setLaundryImg(laundry.getLaundryImg());
        order.setLaundryAddress(laundry.getAddress());

        // If request didn't already provide a custom address, use user's saved address
        if (order.getCustomerAddress() == null || order.getCustomerAddress().isBlank()) {
            order.setCustomerAddress(customer.getAddress());
        }

        // Also link the entity relation if you keep it
        order.setUsers(customer);

        // 3) fetch services that belong to THIS laundry, compute total
        List<Services> services = servicesRepository.findAllByIdInAndLaundry_Id(
                order.getServiceIds(), laundry.getId()
        );

        // Safety: ensure every requested ID belongs to this laundry
        if (services.size() != order.getServiceIds().size()) {
            return "Order error: one or more selected services do not belong to this laundry.";
        }

        double total = 0.0;
        for (Services s : services) {
            total += parsePrice(s.getPrice()); // you already have parsePrice(...)
        }
        order.setTotPrice(total);

        // 4) persist
        repository.save(order);
        return "Order completed";
    }

    /**
     * Your Services.price is a String. This tolerates values like "120.00", "LKR 120", "120LKR".
     */
    private static double parsePrice(String price) {
        if (price == null) return 0.0;
        String num = price.replaceAll("[^0-9.]", "");
        return num.isEmpty() ? 0.0 : Double.parseDouble(num);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> retriveUserOrders(String email) {
        return repository.findAll().stream()
                .map(mappers::toOrderDTO)
                .toList();
    }

    public ResponseEntity<?> retriveLaundryRelatedOrder(String email) {
        String target = (email == null ? "" : email.trim());
        if (target.isEmpty()) {
            return ResponseEntity.badRequest().body("Laundry (owner) email is required.");
        }

        var orders = repository
                .findByLaundryEmailIgnoreCaseOrderByIdDesc(target)
                .stream()
                .map(mappers::toOrderDTO)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @Transactional
    public ResponseEntity<?> retriveOrderById(Long orderID) {
        if (orderID == null) {
            return ResponseEntity.badRequest().body("orderID is required.");
        }

        // Find the order and map to DTO (not to ResponseEntity yet)
        var dtoOpt = repository.findById(orderID).map(mappers::toOrderDTO);

        if (dtoOpt.isPresent()) {
            // Return as a one-element array to match your frontend expectation: [{...}]
            // If you're on Java 8, use Collections.singletonList(...)
            return ResponseEntity.ok(java.util.List.of(dtoOpt.get()));
        } else {
            return ResponseEntity.status(404).body("No order found for id " + orderID);
        }
    }


    @Transactional
    public ResponseEntity<?> updateStatus(Long orderID, OrderStatus status) {
        if (orderID == null || status == null) {
            return ResponseEntity.badRequest().body("orderID and status are required.");
        }

        var opt = repository.findById(orderID);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("No order found for id " + orderID);
        }

        CustomerOrder order = opt.get();
        if (order.getStatus() != status) {
            order.setStatus(status);
            repository.save(order);
        }

        OrderDto dto = mappers.toOrderDTO(order);
        return ResponseEntity.ok(dto);
    }
}
