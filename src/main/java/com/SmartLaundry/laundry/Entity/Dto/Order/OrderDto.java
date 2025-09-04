package com.SmartLaundry.laundry.Entity.Dto.Order;

import com.SmartLaundry.laundry.Entity.Order.OrderStatus;

import java.util.Date;
import java.util.List;

public record OrderDto(
        Long id,
        List<Long> serviceIds,
        String customerEmail,
        String laundryEmail,
        String laundryName,
        String customerAddress,
        String laundryAddress,
        double totPrice,
        String laundryImg,
        Date estimatedDate,
        OrderStatus status,
        String customerName,
        String customerPhone
) {}