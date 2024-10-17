package com.example.colombina.entidad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an authentication token.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token;
}