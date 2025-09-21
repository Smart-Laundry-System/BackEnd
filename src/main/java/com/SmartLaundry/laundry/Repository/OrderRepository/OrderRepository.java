package com.SmartLaundry.laundry.Repository.OrderRepository;

import com.SmartLaundry.laundry.Entity.Order.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByLaundry_Owner_EmailIgnoreCaseOrderByIdDesc(String ownerEmail);

    // by laundry id
    List<CustomerOrder> findByLaundry_IdOrderByIdDesc(Long laundryId);

    // by customer email
    List<CustomerOrder> findByCustomerEmailIgnoreCaseOrderByIdDesc(String customerEmail);}
