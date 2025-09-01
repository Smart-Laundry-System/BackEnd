package com.SmartLaundry.laundry.Notification;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SseHub {
    private final Map<String, List<SseEmitter>> emittersByEmail = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String email) {
        SseEmitter emitter = new SseEmitter(0L);
        emittersByEmail.computeIfAbsent(email, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> remove(email, emitter));
        emitter.onTimeout(() -> remove(email, emitter));
        emitter.onError(e -> remove(email, emitter));

        try { emitter.send(SseEmitter.event().name("connected").data("ok")); }
        catch (IOException ignored) {}

        return emitter;
    }

    public void push(String email, String eventName, Object data) {
        List<SseEmitter> list = emittersByEmail.get(email);
        if (list == null) return;
        for (SseEmitter em : list) {
            try { em.send(SseEmitter.event().name(eventName).data(data)); }
            catch (IOException ex) { remove(email, em); }
        }
    }

    private void remove(String email, SseEmitter em) {
        List<SseEmitter> list = emittersByEmail.get(email);
        if (list != null) {
            list.remove(em);
            if (list.isEmpty()) emittersByEmail.remove(email);
        }
    }
}
