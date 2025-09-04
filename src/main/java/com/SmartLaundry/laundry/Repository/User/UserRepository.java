package com.SmartLaundry.laundry.Repository.User;

import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);


    @Query("""
  select distinct u
  from User u
  join u.userLaundries ul
  join ul.laundry l
  where lower(l.owner.email) = lower(:laundryEmail)
    and ul.relationRole = :role
""")
    List<User> findAllByLaundryEmailOrOwnerEmailAndRole(
            @Param("laundryEmail") String laundryEmail,
            @Param("role") UserLaundryRole role);

    @Query("""
           select distinct u
           from User u
           join u.orders o
           where lower(o.laundryEmail) = lower(:laundryEmail)
           """)
    List<User> findAllCustomersByOrderLaundryEmail(@Param("laundryEmail") String laundryEmail);
}
