package com.SmartLaundry.laundry.Entity.Dto.NotificationsCom;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NotificationRequest {
    private String laundryName;
    @NotBlank @Email private String laundryEmail;

    // If null/blank → send to ALL related customers
    private String customerEmail;

    @NotBlank private String subject;
    @NotBlank private String message;

    // getters & setters
    public String getLaundryName() { return laundryName; }
    public void setLaundryName(String laundryName) { this.laundryName = laundryName; }

    public String getLaundryEmail() { return laundryEmail; }
    public void setLaundryEmail(String laundryEmail) { this.laundryEmail = laundryEmail; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
