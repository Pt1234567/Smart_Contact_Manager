package com.smartContactManager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {

    @NotNull(message = "Name cannot be null")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Email cannot be null")
    @NotBlank
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number")
    private String phoneNumber;

    private String about;


}
