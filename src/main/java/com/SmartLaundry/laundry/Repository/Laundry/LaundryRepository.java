package com.SmartLaundry.laundry.Repository.Laundry;
//
//import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface LaundryRepository extends JpaRepository<Laundry, Long> {
//    Optional<Laundry> findByEmail(String email);
//    Optional<Laundry> findFirstByOwnerEmail(String email);
//}
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    Optional<Laundry> findFirstByOwner_Email(String email); // <- nested property
    List<Laundry> findAll();

    @Query("""
           select l
           from Laundry l
           where lower(l.owner.email) = lower(:email)
           """)
    Optional<Laundry> findByEmailOrOwnerEmailIgnoreCase(@Param("email") String email);

    boolean findByLaundryEmailComplain(String laundryEmail);
}