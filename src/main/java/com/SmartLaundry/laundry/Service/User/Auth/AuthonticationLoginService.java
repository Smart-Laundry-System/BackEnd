package com.SmartLaundry.laundry.Service.User.Auth;
//
//import com.SmartLaundry.laundry.Dto.User.LoginRequest;
//import com.SmartLaundry.laundry.Entity.Roles.UserRole;
//import com.SmartLaundry.laundry.Entity.User.User;
//import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
//import com.SmartLaundry.laundry.Repository.User.UserRepository;
//import com.SmartLaundry.laundry.Service.Emile.EmailService;
//import com.SmartLaundry.laundry.Service.JWT.JWTService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class AuthonticationLoginService {
//    private final UserRepository userRepository;
//
//    private final JWTService jwtService;
//
//    private final EmailService emailService;
//
//    final AuthenticationManager authManager;
//
//    public AuthonticationLoginService(UserRepository userRepository, AuthenticationManager authManager, JWTService jwtService, EmailService emailService, LaundryRepository laundryRepository) {
//        this.userRepository = userRepository;
//        this.authManager = authManager;
//        this.jwtService = jwtService;
//        this.emailService = emailService;
//    }
//
//    public String registerUser(User user){
//        try {
//            if (user.getRole().equals(UserRole.CUSTOMER)){
//                Optional<User> user1 = userRepository.findByEmail(user.getEmail());
//                if (user1.isEmpty()) {
//                    userRepository.save(user);
//                    return "User added successfully";
//                } else if (!Objects.equals(user1.get().getRole(), user.getRole())) {
//                    userRepository.save(user);
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
//
//
//
//    public String verify(LoginRequest user){
//       try {
////           Optional<User> user1 = userRepository.findByEmail(user.getUsername());
////           if (user1.isPresent()){
//
//
//               Authentication authentication = authManager.authenticate(
//                       new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
//               );
//               if (authentication.isAuthenticated())
//                   return jwtService.genarateToken(user.getUsername());
//               else
//                   return "Access restricted";
////           } else {
////               return "User not found";
////           }
//        } catch (Exception e){
////           System.out.println(e.getClass().getName());
//           return "Error"+e.getMessage().toString();
//       }
//    }
//
//    public ResponseEntity<?> updatePasswordWithOTP(String email, String otp, String newPassword) {
//        if (!emailService.validateOTP(email, otp)) {
//            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
//        }
//
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setPassword(newPassword);
//            userRepository.save(user);
//            emailService.clearOTP(email); // Invalidate OTP
//            return ResponseEntity.ok("Password updated successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("User not found.");
//        }
//    }
//}

import com.SmartLaundry.laundry.Entity.Dto.User.LoginRequest;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.Laundry.UserLaundryRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import com.SmartLaundry.laundry.Service.Emile.EmailService;
import com.SmartLaundry.laundry.Service.JWT.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthonticationLoginService {

    private final UserLaundryRepository userLaundryRepository;
    private final LaundryRepository laundryRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final EmailService emailService;

    public AuthonticationLoginService(UserRepository userRepository,
                                      AuthenticationManager authManager,
                                      JWTService jwtService,
                                      EmailService emailService, LaundryRepository laundryRepository, UserLaundryRepository userLaundryRepository) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.laundryRepository = laundryRepository;
        this.userLaundryRepository = userLaundryRepository;
    }

//    public String registerUser(User user, String laundryEmail){
//        try {
//            Optional<User> existing = userRepository.findByEmail(user.getEmail());
//            if (existing.isPresent()) {
//                // If user exists but you want to allow role change, update fields here as needed
//                return "User already exist";
//            }
//            userRepository.save(user);
//            return "User added successfully";
//        } catch (Exception e){
//            return "Error when register user: " + e.getMessage();
//        }
//    }

    @Transactional
    public String registerUser(User incoming, String laundryEmail) {
        // 1) Find the laundry by owner email (matches your earlier usage)
        Laundry laundry = laundryRepository.findFirstByOwner_Email(laundryEmail)
                .orElseThrow(() -> new IllegalArgumentException("Laundry not found for owner email: " + laundryEmail));

        // 2) Upsert the user by email
        User user = userRepository.findByEmail(incoming.getEmail()).orElseGet(User::new);
        boolean existed = user.getId() != null;

        user.setEmail(incoming.getEmail());
        user.setName(incoming.getName());
        user.setPassword(incoming.getPassword()); // already encoded by controller
        user.setRole(incoming.getRole() == null ? UserRole.CUSTOMER : incoming.getRole());
        user.setPhone(incoming.getPhone());
        user.setPhone_2(incoming.getPhone_2());
        user.setAddress(incoming.getAddress());

        user = userRepository.save(user);

        // 3) Link user <-> laundry if not already linked
        boolean alreadyLinked = userLaundryRepository.existsByUser_IdAndLaundry_Id(user.getId(), laundry.getId());
        if (!alreadyLinked) {
            UserLaundry link = new UserLaundry();
            link.setUser(user);
            link.setLaundry(laundry);
            link.setRelationRole(UserLaundryRole.CUSTOMER); // map UserRole -> UserLaundryRole
            userLaundryRepository.save(link); // or: laundry.getUserLaundries().add(link); laundryRepository.save(laundry);
        }

        return existed ? "User updated and linked to laundry" : "User added and linked to laundry";
    }

    public String verify(LoginRequest user){
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.genarateToken(user.getUsername());
            }
            return "Access restricted";
        } catch (Exception e){
            return "Error" + e.getMessage();
        }
    }

    public ResponseEntity<?> updatePasswordWithOTP(String email, String otp, String newPassword) {
        if (!emailService.validateOTP(email, otp)) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        User u = optionalUser.get();
        // newPassword is already encoded in the controller
        u.setPassword(newPassword);
        userRepository.save(u);
        emailService.clearOTP(email);
        return ResponseEntity.ok("Password updated successfully.");
    }

    public String appRegister(User user) {
        try {
            Optional<User> existing = userRepository.findByEmail(user.getEmail());
            if (existing.isPresent()) {
                throw new Exception("Customer already added");
            }
            userRepository.save(user);
            return "User added successfully";
        } catch (Exception e){
            return "Error when register user: " + e.getMessage();
        }
    }
}