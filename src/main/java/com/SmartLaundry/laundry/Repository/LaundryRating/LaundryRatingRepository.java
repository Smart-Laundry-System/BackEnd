package com.SmartLaundry.laundry.Repository.LaundryRating;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.LaundryRating.LaundryRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LaundryRatingRepository extends JpaRepository<LaundryRating, Long> {

    Optional<LaundryRating> findByLaundryIdAndCustomerEmailIgnoreCase(Long laundryId, String customerEmail);

    @Query("select avg(r.value) from LaundryRating r where r.laundry.id = :laundryId")
    Double averageFor(@Param("laundryId") Long laundryId);

    @Query("select count(r) from LaundryRating r where r.laundry.id = :laundryId")
    long countFor(@Param("laundryId") Long laundryId);
}
