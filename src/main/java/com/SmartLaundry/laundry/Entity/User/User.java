//package com.SmartLaundry.laundry.Entity.User;
//
//import com.SmartLaundry.laundry.Entity.Complain.Complain;
//import com.SmartLaundry.laundry.Entity.Roles.UserRole;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Entity
//@Table(name = "users")
////@Getter
////@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
//public class User{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_id")
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserRole role;
//
//    @Column(nullable = false)
//    private String phone;
//
////    @Column(nullable = true)
//    private String phone_2;
//
//    @Column(nullable = false)
//    private String address;
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", email='" + email + '\'' +
//                ", name='" + name + '\'' +
//                ", password='" + password + '\'' +
//                ", role=" + role +
//                ", phone='" + phone + '\'' +
//                ", phone_2='" + phone_2 + '\'' +
//                ", address='" + address + '\'' +
//                ", complain=" + complain +
//                '}';
//    }
//
//    public String getPhone_2() {
//        return phone_2;
//    }
//
//    public void setPhone_2(String phone_2) {
//        this.phone_2 = phone_2;
//    }
//
//    public UserRole getRole() {
//        return role;
//    }
//
//    public void setRole(UserRole role) {
//        this.role = role;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @OneToMany
//    @JoinColumn(name = "com_id")
//    private List<Complain> complain;
//}
//


package com.SmartLaundry.laundry.Entity.User;

import com.SmartLaundry.laundry.Entity.Complain.Complain;
import com.SmartLaundry.laundry.Entity.Notification.Notifications;
import com.SmartLaundry.laundry.Entity.Order.CustomerOrder;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String phone;

    private String phone_2;

    @Column(nullable = false)
    private String address;

    // Link table from the "user" side (inverse)
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UserLaundry> userLaundries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserLaundry> userLaundries = new java.util.ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<CustomerOrder> orders = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Complain> complain = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Notifications> notifications = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", phone='" + phone + '\'' +
                ", phone_2='" + phone_2 + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhone_2() { return phone_2; }
    public void setPhone_2(String phone_2) { this.phone_2 = phone_2; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<UserLaundry> getUserLaundries() {
        return userLaundries;
    }

    public void setUserLaundries(List<UserLaundry> userLaundries) {
        this.userLaundries = userLaundries;
    }

    public List<Complain> getComplain() { return complain; }
    public void setComplain(List<Complain> complain) { this.complain = complain; }

}
