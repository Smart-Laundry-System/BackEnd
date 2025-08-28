package com.SmartLaundry.laundry.Service.Emile;
//
//import com.SmartLaundry.laundry.Entity.User.User;
//import com.SmartLaundry.laundry.Repository.User.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Random;
//
//@Service
//public class EmailService {
//    private final JavaMailSender mailSender;
//
//    private final Map<String, String> otpStorage = new HashMap<>(); // In-memory OTP storage
//
//
//    private final UserRepository userRepository;
//    private final Random random = new SecureRandom();
//
//    public EmailService(JavaMailSender mailSender, UserRepository userRepository) {
//        this.mailSender = mailSender;
//        this.userRepository = userRepository;
//    }
//
//    public void sendEmail(String to, String subject, String message) {
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(to);
//        email.setSubject(subject);
//        email.setText(message);
//        mailSender.send(email);
//    }
//
//
//    public String generateOTP(int length) {
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append(random.nextInt(10));
//        }
//        return sb.toString();
//    }
//
//    public String sendForgotPasswordOTP(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isPresent()) {
//            String otp = generateOTP(6);
//            String subject = "Password Reset OTP";
//            String message = "You have requested to reset your password.\n\n" +
//                    "Use the following OTP to reset your password:\n\n" +
//                    "OTP: " + otp + "\n\n" +
//                    "If you did not request this, please ignore this email.";
//            sendEmail(email, subject, message);
//            if (!otpStorage.equals(otpStorage.get(email)))
//                otpStorage.put(email, otp); // Store OTP mapped to email
//            else {
//                clearOTP(email);
//                otpStorage.put(email, otp);
//            }
//            return "OTP sent successfully";
//        } else {
//            return "Email not found";
//        }
//    }
//
//    public boolean validateOTP(String email, String otp) {
//        return otp.equals(otpStorage.get(email));
//    }
//
//    public void clearOTP(String email) {
//        otpStorage.remove(email);
//    }
//
//}


import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    // In-memory, thread-safe
    private static class OtpRecord {
        final String code;
        final long expiresAtEpochSec;
        OtpRecord(String code, long expiresAtEpochSec) {
            this.code = code;
            this.expiresAtEpochSec = expiresAtEpochSec;
        }
    }

    private final Map<String, OtpRecord> otpStorage = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;
    private static final long OTP_TTL_SECONDS = 300; // 5 minutes

    public EmailService(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    public String generateOTP(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public String sendForgotPasswordOTP(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return "Email not found";

        String otp = generateOTP(OTP_LENGTH);
        String subject = "Password Reset OTP";
        String message = "You have requested to reset your password.\n\n" +
                "Use the following OTP to reset your password:\n\n" +
                "OTP: " + otp + "\n\n" +
                "This OTP expires in 5 minutes. If you did not request this, please ignore this email.";

        // Store/replace OTP with expiry (fixes the previous equals bug)
        long expiresAt = Instant.now().getEpochSecond() + OTP_TTL_SECONDS;
        otpStorage.put(email, new OtpRecord(otp, expiresAt));

        sendEmail(email, subject, message);
        return "OTP sent successfully";
    }

    public boolean validateOTP(String email, String otp) {
        OtpRecord rec = otpStorage.get(email);
        if (rec == null) return false;
        long now = Instant.now().getEpochSecond();
        return now <= rec.expiresAtEpochSec && rec.code.equals(otp);
    }

    public void clearOTP(String email) {
        otpStorage.remove(email);
    }
}