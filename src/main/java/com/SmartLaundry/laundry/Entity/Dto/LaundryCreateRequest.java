package com.SmartLaundry.laundry.Entity.Dto;

import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import java.util.List;

public class LaundryCreateRequest {
    private String email;
    private String name;
    private String password;
    private UserRole role;      // expect LAUNDRY
    private String phone;
    private String phone2;      // optional
    private String address;

    private List<ServiceCreateDto> services; // <- lightweight DTO (see below)
    private List<String> availableItems;
    private String otherItems;
    private String laundryImg;
    private String about;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ServiceCreateDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceCreateDto> services) {
        this.services = services;
    }

    public List<String> getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(List<String> availableItems) {
        this.availableItems = availableItems;
    }

    public String getOtherItems() {
        return otherItems;
    }

    public void setOtherItems(String otherItems) {
        this.otherItems = otherItems;
    }

    public String getLaundryImg() {
        return laundryImg;
    }

    public void setLaundryImg(String laundryImg) {
        this.laundryImg = laundryImg;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "LaundryCreateRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", phone='" + phone + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", address='" + address + '\'' +
                ", services=" + services +
                ", availableItems=" + availableItems +
                ", otherItems='" + otherItems + '\'' +
                ", laundryImg='" + laundryImg + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}