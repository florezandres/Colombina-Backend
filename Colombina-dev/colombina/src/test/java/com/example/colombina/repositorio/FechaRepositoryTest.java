package com.example.colombina.repositorio;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.Fecha;

@SpringBootTest
public class FechaRepositoryTest {

    @Autowired
    private FechaRepository repository;

    @Test
    public void testFindByDia() {
        List<Fecha> fechas = repository.findByDia(15);
        assertNotNull(fechas);
    }

    @Test
    public void testFindByMes() {
        List<Fecha> fechas = repository.findByMes(10);
        assertNotNull(fechas);
    }

    @Test
    public void testFindByAño() {
        List<Fecha> fechas = repository.findByAño(2024);
        assertNotNull(fechas);
    }

    @Test
    public void testFindByDiaAndMesAndAño() {
        Optional<Fecha> fecha = repository.findByDiaAndMesAndAño(15, 10, 2024);
        assertTrue(fecha.isPresent());
    }
}