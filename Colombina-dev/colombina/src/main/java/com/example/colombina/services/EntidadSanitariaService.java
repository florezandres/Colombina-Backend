package com.example.colombina.services;

import com.example.colombina.DTOs.EntidadSanitariaDTO;
import com.example.colombina.model.EntidadSanitaria;
import com.example.colombina.repositories.EntidadSanitariaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;

@Service
public class EntidadSanitariaService {

    @Autowired
    EntidadSanitariaRepository entidadSanitariaRepository;

    @Autowired
    ModelMapper modelMapper;

   public List<EntidadSanitariaDTO> findAll() {
        List<EntidadSanitaria> antes = entidadSanitariaRepository.findAll();
        Type listType = new TypeToken<List<EntidadSanitariaDTO>>() {}.getType();
        List<EntidadSanitariaDTO> listaEntidades = modelMapper.map(antes, listType);
        return listaEntidades;
    }

    public EntidadSanitariaDTO findById(Long id) {
        EntidadSanitaria entidad = entidadSanitariaRepository.findById(id).orElse(null);
        EntidadSanitariaDTO entidadDTO = modelMapper.map(entidad, EntidadSanitariaDTO.class);
        return entidadDTO;
    }
}
