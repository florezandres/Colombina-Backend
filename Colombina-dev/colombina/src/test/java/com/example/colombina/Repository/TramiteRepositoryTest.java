import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TramiteRepositoryTest {

    @Autowired
    private TramiteRepository repository;

    @Test
    public void testFindByTipoTramite() {
        List<Tramite> tramites = repository.findByTipoTramite("Tipo1");
        assertNotNull(tramites);
    }

    @Test
    public void testFindByEstadoTramite() {
        List<Tramite> tramites = repository.findByEstadoTramite(true);
        assertNotNull(tramites);
    }

    @Test
    public void testFindByFechaRadicacion() {
        Date fecha = new Date();
        List<Tramite> tramites = repository.findByFechaRadicacion(fecha);
        assertNotNull(tramites);
    }
}