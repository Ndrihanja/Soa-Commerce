package com.soa.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AdminDto {
    @Size(min = 4, max = 20, message = "Le nom est invalide! Veuillez remplir les critères (4-20 caractères)")
    private String firstname;
    @Size(min = 4, max = 20, message = "Le prénom est invalide! Veuillez remplir les critères (4-20 caractères)")
    private  String lastname;
    private  String username;
    @Size(min = 5, max = 20, message = "Le mot de passe est invalide! Veuillez remplir les critères (5-20 caractères)")
    private  String password;

    private  String repeatPassword;
}
