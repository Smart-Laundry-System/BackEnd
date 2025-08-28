package com.SmartLaundry.laundry.Entity.Laundry;
////
////import com.SmartLaundry.laundry.Entity.User.User;
////import jakarta.persistence.*;
////import lombok.AllArgsConstructor;
////import lombok.NoArgsConstructor;
////
////import java.util.List;
////
////@Entity
////@Table(name = "laundry")
////@AllArgsConstructor
////@NoArgsConstructor
////public class Laundry extends User {
////    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
////    @Column(name = "laundry_id")
////    private Long id;
////
//////    @ElementCollection
//////    @CollectionTable(name = "laundry_services", joinColumns = @JoinColumn(name = "laundry_id"))
//////    @Column(name = "service")
//////    private List<String> services;
////
////    @ElementCollection
////    @CollectionTable(name = "laundry_services", joinColumns = @JoinColumn(name = "laundry_id"))
////    private List<Services> services;
////
////
////    @ElementCollection
////    @CollectionTable(name = "laundry_available_items", joinColumns = @JoinColumn(name = "laundry_id"))
////    @Column(name = "item")
////    private List<String> availableItems;
////
////    @Column(nullable = false)
////    private String otherItems;
////
////    @Column(nullable = false)
////    private String laundryImg;
////
////    @Column(nullable = false)
////    private String about;
////
////    @Override
////    public Long getId() {
////        return id;
////    }
////
////    @Override
////    public void setId(Long id) {
////        this.id = id;
////    }
////
////    @Override
////    public String toString() {
////        return "Laundry{" +
////                "id=" + id +
////                ", services=" + services +
////                ", availableItems=" + availableItems +
////                ", otherItems='" + otherItems + '\'' +
////                ", laundryImg='" + laundryImg + '\'' +
////                ", about='" + about + '\'' +
////                '}';
////    }
////
////    public List<Services> getServices() {
////        return services;
////    }
////
////    public void setServices(List<Services> services) {
////        this.services = services;
////    }
////
////    public List<String> getAvailableItems() {
////        return availableItems;
////    }
////
////    public void setAvailableItems(List<String> availableItems) {
////        this.availableItems = availableItems;
////    }
////
////    public String getOtherItems() {
////        return otherItems;
////    }
////
////    public void setOtherItems(String otherItems) {
////        this.otherItems = otherItems;
////    }
////
////    public String getLaundryImg() {
////        return laundryImg;
////    }
////
////    public void setLaundryImg(String laundryImg) {
////        this.laundryImg = laundryImg;
////    }
////
////    public String getAbout() {
////        return about;
////    }
////
////    public void setAbout(String about) {
////        this.about = about;
////    }
////}
//
//package com.SmartLaundry.laundry.Entity.Laundry;
//
//import com.SmartLaundry.laundry.Entity.User.User;
//import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "laundry")
//@AllArgsConstructor
//@NoArgsConstructor
//public class Laundry {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "laundry_id")
//    private Long id;
//
////    @ElementCollection
////    @CollectionTable(name = "laundry_services", joinColumns = @JoinColumn(name = "laundry_id"))
////    @Column(name = "service")
////    private List<String> services;
//
//    @ElementCollection
//    @CollectionTable(name = "laundry_services", joinColumns = @JoinColumn(name = "laundry_id"))
//    private List<Services> services;
//
//
//    @ElementCollection
//    @CollectionTable(name = "laundry_available_items", joinColumns = @JoinColumn(name = "laundry_id"))
//    @Column(name = "item")
//    private List<String> availableItems;
//
//    @Column(nullable = false)
//    private String otherItems;
//
//    @Column(nullable = false)
//    private String laundryImg;
//
//    @Column(nullable = false)
//    private String about;
//
//    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserLaundry> userLaundries = new java.util.ArrayList<>();
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Override
//    public String toString() {
//        return "Laundry{" +
//                "id=" + id +
//                ", services=" + services +
//                ", availableItems=" + availableItems +
//                ", otherItems='" + otherItems + '\'' +
//                ", laundryImg='" + laundryImg + '\'' +
//                ", about='" + about + '\'' +
//                ", userLaundries=" + userLaundries +
//                '}';
//    }
//
//    public List<UserLaundry> getUserLaundries() {
//        return userLaundries;
//    }
//
//    public void setUserLaundries(List<UserLaundry> userLaundries) {
//        this.userLaundries = userLaundries;
//    }
//
//    public List<Services> getServices() {
//        return services;
//    }
//
//    public void setServices(List<Services> services) {
//        this.services = services;
//    }
//
//    public List<String> getAvailableItems() {
//        return availableItems;
//    }
//
//    public void setAvailableItems(List<String> availableItems) {
//        this.availableItems = availableItems;
//    }
//
//    public String getOtherItems() {
//        return otherItems;
//    }
//
//    public void setOtherItems(String otherItems) {
//        this.otherItems = otherItems;
//    }
//
//    public String getLaundryImg() {
//        return laundryImg;
//    }
//
//    public void setLaundryImg(String laundryImg) {
//        this.laundryImg = laundryImg;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//}

import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "laundry")
@AllArgsConstructor
@NoArgsConstructor
public class Laundry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "laundry_id")
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String phone;
    @Column(nullable = false) private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "laundry_services", joinColumns = @JoinColumn(name = "laundry_id"))
    @Column(nullable = false)
    private List<Services> services;

    @ElementCollection
    @CollectionTable(name = "laundry_available_items", joinColumns = @JoinColumn(name = "laundry_id"))
    @Column(name = "item")
    private List<String> availableItems;

    @Column(nullable = false) private String otherItems;
    @Column(nullable = false) private String laundryImg;
    @Column(nullable = false) private String about;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLaundry> userLaundries = new java.util.ArrayList<>();

    // getters/setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<Services> getServices() { return services; }
    public void setServices(List<Services> services) { this.services = services; }
    public List<String> getAvailableItems() { return availableItems; }
    public void setAvailableItems(List<String> availableItems) { this.availableItems = availableItems; }
    public String getOtherItems() { return otherItems; }
    public void setOtherItems(String otherItems) { this.otherItems = otherItems; }
    public String getLaundryImg() { return laundryImg; }
    public void setLaundryImg(String laundryImg) { this.laundryImg = laundryImg; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public List<UserLaundry> getUserLaundries() { return userLaundries; }
    public void setUserLaundries(List<UserLaundry> userLaundries) { this.userLaundries = userLaundries; }
}