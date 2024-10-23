import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void testFindByCredencial() {
        List<Usuario> usuarios = repository.findByCredencial("usuario1");
        assertNotNull(usuarios);
    }

    @Test
    public void testFindByRol() {
        List<Usuario> usuarios = repository.findByRol("admin");
        assertNotNull(usuarios);
    }

    @Test
    public void testFindByCorreo() {
        List<Usuario> usuarios = repository.findByCorreo("correo@example.com");
        assertNotNull(usuarios);
    }
}