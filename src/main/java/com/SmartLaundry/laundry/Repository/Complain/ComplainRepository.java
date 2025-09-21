package com.SmartLaundry.laundry.Repository.Complain;

import com.SmartLaundry.laundry.Entity.Complain.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
}
