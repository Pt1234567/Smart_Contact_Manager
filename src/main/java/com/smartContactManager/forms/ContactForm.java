package com.smartContactManager.forms;

import org.springframework.web.multipart.MultipartFile;

import com.smartContactManager.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

  @NotNull(message = "Name cannot be null")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @NotNull(message = "Email cannot be null")
    @NotBlank
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number")
    private String phoneNumber;

  @NotBlank(message="Address is required")
  private String address;

  @ValidFile(message = "Invalid File")
  private MultipartFile contactImage;

  private String picture;
  private boolean favourite;
  private String githubLink;
  private String linkedLink;


}
