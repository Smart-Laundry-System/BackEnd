package com.SmartLaundry.laundry.Entity.Complain;


import com.SmartLaundry.laundry.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    @Column(name = "com_id")
    private Long id;

    @Column(nullable = false)
    private String laundryEmail;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long customerId;

    @Override
    public String toString() {
        return "Complain{" +
                "id=" + id +
                ", laundry_email='" + laundryEmail + '\'' +
                ", customer_email='" + customerEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", order_id=" + orderId +
                ", customer_id=" + customerId +
                '}';
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaundryEmail() {
        return laundryEmail;
    }

    public void setLaundryEmail(String laundryEmail) {
        this.laundryEmail = laundryEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

