package com.SmartLaundry.laundry.Entity.Laundry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomer {
    @Email
    @Column(nullable = false)
    private String customerEmail;

    @Email
    @Column(nullable = false)
    private String laundryEmail;

    @Override
    public String toString() {
        return "UpdateCustomer{" +
                "customerEmail='" + customerEmail + '\'' +
                ", laundryEmail='" + laundryEmail + '\'' +
                '}';
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getLaundryEmail() {
        return laundryEmail;
    }

    public void setLaundryEmail(String laundryEmail) {
        this.laundryEmail = laundryEmail;
    }
}
