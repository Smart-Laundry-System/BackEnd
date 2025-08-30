package com.SmartLaundry.laundry.Entity.Dto;

import java.util.List;

public record UserDTO(
        Long id, String email, String name, String role, String phone, String address,
        List<LaundryLite> laundries
) {}
