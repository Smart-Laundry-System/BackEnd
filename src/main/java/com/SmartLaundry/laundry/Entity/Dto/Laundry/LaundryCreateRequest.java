package com.SmartLaundry.laundry.Entity.Dto.Laundry;

import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalTime;
import java.util.List;

public class LaundryCreateRequest {
    // --- User fields (from your JSON) ---

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;          // used for User.name and Laundry.name
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[\\[\\]`~!@#$%^&*(),.?+=_\"':;{}|<>-]).+$",
            message = "Password must contain at least one special character"
    )
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;        // LAUNDRY

    @Column(nullable = false)
    private String phone;

    @JsonProperty("phone_2")     // maps JSON key "phone_2" -> this field
    @Column(nullable = false)
    private String phone2;


    @Column(nullable = false)
    private String address;

    // --- Laundry fields (from your JSON) ---
    private List<Services> services;
    private List<String> availableItems;

    @Column(nullable = false)
    private List<String> otherItems;

    @Column(nullable = false)
    private String laundryImg;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "about", nullable = false)
    @Size(min = 60, max = 1200, message = "About must be between 60 and 1200 characters.")
    private String about;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime closeTime;

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

    public List<String> getOtherItems() { return otherItems; }
    public void setOtherItems(List<String> otherItems) { this.otherItems = otherItems; }

    public String getLaundryImg() { return laundryImg; }
    public void setLaundryImg(String laundryImg) { this.laundryImg = laundryImg; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
}