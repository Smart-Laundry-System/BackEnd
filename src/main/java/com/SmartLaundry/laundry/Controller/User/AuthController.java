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

import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryDetailsDto;
import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryDTO;
import com.SmartLaundry.laundry.Entity.Dto.User.UserDTO;
import com.SmartLaundry.laundry.Entity.Laundry.UpdateCustomer;
import com.SmartLaundry.laundry.Service.CustomerLinkService;
import com.SmartLaundry.laundry.Service.Laundry.LaundryService;
import com.SmartLaundry.laundry.Service.User.Impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomerLinkService service;
    private final UserService userService;

    private final LaundryService laundryService;

    public AuthController(UserService userService, CustomerLinkService service, LaundryService laundryService) {
        this.userService = userService;
        this.service = service;
        this.laundryService = laundryService;
    }

    @GetMapping("/retriveUser")
    public ResponseEntity<?> retriveUser(@RequestParam String laundryEmail){
        UserDTO user = service.retriveUser(laundryEmail);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/retriveUsers")
    public ResponseEntity<?> retriveUsers(){
        List<UserDTO> users = service.retriveAllUsers();
        return ResponseEntity.ok().body(users);
    }
//    public ResponseEntity<UserDTO> retriveUser(@RequestParam("email") String email) {
//        return ResponseEntity.ok(service.getUserAsDTOByEmail(email));
//    }

    @GetMapping("/retriveLaundries")
//    public ResponseEntity<?> retriveLaundries(){
//        List<User> users = userService.retriveLaundries();
//        return ResponseEntity.ok().body(users);
//    }
    public ResponseEntity<List<LaundryDTO>> retriveLaundries() {
        return ResponseEntity.ok(service.getAllLaundriesAsDTO());
    }

    @PutMapping("/addRating")
    public ResponseEntity<?> addRating(@RequestParam Double rating, @RequestParam Long id, @RequestParam String customerEmail){
        String message = laundryService.addRating(rating,id,customerEmail);
        return ResponseEntity.ok().body(message);
    }

//    @PutMapping("/updateUser")
//    public ResponseEntity<?> updateUser()

    @PutMapping("/updateCustomer")
//    public ResponseEntity<?> addCustomer(@RequestBody UpdateCustomer customer){
//        String message = userService.addCustomer(customer.getCustomerEmail(), customer.getLaundryEmail());
//        return ResponseEntity.ok().body(message);
//    }
    public ResponseEntity<String> updateCustomer(@RequestBody UpdateCustomer body) {
        String msg = service.addCustomer(body.getCustomerEmail(), body.getLaundryId());
        return ResponseEntity.ok(msg);
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

    @GetMapping("/laundryById")
    public ResponseEntity<?> laundryById(@RequestParam Long id){
        LaundryDetailsDto dto = userService.retrieveDetailsById(id); // implement to build the DTO
        return ResponseEntity.ok().body(dto);
    }
}