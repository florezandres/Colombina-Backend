import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository repository;

    @Test
    public void testFindByMensaje() {
        Optional<Notificacion> notificacion = repository.findByMensaje("Mensaje de prueba");
        assertTrue(notificacion.isPresent());
    }

    @Test
    public void testFindByTramite() {
        Tramite tramite = new Tramite(); // Suponiendo que ya existe un trámite
        Optional<Notificacion> notificacion = repository.findByTramite(tramite);
        assertTrue(notificacion.isPresent());
    }
}