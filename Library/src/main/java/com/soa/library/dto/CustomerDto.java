package com.soa.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CustomerDto {
    @Size(min = 3, max = 15, message = "Le nom doit être entre 3-15 caractères")
    private String firstName;

    @Size(min = 3, max = 15, message = "Le prénom doit être entre 3-15 caractères")
    private String lastName;

    private String username;
    @Size(min = 9, max = 14, message = "Le numero de phone doit être entre 10-13 caractères")
    private String phoneNumber;
    private String country;
    private String address;
    private String city;

    @Size(min = 5, max = 20, message = "Le mot de passe doit avoir entre 5-20 caractères")
    private String password;

    private String repeatPassword;
}
