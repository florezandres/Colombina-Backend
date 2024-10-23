package com.example.colombina.repositorio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.SolicitudDEI;
import com.example.colombina.repositorio.SolicitudDEIRepository;

import java.util.List;

@SpringBootTest
public class SolicitudDEIRepositoryTest {

    @Autowired
    private SolicitudDEIRepository repository;

    @Test
    public void testFindByNombreSolicitante() {
        List<SolicitudDEI> solicitudes = repository.findByNombreSolicitante("Solicitante1");
        assertNotNull(solicitudes);
    }

    @Test
    public void testFindByTipoDeProducto() {
        List<SolicitudDEI> solicitudes = repository.findByTipoDeProducto("Producto1");
        assertNotNull(solicitudes);
    }

    @Test
    public void testFindByEstadoSolicitud() {
        List<SolicitudDEI> solicitudes = repository.findByEstadoSolicitud(true);
        assertNotNull(solicitudes);
    }
}