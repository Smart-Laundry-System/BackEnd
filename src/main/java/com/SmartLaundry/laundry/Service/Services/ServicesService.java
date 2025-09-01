package com.SmartLaundry.laundry.Service.Services;

import com.SmartLaundry.laundry.Repository.Services.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServicesService {
    private final ServicesRepository repository;

    public ServicesService(ServicesRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> retriveServicesBasedOnLaundryId(Long id) {
    }
}
