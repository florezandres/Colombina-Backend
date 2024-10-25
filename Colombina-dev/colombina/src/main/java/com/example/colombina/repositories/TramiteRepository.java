package com.example.colombina.repositories;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.example.colombina.DTOs.EstadisticasDTO;
import com.example.colombina.model.Tramite;
import java.util.Date;


@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
  Optional<Tramite> findByNumeroRadicado(String numeroRadicado);

  List<Tramite> findByEstado(Tramite.EstadoTramite estado);

  @NotNull
  List<Tramite> findAll();

  Optional<Tramite> findById(Long id);

  // List<Tramite> findByFechaRadicacionAndEstado(Date fechaInicio, Date fechaFin,
  // String tipoTramite, Tramite.EstadoTramite estado);
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

  // filtros para Estadisticas
  
  @Query("SELECT u.nombre, COUNT(t) FROM Tramite t JOIN t.solicitud s JOIN s.solicitante u WHERE t.estado = :queGraficar GROUP BY u.nombre")
    List<Object[]> countTramitesByUsuario(String queGraficar);

    @Query("SELECT e.pais, COUNT(t) FROM Tramite t JOIN t.entidadSanitaria e WHERE t.estado = :queGraficar GROUP BY e.pais")
    List<Object[]> countTramitesByPais(String queGraficar);

    @Query("SELECT FUNCTION('MONTH', t.fechaRadicacion), COUNT(t) FROM Tramite t WHERE t.estado = :queGraficar GROUP BY FUNCTION('MONTH', t.fechaRadicacion)")
    List<Object[]> countTramitesByMes(String queGraficar);

    @Query("SELECT t.tipoProducto, COUNT(t) FROM Tramite t WHERE t.estado = :queGraficar GROUP BY t.tipoProducto")
    List<Object[]> countTramitesByProducto(String queGraficar);
    //NUEVOS
    //********************************************************************** */

    // Consultas por Usuario
    @Query("SELECT u.nombre, COUNT(t) FROM Tramite t JOIN t.solicitud s JOIN s.solicitante u WHERE t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE' GROUP BY u.nombre")
    List<Object[]> countTramitesActivosByUsuario();

    @Query("SELECT u.nombre, COUNT(t) FROM Tramite t JOIN t.solicitud s JOIN s.solicitante u WHERE t.estado = 'RECHAZADO' GROUP BY u.nombre")
    List<Object[]> countTramitesRechazadosByUsuario();

    @Query("SELECT u.nombre, COUNT(t) FROM Tramite t JOIN t.solicitud s JOIN s.solicitante u WHERE t.tipoTramite = 'NACIONAL' GROUP BY u.nombre")
    List<Object[]> countTramitesNacionalesByUsuario();

    @Query("SELECT u.nombre, COUNT(t) FROM Tramite t JOIN t.solicitud s JOIN s.solicitante u WHERE t.tipoTramite = 'INTERNACIONAL' GROUP BY u.nombre")
    List<Object[]> countTramitesInternacionalesByUsuario();

    @Query("SELECT u.nombre, COUNT(t) FROM Tramite t JOIN t.solicitud s JOIN s.solicitante u GROUP BY u.nombre")
    List<Object[]> countAllTramitesByUsuario();

    // Consultas por País
    @Query("SELECT e.pais, COUNT(t) FROM Tramite t JOIN t.entidadSanitaria e WHERE t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE' GROUP BY e.pais")
    List<Object[]> countTramitesActivosByPais();

    @Query("SELECT e.pais, COUNT(t) FROM Tramite t JOIN t.entidadSanitaria e WHERE t.estado = 'RECHAZADO' GROUP BY e.pais")
    List<Object[]> countTramitesRechazadosByPais();

    @Query("SELECT e.pais, COUNT(t) FROM Tramite t JOIN t.entidadSanitaria e WHERE t.tipoTramite = 'NACIONAL' GROUP BY e.pais")
    List<Object[]> countTramitesNacionalesByPais();

    @Query("SELECT e.pais, COUNT(t) FROM Tramite t JOIN t.entidadSanitaria e WHERE t.tipoTramite = 'INTERNACIONAL' GROUP BY e.pais")
    List<Object[]> countTramitesInternacionalesByPais();

    @Query("SELECT e.pais, COUNT(t) FROM Tramite t JOIN t.entidadSanitaria e GROUP BY e.pais")
    List<Object[]> countAllTramitesByPais();

    // Consultas por Meses
    @Query("SELECT FUNCTION('MONTH', t.fechaRadicacion), COUNT(t) FROM Tramite t WHERE t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE' GROUP BY FUNCTION('MONTH', t.fechaRadicacion)")
    List<Object[]> countTramitesActivosByMes();

    @Query("SELECT FUNCTION('MONTH', t.fechaRadicacion), COUNT(t) FROM Tramite t WHERE t.estado = 'RECHAZADO' GROUP BY FUNCTION('MONTH', t.fechaRadicacion)")
    List<Object[]> countTramitesRechazadosByMes();

    @Query("SELECT FUNCTION('MONTH', t.fechaRadicacion), COUNT(t) FROM Tramite t WHERE t.tipoTramite = 'NACIONAL' GROUP BY FUNCTION('MONTH', t.fechaRadicacion)")
    List<Object[]> countTramitesNacionalesByMes();

    @Query("SELECT FUNCTION('MONTH', t.fechaRadicacion), COUNT(t) FROM Tramite t WHERE t.tipoTramite = 'INTERNACIONAL' GROUP BY FUNCTION('MONTH', t.fechaRadicacion)")
    List<Object[]> countTramitesInternacionalesByMes();

    @Query("SELECT FUNCTION('MONTH', t.fechaRadicacion), COUNT(t) FROM Tramite t GROUP BY FUNCTION('MONTH', t.fechaRadicacion)")
    List<Object[]> countAllTramitesByMes();

    // Consultas por Productos
    @Query("SELECT t.tipoProducto, COUNT(t) FROM Tramite t WHERE t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE' GROUP BY t.tipoProducto")
    List<Object[]> countTramitesActivosByProducto();

    @Query("SELECT t.tipoProducto, COUNT(t) FROM Tramite t WHERE t.estado = 'RECHAZADO' GROUP BY t.tipoProducto")
    List<Object[]> countTramitesRechazadosByProducto();

    @Query("SELECT t.tipoProducto, COUNT(t) FROM Tramite t WHERE t.tipoTramite = 'NACIONAL' GROUP BY t.tipoProducto")
    List<Object[]> countTramitesNacionalesByProducto();

    @Query("SELECT t.tipoProducto, COUNT(t) FROM Tramite t WHERE t.tipoTramite = 'INTERNACIONAL' GROUP BY t.tipoProducto")
    List<Object[]> countTramitesInternacionalesByProducto();

    @Query("SELECT t.tipoProducto, COUNT(t) FROM Tramite t GROUP BY t.tipoProducto")
    List<Object[]> countAllTramitesByProducto();

    //********************************************************************** */
    //FIN NUEVOS
    //Final filtros para estadistica
    // filtros para tramites
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

}
