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
    private Long laundryId;

    @Override
    public String toString() {
        return "UpdateCustomer{" +
                "customerEmail='" + customerEmail + '\'' +
                ", laundryEmail='" + laundryId + '\'' +
                '}';
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Long laundryId) {
        this.laundryId = laundryId;
    }
}
