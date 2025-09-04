package com.SmartLaundry.laundry.Entity.Dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EmailSender {
    @Email
    @NotNull
    private String email;

    @Override
    public String toString() {
        return "EmailSender{" +
                "email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
