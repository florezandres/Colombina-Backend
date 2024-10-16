package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.PagoPSE;

public interface PagoPSERepository extends JpaRepository<PagoPSE, Long>{

    // Buscar pagos por monto exacto
    List<PagoPSE> findByMonto(float monto);

    // Buscar pagos cuyo monto sea mayor o igual a un valor dado
    List<PagoPSE> findByMontoGreaterThanEqual(float monto);

    // Buscar pagos cuyo monto sea menor o igual a un valor dado
    List<PagoPSE> findByMontoLessThanEqual(float monto);

    // Buscar pagos por fecha de pago exacta
    List<PagoPSE> findByFechaDePago(Date fechaDePago);

    // Buscar pagos entre un rango de fechas
    List<PagoPSE> findByFechaDePagoBetween(Date startDate, Date endDate);

    // Buscar pagos por método de pago
    List<PagoPSE> findByMetodoDePago(String metodoDePago);

    // Buscar pagos por referencia de pago exacta
    List<PagoPSE> findByReferenciaDePago(String referenciaDePago);

    // Buscar pagos que contengan una palabra clave en la referencia de pago
    List<PagoPSE> findByReferenciaDePagoContaining(String keyword);
}
