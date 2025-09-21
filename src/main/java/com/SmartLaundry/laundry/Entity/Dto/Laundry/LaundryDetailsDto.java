package com.SmartLaundry.laundry.Entity.Dto.Laundry;

import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.time.LocalTime;
import java.util.List;

public class LaundryDetailsDto {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String laundryImg;
    private String about;


    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private Double rating;
    private List<Services> services;
    private List<String> availableItems;
    private List<String> otherItems;

    private List<UserSummary> users; // aggregated user details for the UI

    public static class UserSummary {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String relationRole; // OWNER/EMPLOYEE/CUSTOMER (string for simple JSON)

        public UserSummary() {}
        public UserSummary(Long id, String name, String email, String phone, String address, String relationRole) {
            this.id = id; this.name = name; this.email = email; this.phone = phone; this.address = address; this.relationRole = relationRole;
        }
        // getters/setters

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getRelationRole() { return relationRole; }
        public void setRelationRole(String relationRole) { this.relationRole = relationRole; }
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    // getters/setters

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getLaundryImg() { return laundryImg; }
    public void setLaundryImg(String laundryImg) { this.laundryImg = laundryImg; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
    public List<Services> getServices() { return services; }
    public void setServices(List<Services> services) { this.services = services; }
    public List<String> getAvailableItems() { return availableItems; }
    public void setAvailableItems(List<String> availableItems) { this.availableItems = availableItems; }
    public List<String> getOtherItems() { return otherItems; }
    public void setOtherItems(List<String> otherItems) { this.otherItems = otherItems; }
    public List<UserSummary> getUsers() { return users; }
    public void setUsers(List<UserSummary> users) { this.users = users; }
}