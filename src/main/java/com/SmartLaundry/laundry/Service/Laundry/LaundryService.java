package com.SmartLaundry.laundry.Service.Laundry;
//
//import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
//import com.SmartLaundry.laundry.Entity.Roles.UserRole;
//import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class LaundryService {
//
//    private final LaundryRepository laundryRepository;
//
//    public LaundryService(LaundryRepository laundryRepository) {
//        this.laundryRepository = laundryRepository;
//    }
//
//    public String registerLaundry(Laundry user) {
//        try {
//            if(user.getRole().equals(UserRole.LAUNDRY)) {
//                Optional<Laundry> user1 = laundryRepository.findByEmail(user.getEmail());
//                if (user1.isEmpty()) {
//                    laundryRepository.save(user);
//                    return "User added successfully";
//                } else if (!Objects.equals(user1.get().getRole(), user.getRole())) {
//                    laundryRepository.save(user);
//                    return "User added successfully";
//                } else {
//                    return "User already exist";
//                }
//            } else {
//                return "User already exists";
//            }
//        } catch (Exception e){
//            return "Error when register user" + e.getMessage().toString();
//        }
//    }
//}
import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryCreateRequest;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.LaundryRating.LaundryRating;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.LaundryRating.LaundryRatingRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LaundryService {

    private final LaundryRepository laundryRepository;
    private final UserRepository userRepository;

    private final LaundryRatingRepository ratingRepository;

    public LaundryService(LaundryRepository laundryRepository, UserRepository userRepository, LaundryRatingRepository ratingRepository) {
        this.laundryRepository = laundryRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    // LaundryService.java
// LaundryService.java
    @Transactional
    public String addRating(Double rating, Long laundryId, String customerEmail) {
        if (rating == null || rating < 0.0 || rating > 5.0)
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        if (laundryId == null)
            throw new IllegalArgumentException("laundryId is required");
        if (customerEmail == null || customerEmail.isBlank())
            throw new IllegalArgumentException("customerEmail is required");

        Laundry laundry = laundryRepository.findById(laundryId)
                .orElseThrow(() -> new EntityNotFoundException("Laundry not found: id=" + laundryId));

        var existingOpt = ratingRepository.findByLaundryIdAndCustomerEmailIgnoreCase(laundryId, customerEmail);
        boolean updated = existingOpt.isPresent();

        LaundryRating lr = existingOpt.orElseGet(() -> {
            LaundryRating n = new LaundryRating();
            n.setLaundry(laundry);
            n.setCustomerEmail(customerEmail);
            return n;
        });

        lr.setValue(rating);
        ratingRepository.save(lr); // updates if existing, inserts if new (unique key prevents duplicates)

        // refresh aggregates
        Double avg = ratingRepository.averageFor(laundryId);
        long count = ratingRepository.countFor(laundryId);

        laundry.setRating(avg == null ? 0.0 : avg);
        laundry.setRatingCount(count);
        laundryRepository.save(laundry);

        return (updated ? "Updated" : "Created") +
                String.format(" rating. avg=%.2f, count=%d", laundry.getRating(), laundry.getRatingCount());
    }

    @Transactional
    public String registerLaundry(LaundryCreateRequest req) {
        try {
            Optional<User> existingOwner = userRepository.findByEmail(req.getEmail());
            if (existingOwner.isPresent() && req.getRole() == UserRole.LAUNDRY) {
                throw new Exception("Laundry already exist");
            }

            User owner = existingOwner.orElseGet(User::new);
            owner.setEmail(req.getEmail());
            owner.setName(req.getName());
            owner.setPassword(req.getPassword()); // already encoded in controller
            owner.setRole(req.getRole());         // LAUNDRY
            owner.setPhone(req.getPhone());
            owner.setPhone_2(req.getPhone2());
            owner.setAddress(req.getAddress());
            owner = userRepository.save(owner);

            // 2) Build Laundry
            Laundry l = new Laundry();
            l.setName(req.getName());
            l.setPhone(req.getPhone());
            l.setAddress(req.getAddress());
            l.setLaundryImg(req.getLaundryImg());
            l.setAbout(req.getAbout());
            l.setOpenTime(req.getOpenTime());
            l.setCloseTime(req.getCloseTime());
            l.setAvailableItems(req.getAvailableItems());
            l.setOtherItems(req.getOtherItems());
            l.setOwner(owner);

            // 3) Map DTO services to entity and link the parent
            if (req.getServices() != null) {
                for (Services sd : req.getServices()) {
                    Services s = new Services();
                    s.setId(null);
                    s.setTitle(sd.getTitle());
//                    s.setCategory(sd.getCategory());
                    s.setPrice(sd.getPrice());
                    l.addService(s); // ✅ sets s.laundry = l and adds to list
                }
            }

            // 4) Persist once; Cascade inserts Services with non-null laundry_id
            laundryRepository.save(l);

            return "User added successfully";
        } catch (Exception e) {
            return "Error when register user:\n" + e.getMessage();
        }
    }

    private static String buildExceptionTree(Throwable t) {
        StringBuilder sb = new StringBuilder();
        buildExceptionTree(t, sb, 0, new java.util.HashSet<>());
        return sb.toString();
    }

    private static void buildExceptionTree(Throwable t, StringBuilder sb, int level, java.util.Set<Throwable> seen) {
        if (t == null || seen.contains(t)) return; // prevent cycles
        seen.add(t);

        String indent = "  ".repeat(level);

        // Header line for this throwable
        sb.append(indent)
                .append(t.getClass().getName())
                .append(": ")
                .append(t.getMessage() == null ? "" : t.getMessage())
                .append('\n');

        // Stack frames
        for (StackTraceElement ste : t.getStackTrace()) {
            sb.append(indent).append("  at ").append(ste.toString()).append('\n');
        }

        // Suppressed exceptions
        for (Throwable s : t.getSuppressed()) {
            sb.append(indent).append("  Suppressed: ").append(s.getClass().getName())
                    .append(": ").append(s.getMessage() == null ? "" : s.getMessage()).append('\n');
            buildExceptionTree(s, sb, level + 2, seen);
        }

        // Cause chain
        Throwable cause = t.getCause();
        if (cause != null) {
            sb.append(indent).append("Caused by:\n");
            buildExceptionTree(cause, sb, level + 1, seen);
        }
    }

}