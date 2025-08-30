package com.SmartLaundry.laundry.Repository.User;

import com.SmartLaundry.laundry.Entity.Dto.UserDTO;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByUsername(String username);
//    Optional<User> findByEmail(String email);
//    List<UserDTO> findByRole(UserRole role);
//    List<UserDTO> findByRole(UserRole role);
Optional<User> findByEmail(String email);

    // simple finder – do NOT annotate this with @EntityGraph that mentions "laundries"
    List<User> findByRole(UserRole role);

    // if/when you need the link side eagerly:
    @Query("""
           select distinct u
           from User u
           join u.userLaundries ul
           join ul.laundry l
           where l.owner.email = :laundryEmail
           """)
    List<User> findAllByLaundryOwnerEmail(@Param("laundryEmail") String laundryEmail);

    @Query("""
           select distinct u
           from User u
           join u.userLaundries ul
           join ul.laundry l
           where l.owner.email = :laundryEmail
             and ul.relationRole = :role
           """)
    List<User> findAllByLaundryOwnerEmailAndRole(@Param("laundryEmail") String laundryEmail,
                                                 @Param("role") UserLaundryRole role);
}
