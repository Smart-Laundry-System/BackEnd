package com.SmartLaundry.laundry.Repository.OrderRepository;

import com.SmartLaundry.laundry.Entity.Order.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByLaundryEmailIgnoreCaseOrderByIdDesc(String laundryEmail);

    // (optional) If you want user-specific retrieval too
    List<CustomerOrder> findByCustomerEmailIgnoreCaseOrderByIdDesc(String customerEmail);
}
