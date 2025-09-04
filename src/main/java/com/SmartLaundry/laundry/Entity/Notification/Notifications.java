package com.SmartLaundry.laundry.Entity.Notification;

import com.SmartLaundry.laundry.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "not_id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column
    private String laundryName;

    @Column(nullable = false)
    private String laundryEmail;

    @Column
    private Date date;

    @Column
    private String customerEmail;

    @Column
    private Time time;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 4000)
    private String message;

    @Column
    private String laundryImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status = NotificationStatus.UNSEEN;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getLaundryImg() {
        return laundryImg;
    }

    public void setLaundryImg(String laundryImg) {
        this.laundryImg = laundryImg;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLaundryName() { return laundryName; }
    public void setLaundryName(String laundryName) { this.laundryName = laundryName; }

    public String getLaundryEmail() { return laundryEmail; }
    public void setLaundryEmail(String laundryEmail) { this.laundryEmail = laundryEmail; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", laundryName='" + laundryName + '\'' +
                ", laundryEmail='" + laundryEmail + '\'' +
                ", date=" + date +
                ", customerEmail='" + customerEmail + '\'' +
                ", time=" + time +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", laundryImg='" + laundryImg + '\'' +
                ", status=" + status +
                '}';
    }
}
