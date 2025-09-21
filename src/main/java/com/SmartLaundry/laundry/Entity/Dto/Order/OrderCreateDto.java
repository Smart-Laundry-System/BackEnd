package com.SmartLaundry.laundry.Entity.Dto.Order;

import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

public class OrderCreateDto {
    private Long id;
    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private List<Long> serviceIds = new ArrayList<>();

    @Override
    public String toString() {
        return "OrderCreateDto{" +
                "id=" + id +
                ", customerEmail='" + customerEmail + '\'' +
                ", serviceIds=" + serviceIds +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }
}
