package com.SmartLaundry.laundry.Service.User.Impl;
//
////import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
//import com.SmartLaundry.laundry.Entity.User.User;
//import com.SmartLaundry.laundry.Entity.User.UserPrinciple;
////import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
//import com.SmartLaundry.laundry.Repository.User.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
////@RequiredArgsConstructor
//public class UserService implements UserDetailsService {
//
////    private final LaundryRepository laundryRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository
////            , LaundryRepository laundryRepository
//    ) {
//        this.userRepository = userRepository;
////        this.laundryRepository = laundryRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<User> user = userRepository.findByEmail(username);
//        if(user == null){
//            System.out.println("User not found");
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return new UserPrinciple(user.get());
//    }
//
//    public List<User> retriveUsers() {
//        return userRepository.findAll();
//    }
//
//    public Object retriveDetails(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//
//        if (user.isPresent()){
//            return user;
//        } else {
//            return "can't find the email";
//        }
//    }
//
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        return userRepository.findByEmail(username)
////                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
////    }
//}
import com.SmartLaundry.laundry.Dto.Laundry.LaundryDetailsDto;
import com.SmartLaundry.laundry.Entity.Dto.UserDTO;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Roles.UserRole;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.User.UserPrinciple;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import com.SmartLaundry.laundry.Repository.Laundry.LaundryRepository;
import com.SmartLaundry.laundry.Repository.Laundry.UserLaundryRepository;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserLaundryRepository userLaundryRepository;
    private final UserRepository userRepository;
    private final LaundryRepository laundryRepository;

    public UserService(UserRepository userRepository, LaundryRepository laundryRepository, UserLaundryRepository userLaundryRepository) {
        this.userRepository = userRepository;
        this.laundryRepository = laundryRepository;
        this.userLaundryRepository = userLaundryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrinciple(u);
    }

    // used by GET /api/auth/details?email=...
    public LaundryDetailsDto retriveDetails(String email) {
        User u = userRepository.findByEmail(email).orElse(null);
        if (u == null) return new LaundryDetailsDto();

        Laundry l = laundryRepository.findFirstByOwner_Email(u.getEmail()).orElse(null);
        if (l == null) return new LaundryDetailsDto();

        LaundryDetailsDto dto = new LaundryDetailsDto();
        dto.setId(l.getId());
        dto.setName(l.getName());
        dto.setPhone(l.getPhone());
        dto.setAddress(l.getAddress());
        dto.setLaundryImg(l.getLaundryImg());
        dto.setAbout(l.getAbout());
        dto.setServices(l.getServices());
        dto.setAvailableItems(l.getAvailableItems());
        dto.setOtherItems(l.getOtherItems());

        // Optional: include related users if you’ve linked them via UserLaundry
        if (l.getUserLaundries() != null) {
            dto.setUsers(
                    l.getUserLaundries().stream()
                            .map(ul -> new LaundryDetailsDto.UserSummary(
                                    ul.getUser().getId(),
                                    ul.getUser().getName(),
                                    ul.getUser().getEmail(),
                                    ul.getUser().getPhone(),
                                    ul.getUser().getAddress(),
                                    ul.getRelationRole().name()
                            ))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }


    @Transactional
    public String addCustomer(String customerEmail, String laundryEmail) {

        Optional<Laundry> laundry = laundryRepository.findFirstByOwner_Email(laundryEmail);
        Optional<User> user = userRepository.findByEmail(customerEmail);
        if(laundry.isPresent() && user.isPresent()) {
            User user1 = user.get();
            Laundry laundry1 = laundry.get();
            Optional<UserLaundry> userLaundry = userLaundryRepository.findByUser_IdAndLaundry_Id(user1.getId(), laundry1.getId());
            if ((userLaundry.isPresent())){
//                UserLaundry link = userLaundry.get();
//                if (link.getRelationRole() == UserLaundryRole.CUSTOMER) {
                    return "Customer already linked to this laundry.";
//                }
//                link.setRelationRole(UserLaundryRole.CUSTOMER);
//                userLaundryRepository.save(link);
            } else {
                // Create new link
                UserLaundry link = new UserLaundry();
                link.setUser(user1);
                link.setLaundry(laundry1);
                link.setRelationRole(UserLaundryRole.CUSTOMER);
                link.setLinkedAt(Instant.now());
                // cascade from Laundry would also work, but saving the link is explicit:
                userLaundryRepository.save(link);
                // Keep the in-memory list updated (optional)
                laundry1.getUserLaundries().add(link);
            }
        }
        return "If customer new then added successfully";
    }
}