package com.SmartLaundry.laundry.API;

import com.SmartLaundry.laundry.Entity.Dto.CustomerLite;
import com.SmartLaundry.laundry.Entity.Dto.LaundryDTO;
import com.SmartLaundry.laundry.Entity.Dto.LaundryLite;
import com.SmartLaundry.laundry.Entity.Dto.UserDTO;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundry;
import com.SmartLaundry.laundry.Entity.UserLaundry.UserLaundryRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mappers {

    public LaundryLite toLaundryLite(Laundry l) {
        return new LaundryLite(l.getId(), l.getName(), l.getPhone(), l.getAddress());
    }

    public CustomerLite toCustomerLite(User u) {
        return new CustomerLite(u.getId(), u.getEmail(), u.getName(), u.getPhone());
    }

    public UserDTO toUserDTO(User u) {
        List<LaundryLite> laundries = u.getUserLaundries().stream()
                .map(UserLaundry::getLaundry)
                .map(this::toLaundryLite)
                .distinct()
                .toList();

        return new UserDTO(
                u.getId(), u.getEmail(), u.getName(),
                u.getRole() != null ? u.getRole().name() : null,
                u.getPhone(), u.getAddress(),
                laundries
        );
    }

    public LaundryDTO toLaundryDTO(Laundry l) {
        List<CustomerLite> customers = l.getUserLaundries().stream()
                .filter(ul -> ul.getRelationRole() == UserLaundryRole.CUSTOMER)
                .map(UserLaundry::getUser)
                .map(this::toCustomerLite)
                .distinct()
                .toList();

        return new LaundryDTO(
                l.getId(), l.getName(), l.getPhone(), l.getAddress(), customers
        );
    }
}
