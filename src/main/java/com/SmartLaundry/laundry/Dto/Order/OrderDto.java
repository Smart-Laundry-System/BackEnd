package com.SmartLaundry.laundry.Dto.Order;

import com.SmartLaundry.laundry.Entity.Order.OrderStatus;
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
        OrderStatus status,
        String customerName,
        String customerPhone
) {}