package com.SmartLaundry.laundry.Controller.Login;

import com.SmartLaundry.laundry.Dto.User.EmailSender;
import com.SmartLaundry.laundry.Dto.User.LoginRequest;
import com.SmartLaundry.laundry.Dto.User.UserUpdate;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Service.Emile.EmailService;
import com.SmartLaundry.laundry.Service.User.Auth.AuthonticationLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
public class AuthonticationLogin {

    private final AuthonticationLoginService userService;

    private final EmailService emailService;

    public AuthonticationLogin(AuthonticationLoginService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/addUser")
    public ResponseEntity<?> userRegister(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        String message = userService.registerUser(user);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
        String message = userService.verify(loginRequest);
        return ResponseEntity.ok().body(message);
    }
    // Step 1: Send OTP
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> sendOtp(@RequestBody EmailSender user) {
        String response = emailService.sendForgotPasswordOTP(user.getEmail());
        if (response.equals("OTP sent successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Step 2: Reset Password with OTP
    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserUpdate request) {
        request.setPassword(encoder.encode(request.getPassword()));
        return userService.updatePasswordWithOTP(request.getUserName(), request.getOtp(), request.getPassword());
    }

}
