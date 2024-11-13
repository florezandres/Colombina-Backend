package com.example.colombina.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.colombina.model.Solicitud;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
        List<Solicitud> findAllByOrderByIdDesc(Pageable pageable);

            // Consulta para obtener el ID del cliente a partir del ID de la solicitud
    @Query("SELECT s.solicitante.id FROM Solicitud s WHERE s.id = :solicitudId")
    Long findSolicitanteIdBySolicitudId(@Param("solicitudId") Long solicitudId);

    // Consulta para obtener el ID del trámite a partir del ID de la solicitud
    @Query("SELECT s.tramite.id FROM Solicitud s WHERE s.id = :solicitudId")
    Long findTramiteIdBySolicitudId(@Param("solicitudId") Long solicitudId);

        @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.tramite t LEFT JOIN FETCH s.solicitante u WHERE " +
                        "(:estado IS NULL OR t.estado = :estado) AND " +
                        "(:tipoTramite IS NULL OR t.tipoProducto = :tipoTramite) AND " +
                        "(:nacionalidad IS NULL OR t.tipoTramite = :nacionalidad) AND " +
                        "(cast(cast(:fechaInicio as text) as date) IS NULL OR s.fechaSolicitud >= cast(cast(:fechaInicio as text) as date)) AND " +
                        "(cast(cast(:fechaFin as text) as date) IS NULL OR t.fechaRadicacion <= cast(cast(:fechaFin as text) as date)) AND " +
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
                        "(cast(cast(:fechaInicio as text) as date) IS NULL OR s.fechaSolicitud >= cast(cast(:fechaInicio as text) as date)) AND " +
                        "(cast(cast(:fechaFin as text) as date) IS NULL OR t.fechaRadicacion <= cast(cast(:fechaFin as text) as date)) AND " +
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

        Solicitud findByTramiteId(Long id);
}
