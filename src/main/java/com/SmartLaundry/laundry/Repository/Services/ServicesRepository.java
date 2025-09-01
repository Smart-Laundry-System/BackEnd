package com.SmartLaundry.laundry.Repository.Services;

import com.SmartLaundry.laundry.Entity.Laundry.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {
    // Only services that belong to the same Laundry (by id)
    List<Services> findAllByIdInAndLaundry_Id(List<Long> ids, Long laundryId);
}