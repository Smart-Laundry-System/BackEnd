package com.SmartLaundry.laundry.Controller.Complain;

import com.SmartLaundry.laundry.Entity.Complain.Complain;
import com.SmartLaundry.laundry.Service.Complain.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ComplainController {

    private final ComplainService service;

    public ComplainController(ComplainService service) {
        this.service = service;
    }

    @PostMapping("/addComplain")
    public ResponseEntity<?> addComplain(@RequestBody Complain complain){
        String message = service.addComplain(complain);
        return ResponseEntity.ok().body(message);
    }
}
