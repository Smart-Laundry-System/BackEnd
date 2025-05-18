package com.SmartLaundry.laundry.Entity.Complain;


import com.SmartLaundry.laundry.Entity.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "com_id")
    private Long id;

    @Column(nullable = false)
    private String laundry_email;

    @Column(nullable = false)
    private String customer_email;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Long order_id;

    @Column(nullable = false)
    private Long customer_id;

    @Override
    public String toString() {
        return "Complain{" +
                "id=" + id +
                ", laundry_email='" + laundry_email + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", message='" + message + '\'' +
                ", order_id=" + order_id +
                ", customer_id=" + customer_id +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

