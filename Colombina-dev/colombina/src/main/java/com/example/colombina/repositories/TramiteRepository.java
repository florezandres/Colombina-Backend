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
import com.example.colombina.model.Tramite.EstadoTramite;
import com.example.colombina.model.Tramite.TipoTramite;

import java.util.Date;


@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
  Optional<Tramite> findByNumeroRadicado(String numeroRadicado);

  List<Tramite> findByEstado(Tramite.EstadoTramite estado);

  @NotNull
  List<Tramite> findAll();

  Optional<Tramite> findById(Long id);

  // filtros para Estadisticas
  
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

   @Query("SELECT EXTRACT(MONTH FROM t.fechaRadicacion) AS mes, COUNT(t) " +
       "FROM Tramite t " +
       "WHERE t.estado = :estado1 OR t.estado = :estado2 " +
       "GROUP BY EXTRACT(MONTH FROM t.fechaRadicacion) " +
       "ORDER BY mes")
    List<Object[]> countTramitesByMesAndEstado(@Param("estado1") EstadoTramite estado1, @Param("estado2") EstadoTramite estado2);

    long countByTipoTramite(TipoTramite tipoTramite);
    long countByEstado(EstadoTramite estado);
    
    //********************************************************************** */
    //FIN NUEVOS
   

}
