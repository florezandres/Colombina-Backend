package com.example.colombina.repositorio;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.ExpedienteRegulatorio;

@SpringBootTest
public class ExpedienteRegulatorioRepositoryTest {

    @Autowired
    private ExpedienteRegulatorioRepository repository;

    @Test
    public void testFindByNumeroExpediente() {
        List<ExpedienteRegulatorio> expedientes = repository.findByNumeroExpediente(123);
        assertNotNull(expedientes);
    }

    @Test
    public void testFindByEstadoExpediente() {
        List<ExpedienteRegulatorio> expedientes = repository.findByEstadoExpediente(true);
        assertNotNull(expedientes);
    }

    @Test
    public void testFindByFechaCreacion() {
        Date fecha = new Date();
        List<ExpedienteRegulatorio> expedientes = repository.findByFechaCreacion(fecha);
        assertNotNull(expedientes);
    }

    @Test
    public void testFindByDescripcionExpedienteContaining() {
        List<ExpedienteRegulatorio> expedientes = repository.findByDescripcionExpedienteContaining("keyword");
        assertNotNull(expedientes);
    }
}