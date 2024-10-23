package com.example.colombina.repositorio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.PagoPSE;
import com.example.colombina.repositorio.PagoPSERepository;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class PagoPSERepositoryTest {

    @Autowired
    private PagoPSERepository repository;

    @Test
    public void testFindByMonto() {
        List<PagoPSE> pagos = repository.findByMonto(100.0);
        assertNotNull(pagos);
    }

    @Test
    public void testFindByFechaDePago() {
        Date fecha = new Date();
        List<PagoPSE> pagos = repository.findByFechaDePago(fecha);
        assertNotNull(pagos);
    }

    @Test
    public void testFindByMetodoDePago() {
        List<PagoPSE> pagos = repository.findByMetodoDePago("Tarjeta de Crédito");
        assertNotNull(pagos);
    }
}