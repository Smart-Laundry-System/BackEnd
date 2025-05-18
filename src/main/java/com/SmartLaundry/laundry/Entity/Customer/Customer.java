package com.SmartLaundry.laundry.Entity.Customer;

import com.SmartLaundry.laundry.Entity.User.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;

public class Customer extends User {
    @Email
    @Column(unique = true, nullable = false)
    private String email;
}
