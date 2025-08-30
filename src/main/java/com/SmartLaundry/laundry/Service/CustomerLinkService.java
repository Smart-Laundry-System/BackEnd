package com.SmartLaundry.laundry.Service;

import com.SmartLaundry.laundry.API.Mappers;
import com.SmartLaundry.laundry.Entity.Dto.LaundryDTO;
import com.SmartLaundry.laundry.Entity.Dto.UserDTO;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.Laundry.UserLaundryRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CustomerLinkService {

    private final LaundryRepository laundryRepository;
    private final UserRepository userRepository;
    private final UserLaundryRepository userLaundryRepository;
    private final Mappers mappers;

    public CustomerLinkService(
            LaundryRepository laundryRepository,
            UserRepository userRepository,
            UserLaundryRepository userLaundryRepository,
            Mappers mappers
    ) {
        this.laundryRepository = laundryRepository;
        this.userRepository = userRepository;
        this.userLaundryRepository = userLaundryRepository;
        this.mappers = mappers;
    }

    @Transactional
    public String addCustomer(String customerEmail, String laundryOwnerEmail) {

        Laundry laundry = laundryRepository.findFirstByOwner_Email(laundryOwnerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Laundry(owner=" + laundryOwnerEmail + ") not found"));

        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("User(email=" + customerEmail + ") not found"));

        var existing = userLaundryRepository.findByUser_IdAndLaundry_Id(user.getId(), laundry.getId());
        if (existing.isPresent()) {
            UserLaundry link = existing.get();
            if (link.getRelationRole() != UserLaundryRole.CUSTOMER) {
                link.setRelationRole(UserLaundryRole.CUSTOMER);
                userLaundryRepository.save(link);
                return "Existing link updated to CUSTOMER.";
            }
            return "Customer already linked to this laundry.";
        }

        UserLaundry link = new UserLaundry();
        link.setUser(user);
        link.setLaundry(laundry);
        link.setRelationRole(UserLaundryRole.CUSTOMER);
        link.setLinkedAt(Instant.now());

        try {
            userLaundryRepository.saveAndFlush(link);
        } catch (DataIntegrityViolationException e) {
            // Unique constraint safety net (in case of race)
            return "Customer already linked to this laundry.";
        }

        // Keep both sides in sync in memory (optional)
        laundry.getUserLaundries().add(link);
        user.getUserLaundries().add(link);

        return "Customer linked successfully.";
    }

    @Transactional(readOnly = true)
    public UserDTO getUserAsDTOByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User(email=" + email + ") not found"));
        return mappers.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<LaundryDTO> getAllLaundriesAsDTO() {
        return laundryRepository.findAll().stream()
                .map(mappers::toLaundryDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> retriveAllUsers() {
        return userRepository.findByRole(UserRole.CUSTOMER).stream()
                .map(mappers::toUserDTO)
                .toList();
    }

    public List<UserDTO> retriveUser(String laundryEmail) {
        return userRepository.findAllByLaundryOwnerEmailAndRole(laundryEmail,UserLaundryRole.CUSTOMER)
                .stream()
                .map(mappers::toUserDTO)
                .toList();
    }
}
