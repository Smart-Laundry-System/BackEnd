package com.SmartLaundry.laundry.Entity.Dto.User;

import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String name;
    private String password;
    private UserRole role;
    private String phone;
    private String phone2;
    private String address;
}
