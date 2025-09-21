package com.SmartLaundry.laundry.Entity.LaundryRating;

import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "laundry_ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"laundry_id", "customer_email"})
)
public class LaundryRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "laundry_id")
    private Laundry laundry;

    @Column(name = "customer_email", nullable = false, length = 255)
    private String customerEmail;

    @Column(name = "value", nullable = false)
    private Double value; // 0.0 .. 5.0

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() { createdAt = updatedAt = Instant.now(); }

    @PreUpdate
    void preUpdate() { updatedAt = Instant.now(); }

    // --- getters/setters ---
    public Long getId() { return id; }
    public Laundry getLaundry() { return laundry; }
    public void setLaundry(Laundry laundry) { this.laundry = laundry; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "LaundryRating{" +
                "id=" + id +
                ", customerEmail='" + customerEmail + '\'' +
                ", value=" + value +
                '}';
    }
}
