package com.SmartLaundry.laundry.Repository.Laundry;

import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLaundryRepository extends JpaRepository<UserLaundry, Long> {
    Optional<UserLaundry> findByUser_IdAndLaundry_Id(Long userId, Long laundryId);
    boolean existsByUser_IdAndLaundry_Id(Long userId, Long laundryId);
    List<UserLaundry> findAllByLaundry_IdAndRelationRole(Long laundryId, UserLaundryRole role);
}
