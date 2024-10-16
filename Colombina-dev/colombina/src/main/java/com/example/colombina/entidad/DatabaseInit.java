package com.example.colombina.entidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.colombina.repositorio.UsuarioRepository;

import jakarta.transaction.Transactional;

//Implementar clases de repositorio para inicializar la base de datos



//...

@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

        @Autowired
        UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Implementacion del repositorio

        //Agrgar usuarios en base de datos
        

    }
    
}
