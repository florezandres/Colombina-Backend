import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class AsuntoRegulatorioRepositoryTest {

    @Autowired
    private AsuntoRegulatorioRepository repository;

    @Test
    public void testFindByResponsable() {
        List<AsuntoRegulatorio> asuntos = repository.findByResponsable("Responsable1");
        assertNotNull(asuntos);
    }

    @Test
    public void testFindByEstadoAsunto() {
        List<AsuntoRegulatorio> asuntos = repository.findByEstadoAsunto(true);
        assertNotNull(asuntos);
    }

    @Test
    public void testFindByFechaAsunto() {
        Date fecha = new Date();
        List<AsuntoRegulatorio> asuntos = repository.findByFechaAsunto(fecha);
        assertNotNull(asuntos);
    }
}