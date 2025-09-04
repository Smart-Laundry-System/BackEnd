package com.SmartLaundry.laundry.Controller.Services;

import com.SmartLaundry.laundry.Service.Services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ServicesController {
    private final ServicesService service;

    public ServicesController(ServicesService service) {
        this.service = service;
    }

    @GetMapping("/retriveServices")
    public ResponseEntity<?> retriveServices(@RequestParam Long id){
        return service.retriveServicesBasedOnLaundryId(id);
    }

    @GetMapping("/retriveServiceById")
    public ResponseEntity<?> retriveServiceById(@RequestParam("ids") List<Long> id){
        return service.RetriveAllServicesUsingIds(id);
    }
}
