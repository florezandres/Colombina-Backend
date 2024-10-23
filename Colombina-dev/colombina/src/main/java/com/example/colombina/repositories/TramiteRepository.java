package com.example.colombina.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import org.springframework.stereotype.Repository;

import com.example.colombina.DTOs.EstadisticasDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Tramite;

@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    Optional<Tramite> findByNumeroRadicado(String numeroRadicado);
    List<Tramite> findByEstado(Tramite.EstadoTramite estado);

    //List<Tramite> findByFechaRadicacionAndEstado(Date fechaInicio, Date fechaFin, String tipoTramite, Tramite.EstadoTramite estado);
      // Consulta para contar trámites por mes y tipo (nacional o internacional)
      @Query("SELECT new com.example.colombina.DTOs.EstadisticasDTO(MONTH(t.fechaRadicacion), COUNT(t)) " +
      "FROM Tramite t WHERE t.tipoTramite = :tipo GROUP BY MONTH(t.fechaRadicacion)")
    List<EstadisticasDTO> contarTramitesPorMesYTipo(@Param("tipo") String tipo);


    // Consulta para contar documentos devueltos por tipo
    @Query("SELECT new com.example.colombina.DTOs.EstadisticasDTO(d.tipo, COUNT(d)) " +
       "FROM Documento d GROUP BY d.tipo")
    List<EstadisticasDTO> contarDocumentosDevueltosPorTipo();



     // Contar trámites por semana
     @Query("SELECT FUNCTION('WEEK', t.fechaRadicacion) AS semana, COUNT(t) AS cantidad " +
     "FROM Tramite t WHERE t.tipoTramite = :tipo GROUP BY FUNCTION('WEEK', t.fechaRadicacion)")
    List<EstadisticasDTO> contarTramitesPorSemanaYTipo(String tipo);

    // Contar trámites por año
    @Query("SELECT YEAR(t.fechaRadicacion) AS ano, COUNT(t) AS cantidad " +
        "FROM Tramite t WHERE t.tipoTramite = :tipo GROUP BY YEAR(t.fechaRadicacion)")
    List<EstadisticasDTO> contarTramitesPorAnoYTipo(String tipo);
    
    




    //filtros para tramites
    @Query("SELECT new com.example.colombina.DTOs.EstadisticasDTO(t.tipoTramite, COUNT(t)) " +
       "FROM Tramite t " +
       "WHERE (:tipo IS NULL OR t.tipoTramite = :tipo) " +
       "AND (:pais IS NULL OR t.entidadSanitaria.pais = :pais) " +
       "AND (:fechaInicio IS NULL OR t.fechaRadicacion >= :fechaInicio) " +
       "AND (:fechaFin IS NULL OR t.fechaRadicacion <= :fechaFin) " +
       "GROUP BY t.tipoTramite")
    List<EstadisticasDTO> filtrarTramites(@Param("tipo") String tipo, 
                                      @Param("pais") String pais, 
                                      @Param("fechaInicio") String fechaInicio, 
                                      @Param("fechaFin") String fechaFin);

    //

}
