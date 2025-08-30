//package com.SmartLaundry.laundry.API;
//
//import com.SmartLaundry.laundry.Service.CustomerLinkService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/auth")
//public class CustomerLinkController {
//
//    private final CustomerLinkService service;
//
//    public CustomerLinkController(CustomerLinkService service) {
//        this.service = service;
//    }
//
//    // PUT /api/auth/updateCustomer
//    // Body: {"customerEmail":"x@y.com","laundryEmail":"owner@laundry.com"}
//    @PutMapping("/updateCustomer")
//    public ResponseEntity<String> updateCustomer(@RequestBody AddCustomerRequest body) {
//        String msg = service.addCustomer(body.getCustomerEmail(), body.getLaundryEmail());
//        return ResponseEntity.ok(msg);
//    }
//
//    // GET /api/auth/retriveUser?email=someone@email.com
//    // (If you have Security, you can derive email from token; this keeps it simple.)
//    @GetMapping("/retriveUser")
//    public ResponseEntity<UserDTO> retriveUser(@RequestParam("email") String email) {
//        return ResponseEntity.ok(service.getUserAsDTOByEmail(email));
//    }
//
//    // GET /api/auth/retriveLaundries
//    @GetMapping("/retriveLaundries")
//    public ResponseEntity<List<LaundryDTO>> retriveLaundries() {
//        return ResponseEntity.ok(service.getAllLaundriesAsDTO());
//    }
//}
