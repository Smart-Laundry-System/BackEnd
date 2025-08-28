package com.SmartLaundry.laundry.Controller.User;

//import com.SmartLaundry.laundry.Entity.User.User;
//import com.SmartLaundry.laundry.Service.User.Impl.UserService;
////import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    private final UserService userService;
//
//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/retriveUser")
//    public ResponseEntity<?> retriveUser(){
//        List<User> users = userService.retriveUsers();
//        return ResponseEntity.ok().body(users);
//    }
//
//    @GetMapping("/details")
//    public ResponseEntity<?> laundryDetails(@RequestParam String email){
//        return ResponseEntity.ok().body(userService.retriveDetails(email));
//    }
//
//}

import com.SmartLaundry.laundry.Dto.Laundry.LaundryDetailsDto;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Service.User.Impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/retriveUser")
    public ResponseEntity<?> retriveUser(){
        List<User> users = userService.retriveUsers();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<?> addCustomer(@RequestParam String customerEmail, @RequestParam String laundryEmail){
        String message = userService.addCustomer(customerEmail, laundryEmail);
        return ResponseEntity.ok().body(message);
    }
    /**
     * For your React Native screen:
     * /api/auth/details?email=someone@example.com
     * Returns LaundryDetailsDto (Laundry + users) for a laundry associated to that user.
     * You can decide in the service which Laundry to pick (e.g., OWNER’s primary laundry).
     */
    @GetMapping("/details")
    public ResponseEntity<?> laundryDetails(@RequestParam String email){
        LaundryDetailsDto dto = userService.retriveDetails(email); // implement to build the DTO
        return ResponseEntity.ok().body(dto);
    }
}