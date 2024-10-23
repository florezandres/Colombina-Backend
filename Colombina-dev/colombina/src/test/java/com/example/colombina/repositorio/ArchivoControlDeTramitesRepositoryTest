import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class ArchivoControlDeTramitesRepositoryTest {

    @Autowired
    private ArchivoControlDeTramitesRepository repository;

    @Test
    public void testFindByEstadoTramite() {
        List<ArchivoControlDeTramites> archivos = repository.findByEstadoTramite(true);
        assertNotNull(archivos);
    }

    @Test
    public void testFindByFechaCreacion() {
        Date fecha = new Date();
        List<ArchivoControlDeTramites> archivos = repository.findByFechaCreacion(fecha);
        assertNotNull(archivos);
    }

    @Test
    public void testFindByHistorialDeModificacionesContaining() {
        List<ArchivoControlDeTramites> archivos = repository.findByHistorialDeModificacionesContaining("keyword");
        assertNotNull(archivos);
    }
}
