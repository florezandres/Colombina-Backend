package com.example.colombina.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.DTOs.LoginRequestDTO;
import com.example.colombina.services.AutenticacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(
            autenticacionService.login(body.getUsername(), body.getPassword())
        );
    }
    
}
