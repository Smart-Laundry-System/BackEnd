package com.SmartLaundry.laundry.Service.User.Impl;

import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.User.UserPrinciple;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository
//            ,
//                       PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }
//
//    public String registerUser(User user) {
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail()); // Use getEmail()
//        if (existingUser.isPresent()) {
//            return "Email already exists!"; // Email already exists check
//        }
//
//        // Encrypt password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password
//        user.setRole("ROLE_USER"); // Default role (could be adjusted)
//        userRepository.save(user); // Save to DB
//        return "User registered successfully!"; // Success message
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrinciple(user.get());
    }

    public List<User> retriveUsers() {
        return userRepository.findAll();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//    }
}
