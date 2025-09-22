package com.SmartLaundry.laundry.Service.Complain;

import com.SmartLaundry.laundry.Entity.Complain.Complain;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Repository.Complain.ComplainRepository;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            Optional<Laundry> laundry = laundryRepository.findFirstByOwner_Email(complain.getLaundryEmail());
            Optional<User> user = customerRepository.findByEmail(complain.getCustomerEmail());
            if(laundry.isPresent()&&user.isPresent()) {
                repository.save(complain);
                return "Complain added successfully";
            } else {
                return "Couldn't find the user and the laundry";
            }
        } catch (Exception e){
            return "Error on submiting complain"+e.getMessage();
        }
    }
}
