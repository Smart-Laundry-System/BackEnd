package com.SmartLaundry.laundry.Repository.Notification;

import com.SmartLaundry.laundry.Entity.Notification.NotificationStatus;
import com.SmartLaundry.laundry.Entity.Notification.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByCustomerEmailOrderByDateDescTimeDesc(String email);

    List<Notifications> findByCustomerEmailAndStatusOrderByDateDescTimeDesc(
            String email, NotificationStatus status);

    long countByCustomerEmailAndStatus(String email, NotificationStatus status);
}
