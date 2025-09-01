package com.SmartLaundry.laundry.Service.Notification;

import com.SmartLaundry.laundry.API.Mappers;
import com.SmartLaundry.laundry.Entity.Dto.NotificationDTO;
import com.SmartLaundry.laundry.Entity.Dto.NotificationRequest;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Notification.NotificationStatus;
import com.SmartLaundry.laundry.Entity.Notification.Notifications;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import com.SmartLaundry.laundry.Notification.SseHub;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.Notification.NotificationRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class NotificationsService {

    private final Mappers mappers;

    private final LaundryRepository laundryRepo;
    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;
    private final SseHub sseHub;

    public NotificationsService(Mappers mappers, LaundryRepository laundryRepo, NotificationRepository notificationRepo,
                                UserRepository userRepo,
                                SseHub sseHub) {
        this.mappers = mappers;
        this.laundryRepo = laundryRepo;
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
        this.sseHub = sseHub;
    }

    @Transactional
    public int addNotification(NotificationRequest req) {
        final long now = System.currentTimeMillis();
        final Date today = new Date(now);
        final Time nowTime = new Time(now);

        final String targetLaundryEmail =
                Optional.ofNullable(req.getLaundryEmail()).orElse("")
                        .trim().toLowerCase();

        // ⬇️ Pull laundry (by own email OR owner email) to get image/name
        var laundryOpt = laundryRepo.findByEmailOrOwnerEmailIgnoreCase(targetLaundryEmail);
        final String laundryImg = laundryOpt.map(l -> l.getLaundryImg()).orElse(null);
        final String laundryNameFallback = laundryOpt.map(l -> l.getName()).orElse(null);

        List<User> recipients = new ArrayList<>();

        if (isBlank(req.getCustomerEmail())) {
            recipients.addAll(
                    userRepo.findAllByLaundryEmailOrOwnerEmailAndRole(
                            targetLaundryEmail, UserLaundryRole.CUSTOMER)
            );
            if (recipients.isEmpty()) {
                recipients.addAll(
                        userRepo.findAllCustomersByOrderLaundryEmail(targetLaundryEmail)
                );
            }
        } else {
            userRepo.findByEmail(req.getCustomerEmail().trim().toLowerCase())
                    .ifPresent(recipients::add);
        }

        List<Notifications> toSave = new ArrayList<>();
        for (User u : recipients) {
            Notifications n = new Notifications();
            // prefer request name, otherwise fallback from Laundry entity
            n.setLaundryName(
                    isBlank(req.getLaundryName()) ? laundryNameFallback : req.getLaundryName()
            );
            n.setLaundryEmail(targetLaundryEmail);
            n.setLaundryImg(laundryImg);                // ⬅️ SET IMAGE
            n.setCustomerEmail(u.getEmail());
            n.setSubject(req.getSubject());
            n.setMessage(req.getMessage());
            n.setStatus(NotificationStatus.UNSEEN);
            n.setDate(today);
            n.setTime(nowTime);
            n.setUser(u);
            toSave.add(n);
        }

        if (toSave.isEmpty()) return 0;

        notificationRepo.saveAll(toSave);
        toSave.forEach(n -> sendLiveUpdate(n.getCustomerEmail()));
        return toSave.size();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    public List<NotificationDTO> getUserNotificationsDTO(String email) {
        return notificationRepo.findByCustomerEmailOrderByDateDescTimeDesc(email)
                .stream().map(mappers::toNotificationDTO).toList();
    }

    public List<NotificationDTO> getUnseenDTO(String email) {
        return notificationRepo.findByCustomerEmailAndStatusOrderByDateDescTimeDesc(
                        email, NotificationStatus.UNSEEN)
                .stream().map(mappers::toNotificationDTO).toList();
    }

    public long unseenCount(String email) {
        return notificationRepo.countByCustomerEmailAndStatus(
                email, NotificationStatus.UNSEEN);
    }

    @Transactional
    public void markSeen(Long id) {
        notificationRepo.findById(id).ifPresent(n -> {
            n.setStatus(NotificationStatus.SEEN);
            notificationRepo.save(n);
            sendLiveUpdate(n.getCustomerEmail());
        });
    }

    @Transactional
    public int markAllSeen(String email) {
        var unseen = notificationRepo.findByCustomerEmailAndStatusOrderByDateDescTimeDesc(
                email, NotificationStatus.UNSEEN);
        unseen.forEach(n -> n.setStatus(NotificationStatus.SEEN));
        notificationRepo.saveAll(unseen);
        sendLiveUpdate(email);
        return unseen.size();
    }

    private void sendLiveUpdate(String email) {
        long count = unseenCount(email);
        var payload = new LiveUpdate(count, Instant.now().toString());
        sseHub.push(email, "notification.update", payload);
    }

    public record LiveUpdate(long unseenCount, String ts) {}
}
