package com.example.colombina.servicio;

import com.example.colombina.entidad.Fecha;
import com.example.colombina.repositorio.FechaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FechaService {

    private final FechaRepository fechaRepository;

    @Autowired
    public FechaService(FechaRepository fechaRepository) {
        this.fechaRepository = fechaRepository;
    }


    public Fecha saveFecha(Fecha fecha) {
        return fechaRepository.save(fecha);
    }


    public Optional<Fecha> getFechaById(Long id) {
        return fechaRepository.findById(id);
    }


    public void deleteFechaById(Long id) {
        fechaRepository.deleteById(id);
    }


    public List<Fecha> getAllFechas() {
        return fechaRepository.findAll();
    }
}