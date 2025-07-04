package com.SmartLaundry.laundry.Dto.User;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
import lombok.*;

//@Data
//@Getter
//@Setter
//@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
//    @Id
//    private Long id;
    private String username;
    private String password;

//    public Long getId() {
//        return id;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
//                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
