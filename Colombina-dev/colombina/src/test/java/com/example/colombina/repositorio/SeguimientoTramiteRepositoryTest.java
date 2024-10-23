package com.example.colombina.repositorio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.SeguimientoTramite;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.repositorio.SeguimientoTramiteRepository;

import java.util.List;

@SpringBootTest
public class SeguimientoTramiteRepositoryTest {

    @Autowired
    private SeguimientoTramiteRepository repository;

    @Test
    public void testFindByEstadoActual() {
        List<SeguimientoTramite> seguimientos = repository.findByEstadoActual("En Proceso");
        assertNotNull(seguimientos);
    }

    @Test
    public void testFindByObservacionesContaining() {
        List<SeguimientoTramite> seguimientos = repository.findByObservacionesContaining("observación");
        assertNotNull(seguimientos);
    }

    @Test
    public void testFindByTramite() {
        Tramite tramite = new Tramite(); // Suponiendo que ya existe un trámite
        List<SeguimientoTramite> seguimientos = repository.findByTramite(tramite);
        assertNotNull(seguimientos);
    }
}