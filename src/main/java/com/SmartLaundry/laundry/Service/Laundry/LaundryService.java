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
import com.SmartLaundry.laundry.Dto.Laundry.LaundryCreateRequest;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LaundryService {

    private final LaundryRepository laundryRepository;
    private final UserRepository userRepository;

    public LaundryService(LaundryRepository laundryRepository, UserRepository userRepository) {
        this.laundryRepository = laundryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String registerLaundry(LaundryCreateRequest req) {
        try {
            // 1) Create or update the User (role LAUNDRY)
            User owner = userRepository.findByEmail(req.getEmail()).orElseGet(User::new);
            owner.setEmail(req.getEmail());
            owner.setName(req.getName());          // reuse single 'name'
            owner.setPassword(req.getPassword());  // already encoded in controller
            owner.setRole(req.getRole());          // should be LAUNDRY
            owner.setPhone(req.getPhone());
            owner.setPhone_2(req.getPhone2());
            owner.setAddress(req.getAddress());
            owner = userRepository.save(owner);

            // 2) Create Laundry
            Laundry l = new Laundry();
            l.setName(req.getName());              // laundry display name
            l.setPhone(req.getPhone());            // use same phone/address unless you separate later
            l.setAddress(req.getAddress());
            l.setLaundryImg(req.getLaundryImg());
            l.setAbout(req.getAbout());

            l.setServices(req.getServices());
            l.setAvailableItems(req.getAvailableItems());
            l.setOtherItems(req.getOtherItems());
            l.setOwner(owner);

            // 3) Link OWNER via association (cascade from Laundry will save this)
            UserLaundry link = new UserLaundry();
            link.setUser(owner);
            link.setLaundry(l);
            link.setRelationRole(UserLaundryRole.OWNER);
            l.getUserLaundries().add(link);
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