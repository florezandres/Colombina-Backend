package com.example.colombina.repositorio;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.EntidadSanitariaINVIMA;

@SpringBootTest
public class EntidadSanitariaINVIMARepositoryTest {

    @Autowired
    private EntidadSanitariaINVIMARepository repository;

    @Test
    public void testFindByResultadoTramite() {
        List<EntidadSanitariaINVIMA> entidades = repository.findByResultadoTramite(true);
        assertNotNull(entidades);
    }

    @Test
    public void testFindByFechaRadicacion() {
        Date fecha = new Date();
        List<EntidadSanitariaINVIMA> entidades = repository.findByFechaRadicacion(fecha);
        assertNotNull(entidades);
    }

    @Test
    public void testFindByComentariosContaining() {
        List<EntidadSanitariaINVIMA> entidades = repository.findByComentariosContaining("keyword");
        assertNotNull(entidades);
    }
}