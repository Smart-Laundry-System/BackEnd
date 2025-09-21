package com.SmartLaundry.laundry.Entity.Dto.Laundry;

import com.SmartLaundry.laundry.Entity.Dto.User.CustomerLite;
import com.SmartLaundry.laundry.Entity.Dto.Services.ServiceLite;

import java.util.List;

public record LaundryDTO(
        Long id, String name, String phone, String address,Double rating, List<ServiceLite> services, List<String> availableItems, List<String> otherItems, String laundryImg,
        List<CustomerLite> customers
) {}
