package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.PagoPSE;
import com.example.colombina.repositorio.PagoPSERepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PagoPSEService {

    @Autowired
    private PagoPSERepository pagoPSERepository;

    // Crear un nuevo pago PSE
    public PagoPSE crearPagoPSE(PagoPSE pagoPSE) {
        return pagoPSERepository.save(pagoPSE);
    }

    // Obtener todos los pagos PSE
    public List<PagoPSE> obtenerTodosLosPagosPSE() {
        return pagoPSERepository.findAll();
    }

    // Obtener un pago PSE por ID
    public Optional<PagoPSE> obtenerPagoPSEPorId(Long id) {
        return pagoPSERepository.findById(id);
    }

    // Actualizar un pago PSE
    public PagoPSE actualizarPagoPSE(Long id, PagoPSE pagoActualizado) {
        Optional<PagoPSE> pagoExistente = pagoPSERepository.findById(id);
        
        if (pagoExistente.isPresent()) {
            // Asigna el ID al objeto que se va a actualizar
            pagoActualizado.setIdTramite(id);
            return pagoPSERepository.save(pagoActualizado);
        } else {
            // Lanza una excepción o maneja el caso en que el ID no existe
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }
    }

    // Eliminar un pago PSE
    public void eliminarPagoPSE(Long id) {
        pagoPSERepository.deleteById(id);
    }

    // Buscar pagos por monto exacto
    public List<PagoPSE> buscarPorMonto(float monto) {
        return pagoPSERepository.findByMonto(monto);
    }

    // Buscar pagos cuyo monto sea mayor o igual a un valor dado
    public List<PagoPSE> buscarPorMontoMayorOIgual(float monto) {
        return pagoPSERepository.findByMontoGreaterThanEqual(monto);
    }

    // Buscar pagos cuyo monto sea menor o igual a un valor dado
    public List<PagoPSE> buscarPorMontoMenorOIgual(float monto) {
        return pagoPSERepository.findByMontoLessThanEqual(monto);
    }

    // Buscar pagos por fecha de pago exacta
    public List<PagoPSE> buscarPorFechaDePago(Date fechaDePago) {
        return pagoPSERepository.findByFechaDePago(fechaDePago);
    }

    // Buscar pagos entre un rango de fechas
    public List<PagoPSE> buscarPorFechaDePagoEntre(Date startDate, Date endDate) {
        return pagoPSERepository.findByFechaDePagoBetween(startDate, endDate);
    }

    // Buscar pagos por método de pago
    public List<PagoPSE> buscarPorMetodoDePago(String metodoDePago) {
        return pagoPSERepository.findByMetodoDePago(metodoDePago);
    }

    // Buscar pagos por referencia de pago exacta
    public List<PagoPSE> buscarPorReferenciaDePago(String referenciaDePago) {
        return pagoPSERepository.findByReferenciaDePago(referenciaDePago);
    }

    // Buscar pagos que contengan una palabra clave en la referencia de pago
    public List<PagoPSE> buscarPorReferenciaDePagoConteniendo(String keyword) {
        return pagoPSERepository.findByReferenciaDePagoContaining(keyword);
    }
}
