package com.SmartLaundry.laundry.Entity.Dto;

import java.util.List;

public record LaundryDTO(
        Long id, String name, String phone, String address,
        List<CustomerLite> customers
) {}
