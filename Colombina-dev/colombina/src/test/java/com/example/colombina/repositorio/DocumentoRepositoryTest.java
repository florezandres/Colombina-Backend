package com.example.colombina.repositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.Documento;
import com.example.colombina.entidad.Tramite;

@SpringBootTest
public class DocumentoRepositoryTest {

    @Autowired
    private DocumentoRepository repository;

    @Test
    public void testFindByTramite() {
        Tramite tramite = new Tramite(); // Suponiendo que ya existe un trámite
        List<Documento> documentos = repository.findByTramite(tramite);
        assertNotNull(documentos);
    }

    @Test
    public void testFindByInformacionContaining() {
        List<Documento> documentos = repository.findByInformacionContaining("keyword");
        assertNotNull(documentos);
    }
}