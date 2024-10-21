package com.example.colombina.controllers;

import com.example.colombina.services.ProgresoService;
import com.example.colombina.services.TramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entidadSanitaria")
public class EntidadSanitariaController {

    @Autowired
    ProgresoService progresoService;

    @Autowired
    TramiteService tramiteService;




}
