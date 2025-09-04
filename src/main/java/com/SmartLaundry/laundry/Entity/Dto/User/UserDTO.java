package com.SmartLaundry.laundry.Entity.Dto.User;

import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryLite;

import java.util.List;

public record UserDTO(
        Long id, String email, String name, String role, String phone, String address,
        List<LaundryLite> laundries
) {}
