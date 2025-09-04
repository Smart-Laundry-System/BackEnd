package com.SmartLaundry.laundry.Service.Services;

import com.SmartLaundry.laundry.API.Mappers;
import com.SmartLaundry.laundry.Repository.Services.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {
    private final Mappers mappers;
    private final ServicesRepository repository;

    public ServicesService(Mappers mappers, ServicesRepository repository) {
        this.mappers = mappers;
        this.repository = repository;
    }

    public ResponseEntity<?> retriveServicesBasedOnLaundryId(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("laundry id is required.");
        }

        var services = repository.findAllByLaundry_Id(id);

        // Map entities → lightweight DTOs (id, title, category, price)
        var dto = services.stream()
                .map(mappers::toServiceLite)
                .toList();

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> RetriveAllServicesUsingIds(List<Long> id) {
        if(id ==null || id.isEmpty()){
            return ResponseEntity.badRequest().body("Id's not available");
        }
        var list = repository.findAllById(id).stream()
                .map(mappers::toServiceLite)
                .toList();
        return ResponseEntity.ok().body(list);
    }
}
