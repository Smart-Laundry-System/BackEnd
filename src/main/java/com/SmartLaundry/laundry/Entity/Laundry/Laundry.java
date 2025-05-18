package com.SmartLaundry.laundry.Entity.Laundry;

import com.SmartLaundry.laundry.Entity.User.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;

public class Laundry extends User {
    @Email
    @Column(unique = true, nullable = false)
    private String email;
}
