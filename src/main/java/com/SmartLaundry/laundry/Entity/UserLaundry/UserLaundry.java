package com.SmartLaundry.laundry.Entity.UserLaundry;
//
//import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
//import com.SmartLaundry.laundry.Entity.User.User;
//import jakarta.persistence.*;
//
//// UserLaundry.java
//@Entity
//@Table(
//        name = "user_laundry",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "laundry_id"})
//)
//public class UserLaundry {
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "laundry_id")
//    private Laundry laundry;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserLaundryRole relationRole; // OWNER, EMPLOYEE, CUSTOMER, etc.
//
//    @Column(nullable = false)
//    private java.time.Instant linkedAt = java.time.Instant.now();
//
//    // getters/setters, equals/hashCode if needed
//}

import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.User.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_laundry",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","laundry_id"}))
public class UserLaundry {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "laundry_id")
    private Laundry laundry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserLaundryRole relationRole;

    @Column(nullable = false)
    private java.time.Instant linkedAt = java.time.Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Laundry getLaundry() { return laundry; }
    public void setLaundry(Laundry laundry) { this.laundry = laundry; }
    public UserLaundryRole getRelationRole() { return relationRole; }
    public void setRelationRole(UserLaundryRole relationRole) { this.relationRole = relationRole; }
    public java.time.Instant getLinkedAt() { return linkedAt; }
    public void setLinkedAt(java.time.Instant linkedAt) { this.linkedAt = linkedAt; }
}