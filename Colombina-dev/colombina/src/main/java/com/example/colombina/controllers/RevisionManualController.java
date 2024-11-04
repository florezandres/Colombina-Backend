package com.example.colombina.controllers;

import com.example.colombina.DTOs.RevisionManualDTO;
import com.example.colombina.services.RevisionManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tramites/{tramiteId}/revision-manual")
public class RevisionManualController {

    @Autowired
    private RevisionManualService revisionManualService;

    @GetMapping
    public RevisionManualDTO obtenerRevision(@PathVariable Long tramiteId) {
        return revisionManualService.obtenerRevisionPorTramite(tramiteId);
    }

    @PostMapping
    public RevisionManualDTO realizarRevision(@PathVariable Long tramiteId, @RequestBody String comentarios) {
        return revisionManualService.realizarRevision(tramiteId, comentarios);
    }
}
