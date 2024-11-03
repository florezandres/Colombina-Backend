package com.example.colombina.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.colombina.services.EstadisticasService;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/estadisticas/tramites")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/chartData")
    public Map<String, Object> getChartData(
            @RequestParam String queGraficar,
            @RequestParam String enFuncionDe) {

        return estadisticasService.getChartData(queGraficar, enFuncionDe);
    }
    @GetMapping("/totales")
    public Map<String, Long> getTotales() {
        return estadisticasService.getTotales();
    }

    @GetMapping("/tramitesActivosYCerradosPorTipo")
    public Map<String, Object> getTramitesActivosYCerradosPorTipo() {
        return estadisticasService.getTramitesActivosYCerradosPorTipo();
    }

 
    @GetMapping("/actual")
    public Map<String, Object> getTramitesTotalesDelAnoActual() {
        int currentYear = Year.now().getValue();
        return estadisticasService.getTramitesTotalesDelAnoActual(currentYear);
    }



    @GetMapping("/tramitesNacionalesActivosYCerradosPorProducto")
    public Map<String, Object> getTramitesNacionalesActivosYCerradosPorProducto() {
        return estadisticasService.getTramitesNacionalesActivosYCerradosPorProducto();
    }

    @GetMapping("/tramitesInternacionalesActivosYCerradosPorProducto")
    public Map<String, Object> getTramitesInternacionalesActivosYCerradosPorProducto() {
        return estadisticasService.getTramitesInternacionalesActivosYCerradosPorProducto();
    }


    @GetMapping("/registrosPorVencer")
    public ResponseEntity<Map<String, Object>> getRegistrosPorVencer() {
        return ResponseEntity.ok(estadisticasService.getRegistrosPorVencer());
    }



}


