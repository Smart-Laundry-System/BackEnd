package com.SmartLaundry.laundry.API;

import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryDTO;
import com.SmartLaundry.laundry.Entity.Dto.Laundry.LaundryLite;
import com.SmartLaundry.laundry.Entity.Dto.NotificationsCom.NotificationDTO;
import com.SmartLaundry.laundry.Entity.Dto.Order.OrderDto;
import com.SmartLaundry.laundry.Entity.Dto.Services.ServiceLite;
import com.SmartLaundry.laundry.Entity.Dto.User.CustomerLite;
import com.SmartLaundry.laundry.Entity.Dto.User.UserDTO;
import com.SmartLaundry.laundry.Entity.Dto.User.UserMini;
import com.SmartLaundry.laundry.Entity.Laundry.Laundry;
import com.SmartLaundry.laundry.Entity.Laundry.Services;
import com.SmartLaundry.laundry.Entity.Notification.Notifications;
import com.SmartLaundry.laundry.Entity.Order.CustomerOrder;
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

    // NEW: Service -> ServiceLite
    public ServiceLite toServiceLite(Services s) {
        return new ServiceLite(s.getId(), s.getTitle(), s.getCategory(), s.getPrice());
    }

    public LaundryDTO toLaundryDTO(Laundry l) {
        List<CustomerLite> customers = l.getUserLaundries().stream()
                .filter(ul -> ul.getRelationRole() == UserLaundryRole.CUSTOMER)
                .map(UserLaundry::getUser)
                .map(this::toCustomerLite)
                .distinct()
                .toList();

        // map entities to ServiceLite (no back reference)
        List<ServiceLite> services = l.getServices().stream()
                .map(this::toServiceLite)
                .toList();

        return new LaundryDTO(
                l.getId(), l.getName(), l.getPhone(), l.getAddress(),
                services, l.getAvailableItems(), l.getOtherItems(), l.getLaundryImg(),
                customers
        );
    }

    public OrderDto toOrderDTO(CustomerOrder order) {
        String customerName = null;
        String customerPhone = null;

        if (order.getUsers() != null) {
            customerName = order.getUsers().getName();
            customerPhone = order.getUsers().getPhone();
        }

        return new OrderDto(
                order.getId(),
                order.getServiceIds(),
                order.getCustomerEmail(),
                order.getLaundryEmail(),
                order.getLaundryName(),
                order.getCustomerAddress(),
                order.getLaundryAddress(),
                order.getTotPrice(),
                order.getLaundryImg(),
                order.getEstimatedDate(),
                order.getRequestDate(),
                order.getStatus(),
                customerName,
                customerPhone,
                order.getAboutLaundry()
        );
    }

    public NotificationDTO toNotificationDTO(Notifications n) {
        return new NotificationDTO(
                n.getId(),
                n.getLaundryName(),
                n.getLaundryEmail(),
                n.getCustomerEmail(),
                n.getSubject(),
                n.getMessage(),
                n.getLaundryImg(),
                n.getStatus() != null ? n.getStatus().name() : null,
                n.getDate() != null ? n.getDate().toString() : null,
                n.getTime() != null ? n.getTime().toString() : null
        );
    }

    // If you ever need a compact user inside a notification detail:
    public UserMini toUserMini(User u) {
        return u == null ? null : new UserMini(u.getId(), u.getEmail(), u.getName());
    }
}
