package com.example.colombina.repositories;

import com.example.colombina.model.Solicitud;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
        List<Solicitud> findAllByOrderByIdDesc(Pageable pageable);

        @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.tramite t LEFT JOIN FETCH s.solicitante u WHERE " +
                        "(:estado IS NULL OR t.estado = :estado) AND " +
                        "(:tipoTramite IS NULL OR t.tipoProducto = :tipoTramite) AND " +
                        "(:nacionalidad IS NULL OR t.tipoTramite = :nacionalidad) AND " +
                        "(cast(:fechaInicio as date) IS NULL OR s.fechaSolicitud >= cast(:fechaInicio as date)) AND " +
                        "(cast(:fechaFin as date) IS NULL OR t.fechaRadicacion <= cast(:fechaFin as date)) AND " +
                        "(:filtro IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
                        "LOWER(t.nombreProducto) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
                        "LOWER(t.numeroRadicado) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
                        "LOWER(t.nombreEtapa) LIKE LOWER(CONCAT('%', :filtro, '%'))) ORDER BY s.id DESC")
        List<Solicitud> findByFilters(Tramite.EstadoTramite estado, String tipoTramite,
                        Tramite.TipoTramite nacionalidad,
                        Date fechaInicio, Date fechaFin,
                        String filtro,
                        Pageable pageable);

        @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.tramite t LEFT JOIN FETCH s.solicitante u WHERE " +
                        "s.solicitante = :solicitante AND " +
                        "(:estado IS NULL OR t.estado = :estado) AND " +
                        "(:tipoTramite IS NULL OR t.tipoProducto = :tipoTramite) AND " +
                        "(:nacionalidad IS NULL OR t.tipoTramite = :nacionalidad) AND " +
                        "(cast(:fechaInicio as date) IS NULL OR s.fechaSolicitud >= cast(:fechaInicio as date)) AND " +
                        "(cast(:fechaFin as date) IS NULL OR t.fechaRadicacion <= cast(:fechaFin as date)) AND " +
                        "(:filtro IS NULL OR LOWER(t.nombreProducto) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
                        "LOWER(t.numeroRadicado) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
                        "LOWER(t.nombreEtapa) LIKE LOWER(CONCAT('%', :filtro, '%'))) ORDER BY s.id DESC")
        List<Solicitud> findByFiltersAndSolicitante(Usuario solicitante, Tramite.EstadoTramite estado,
                        String tipoTramite, Tramite.TipoTramite nacionalidad,
                        Date fechaInicio, Date fechaFin,
                        String filtro,
                        Pageable pageable);

        @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.tramite t LEFT JOIN FETCH s.solicitante u WHERE s.solicitante = :solicitante ORDER BY s.id DESC")
        List<Solicitud> findBySolicitanteWithTramite(Usuario solicitante, Pageable pageable);
}
