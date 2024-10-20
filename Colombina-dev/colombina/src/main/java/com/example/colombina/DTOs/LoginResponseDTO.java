package com.example.colombina.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    
    private String accessToken;
    private String username;
    private String role;

}
