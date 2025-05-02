package com.SmartLaundry.laundry.Controller.User;

import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Service.User.Impl.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
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
}
