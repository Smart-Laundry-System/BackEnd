package com.SmartLaundry.laundry.Controller.Login;
//
//import com.SmartLaundry.laundry.Dto.User.EmailSender;
//import com.SmartLaundry.laundry.Dto.User.LoginRequest;
//import com.SmartLaundry.laundry.Dto.User.UserUpdate;
//import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
//import com.SmartLaundry.laundry.Entity.User.User;
//import com.SmartLaundry.laundry.Service.Emile.EmailService;
//import com.SmartLaundry.laundry.Service.Laundry.LaundryService;
//import com.SmartLaundry.laundry.Service.User.Auth.AuthonticationLoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth/v1")
//public class AuthonticationLogin {
//
//    @Autowired
//    private LaundryService laundryService;
//
//    private final AuthonticationLoginService userService;
//
//    private final EmailService emailService;
//
//    public AuthonticationLogin(AuthonticationLoginService userService, EmailService emailService) {
//        this.userService = userService;
//        this.emailService = emailService;
//    }
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//    @PostMapping("/addUser")
//    public ResponseEntity<?> userRegister(@RequestBody User user){
//        user.setPassword(encoder.encode(user.getPassword()));
//        String message = userService.registerUser(user);
//        return ResponseEntity.ok().body(message);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
//        String message = userService.verify(loginRequest);
//        return ResponseEntity.ok().body(message);
//    }
//    // Step 1: Send OTP
//    @PostMapping("/forgotPassword")
//    public ResponseEntity<?> sendOtp(@RequestBody EmailSender user) {
//        String response = emailService.sendForgotPasswordOTP(user.getEmail());
//        if (response.equals("OTP sent successfully")) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @PostMapping("/addLaundry")
//    public ResponseEntity<?> addLaundry(@RequestBody Laundry user){
//        user.setPassword(encoder.encode(user.getPassword()));
//        String message = laundryService.registerLaundry(user);
//        return ResponseEntity.ok().body(message);
//    }
//
//    // Step 2: Reset Password with OTP
//    @PutMapping("/resetPassword")
//    public ResponseEntity<?> resetPassword(@RequestBody UserUpdate request) {
//        request.setPassword(encoder.encode(request.getPassword()));
//        return userService.updatePasswordWithOTP(request.getUserName(), request.getOtp(), request.getPassword());
//    }
//
//}

import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryCreateRequest;
import com.SmartLaundry.laundry.Entity.Dto.User.EmailSender;
import com.SmartLaundry.laundry.Entity.Dto.User.LoginRequest;
import com.SmartLaundry.laundry.Entity.Dto.User.UserUpdate;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Service.Emile.EmailService;
import com.SmartLaundry.laundry.Service.Laundry.LaundryService;
import com.SmartLaundry.laundry.Service.User.Auth.AuthonticationLoginService;
import com.SmartLaundry.laundry.Entity.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
public class AuthonticationLogin {

    private final LaundryService laundryService;

    private final AuthonticationLoginService userService;
    private final EmailService emailService;

    public AuthonticationLogin(AuthonticationLoginService userService, EmailService emailService, LaundryService laundryService) {
        this.userService = userService;
        this.emailService = emailService;
        this.laundryService = laundryService;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/addUser")
    public ResponseEntity<?> appRegister(@RequestBody User user){
        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        String message = userService.appRegister(user);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/addLaundryUser")
    public ResponseEntity<?> userRegister(@RequestBody User user, @RequestParam String laundryEmail){
        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        String message = userService.registerUser(user,laundryEmail);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
        String message = userService.verify(loginRequest);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> sendOtp(@RequestBody EmailSender user) {
        String response = emailService.sendForgotPasswordOTP(user.getEmail());
        if ("OTP sent successfully".equals(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/addLaundry")
    public ResponseEntity<?> addLaundry(@RequestBody LaundryCreateRequest request){
        if (request.getRole() == null) {
            request.setRole(UserRole.LAUNDRY);
        }
        if (request.getPassword() != null) {
            request.setPassword(encoder.encode(request.getPassword()));
        }
        String message = laundryService.registerLaundry(request);
        return ResponseEntity.ok().body(message);
    }


    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserUpdate request) {
        request.setPassword(encoder.encode(request.getPassword()));
        return userService.updatePasswordWithOTP(request.getUserName(), request.getOtp(), request.getPassword());
    }
}
