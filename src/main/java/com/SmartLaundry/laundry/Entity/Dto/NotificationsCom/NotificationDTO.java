package com.SmartLaundry.laundry.Entity.Dto.NotificationsCom;
public class NotificationDTO {
    private Long id;
    private String laundryName;
    private String laundryEmail;
    private String customerEmail;
    private String subject;
    private String message;
    private String laundryImg;
    private String status;   // SEEN / UNSEEN
    private String date;     // "2025-01-31"
    private String time;     // "23:16:43"

    public NotificationDTO(Long id, String laundryName, String laundryEmail,
                           String customerEmail, String subject, String message,
                           String laundryImg, String status, String date, String time) {
        this.id = id;
        this.laundryName = laundryName;
        this.laundryEmail = laundryEmail;
        this.customerEmail = customerEmail;
        this.subject = subject;
        this.message = message;
        this.laundryImg = laundryImg;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public String getLaundryImg() {
        return laundryImg;
    }

    public Long getId() { return id; }
    public String getLaundryName() { return laundryName; }
    public String getLaundryEmail() { return laundryEmail; }
    public String getCustomerEmail() { return customerEmail; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}