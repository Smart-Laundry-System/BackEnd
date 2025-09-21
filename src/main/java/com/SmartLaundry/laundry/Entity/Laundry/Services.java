package com.SmartLaundry.laundry.Entity.Laundry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;


@Table(
        name = "laundry_services",
        uniqueConstraints = @UniqueConstraint(columnNames = {"laundry_id", "title"})
)
@Entity(name = "LaundryServices")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    @Column(name = "service_id")
    private Long id;

    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
//    private String category;

    @Column(name = "price", nullable = false)
    private String price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "laundry_id", nullable = false)
    private Laundry laundry;

    // getters/setters


    @Override
    public String toString() {
        return "Services{" +
                "id=" + id +
                ", title='" + title + '\'' +
//                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
//    public String getCategory() { return category; }
//    public void setCategory(String category) { this.category = category; }
    public Laundry getLaundry() { return laundry; }
    public void setLaundry(Laundry laundry) { this.laundry = laundry; }
}
