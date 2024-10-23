package com.example.colombina.repositorio;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.colombina.entidad.DocumentoTecnico;

@SpringBootTest
public class DocumentoTecnicoRepositoryTest {

    @Autowired
    private DocumentoTecnicoRepository repository;

    @Test
    public void testFindByTipoDocumento() {
        List<DocumentoTecnico> documentos = repository.findByTipoDocumento("Tipo1");
        assertNotNull(documentos);
    }

    @Test
    public void testFindByFechaAdjuncion() {
        Date fecha = new Date();
        List<DocumentoTecnico> documentos = repository.findByFechaAdjuncion(fecha);
        assertNotNull(documentos);
    }

    @Test
    public void testFindByEstadoDocumento() {
        List<DocumentoTecnico> documentos = repository.findByEstadoDocumento(true);
        assertNotNull(documentos);
    }
}