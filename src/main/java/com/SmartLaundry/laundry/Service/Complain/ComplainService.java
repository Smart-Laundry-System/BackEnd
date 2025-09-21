package com.SmartLaundry.laundry.Service.Complain;

import com.SmartLaundry.laundry.Entity.Complain.Complain;
import com.SmartLaundry.laundry.Repository.Complain.ComplainRepository;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplainService {
    private final ComplainRepository repository;

    private final LaundryRepository laundryRepository;
    private final UserRepository customerRepository;

    public ComplainService(ComplainRepository repository, LaundryRepository laundryRepository, UserRepository customerRepository) {
        this.repository = repository;
        this.laundryRepository = laundryRepository;
        this.customerRepository = customerRepository;
    }

    public String addComplain(Complain complain) {
        try {
            if(laundryRepository.findByLaundryEmailComplain(complain.getLaundry_email())
//                    &&
//                customerRepository.findByCustomerIdComplain()
            ) {
                repository.save(complain);
                return "Complain added successfully";
            } else {
                return "";
            }
        } catch (Exception e){
            return "Error on submiting complain"+e.getMessage();
        }
    }
}
