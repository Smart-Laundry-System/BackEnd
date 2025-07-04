package com.SmartLaundry.laundry.Service.User.Auth;

import com.SmartLaundry.laundry.Dto.User.EmailSender;
import com.SmartLaundry.laundry.Dto.User.LoginRequest;
import com.SmartLaundry.laundry.Dto.User.UserUpdate;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import com.SmartLaundry.laundry.Service.Emile.EmailService;
import com.SmartLaundry.laundry.Service.JWT.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthonticationLoginService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final EmailService emailService;

    final AuthenticationManager authManager;

    public AuthonticationLoginService(UserRepository userRepository, AuthenticationManager authManager, JWTService jwtService, EmailService emailService) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public String registerUser(User user){
        try {
            if (user.getRole().equals(UserRole.LAUNDRY) || user.getRole().equals(UserRole.CUSTOMER)){
                Optional<User> user1 = userRepository.findByEmail(user.getEmail());
                if (!user1.isPresent()) {
                    userRepository.save(user);
                    return "User added successfully";
                } else if (!Objects.equals(user1.get().getRole(), user.getRole())) {
                    userRepository.save(user);
                    return "User added successfully";
                } else {
                    return "User already exist";
                }
            } else {
                return "Invalid role: only LAUNDRY or CUSTOMER allowed";
            }
        } catch (Exception e){
            return "Error when register user" + e.getMessage().toString();
        }
    }

    public String verify(LoginRequest user){
       try {
//           Optional<User> user1 = userRepository.findByEmail(user.getUsername());
//           if (user1.isPresent()){


               Authentication authentication = authManager.authenticate(
                       new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
               );
               if (authentication.isAuthenticated())
                   return jwtService.genarateToken(user.getUsername());
               else
                   return "Access restricted";
//           } else {
//               return "User not found";
//           }
        } catch (Exception e){
//           System.out.println(e.getClass().getName());
           return "Error"+e.getMessage().toString();
       }
    }

    public ResponseEntity<?> updatePasswordWithOTP(String email, String otp, String newPassword) {
        if (!emailService.validateOTP(email, otp)) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            emailService.clearOTP(email); // Invalidate OTP
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

}
