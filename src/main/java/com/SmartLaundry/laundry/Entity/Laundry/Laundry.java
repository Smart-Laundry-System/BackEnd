package com.SmartLaundry.laundry.Entity.Laundry;

import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "laundry")
public class Laundry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    @Column(name = "laundry_id")
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String phone;
    @Column(nullable = false) private String address;

    @Column(name = "rating_avg")
    private Double rating = 0.0;

    @Column(name = "rating_count")
    private Long ratingCount = 0L;

    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Services> services = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "laundry_available_items", joinColumns = @JoinColumn(name = "laundry_id"))
    @Column(name = "item")
    private List<String> availableItems;

    @ElementCollection
    @Column(nullable = false)
    @CollectionTable(
            name = "laundry_other_items",
            joinColumns = @JoinColumn(name = "laundry_id")
    )
    private List<String> otherItems;
    @Column(nullable = false) private String laundryImg;
    @Column(nullable = false)
    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Size(min = 60, max = 1200, message = "About must be between 60 and 1200 characters.")
    private String about;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime closeTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

//    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UserLaundry> userLaundries = new LinkedHashSet<>();
    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserLaundry> userLaundries = new java.util.ArrayList<>();

    public void addService(Services s) {
        if (s == null) return;
        s.setId(null);           // ensure INSERT, not update
        s.setLaundry(this);      // ✅ critical: sets FK side, so laundry_id is NOT null
        this.services.add(s);
    }

    public void clearAndAddServices(List<Services> list) {
        this.services.clear();
        if (list != null) {
            for (Services s : list) addService(s);
        }
    }

    public List<UserLaundry> getUserLaundries() {
        return userLaundries;
    }

    public void setUserLaundries(List<UserLaundry> userLaundries) {
        this.userLaundries = userLaundries;
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

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Long getRatingCount() { return ratingCount; }
    public void setRatingCount(Long ratingCount) { this.ratingCount = ratingCount; }

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
    public List<String> getOtherItems() { return otherItems; }
    public void setOtherItems(List<String> otherItems) { this.otherItems = otherItems; }
    public String getLaundryImg() { return laundryImg; }
    public void setLaundryImg(String laundryImg) { this.laundryImg = laundryImg; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    @Override
    public String toString() {
        return "Laundry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", ratingCount=" + ratingCount +
                ", availableItems=" + availableItems +
                ", otherItems=" + otherItems +
                ", laundryImg='" + laundryImg + '\'' +
                ", about='" + about + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}
