package com.example.colombina.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.colombina.services.EstadisticasService;

import java.util.Map;

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

}


