package com.SmartLaundry.laundry.Controller.Notification;

import com.SmartLaundry.laundry.Entity.Dto.NotificationsCom.NotificationDTO;
import com.SmartLaundry.laundry.Entity.Dto.NotificationsCom.NotificationRequest;
import com.SmartLaundry.laundry.Notification.SseHub;
import com.SmartLaundry.laundry.Service.Notification.NotificationsService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class NotificationController {

    private final NotificationsService service;
    private final SseHub sseHub;

    public NotificationController(NotificationsService service, SseHub sseHub) {
        this.service = service;
        this.sseHub = sseHub;
    }

    // CREATE
    @PostMapping("/addNotification")
    public ResponseEntity<String> addNotification(@Valid @RequestBody NotificationRequest req) {
        int created = service.addNotification(req);
        return ResponseEntity.ok("Created " + created + " notification(s).");
    }

    @GetMapping("/retrieveUserNotifications")
    public List<NotificationDTO> getUserNotifications(@RequestParam String email) {
        return service.getUserNotificationsDTO(email);
    }

    @GetMapping("/retrieveUnseenUserNotifications")
    public List<NotificationDTO> getUnseen(@RequestParam String email) {
        return service.getUnseenDTO(email);
    }

    @GetMapping("/unseenCount")
    public Map<String, Long> unseenCount(@RequestParam String email) {
        return Map.of("unseen", service.unseenCount(email));
    }

    @PutMapping("/notifications/{id}/seen")
    public ResponseEntity<?> markSeen(@PathVariable Long id) {
        service.markSeen(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/notifications/seen-all")
    public Map<String, Integer> markAllSeen(@RequestParam String email) {
        return Map.of("updated", service.markAllSeen(email));
    }

    // LIVE (SSE)
    @GetMapping(path = "/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam String email) {
        var emitter = sseHub.subscribe(email);
        try {
            long count = service.unseenCount(email);
            sseHub.push(email, "unseenCount", Map.of("unseen", count));
        } catch (Exception ignored) {}

        return emitter;
    }
}
