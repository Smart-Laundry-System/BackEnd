package com.SmartLaundry.laundry.Entity.Order;


import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ord_id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER) // or LAZY if you prefer
    @CollectionTable(
            name = "order_service_ids",
            joinColumns = @JoinColumn(name = "order_id") // join to this order
    )
    @Column(name = "service_id", nullable = false)
    private List<Long> serviceIds = new ArrayList<>();

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String laundryEmail;

    @Column
    private String laundryName;

    @Column
    private String customerAddress;

    @Column
    private String laundryAddress;

    @Column(nullable = true)
    private double totPrice;

    @Column
    private String laundryImg;

    @Column
    private Date estimatedDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3));

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status = OrderStatus.UNCONFIRMED;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", serviceIds=" + serviceIds +
                ", customerEmail='" + customerEmail + '\'' +
                ", laundryEmail='" + laundryEmail + '\'' +
                ", laundryName='" + laundryName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", laundryAddress='" + laundryAddress + '\'' +
                ", totPrice=" + totPrice +
                ", laundryImg='" + laundryImg + '\'' +
                ", estimatedDate=" + estimatedDate +
                ", status=" + status +
                '}';
    }

    public Date getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(Date estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getLaundryAddress() {
        return laundryAddress;
    }

    public void setLaundryAddress(String laundryAddress) {
        this.laundryAddress = laundryAddress;
    }

    public String getLaundryName() {
        return laundryName;
    }

    public void setLaundryName(String laundryName) {
        this.laundryName = laundryName;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getLaundryImg() {
        return laundryImg;
    }

    public void setLaundryImg(String laundryImg) {
        this.laundryImg = laundryImg;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
