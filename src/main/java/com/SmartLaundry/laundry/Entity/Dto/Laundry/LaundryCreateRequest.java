package com.SmartLaundry.laundry.Entity.Dto.Laundry;

import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LaundryCreateRequest {
    // --- User fields (from your JSON) ---
    private String email;
    private String name;          // used for User.name and Laundry.name
    private String password;
    private UserRole role;        // LAUNDRY
    private String phone;

    @JsonProperty("phone_2")     // maps JSON key "phone_2" -> this field
    private String phone2;

    private String address;

    // --- Laundry fields (from your JSON) ---
    private List<Services> services;
    private List<String> availableItems;
    private String otherItems;
    private String laundryImg;
    private String about;

    // --- getters/setters ---
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

    // NOTE: Java-friendly name
    public String getPhone2() { return phone2; }
    public void setPhone2(String phone2) { this.phone2 = phone2; }

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
}