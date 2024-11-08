package com.example.colombina.repositories;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.example.colombina.model.Tramite;
import com.example.colombina.model.Tramite.EstadoTramite;
import com.example.colombina.model.Tramite.TipoTramite;



@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
  Optional<Tramite> findByNumeroRadicado(String numeroRadicado);

  List<Tramite> findByEstado(Tramite.EstadoTramite estado);

  @NotNull
  List<Tramite> findAll();

  @NotNull
  Optional<Tramite> findById(Long id);

  List<Tramite> findAllByOrderByIdDesc();

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

    

    
    long countByTipoTramite(TipoTramite tipoTramite);
    long countByEstado(EstadoTramite estado);

    /*
    @Query("SELECT TO_CHAR(t.fechaRadicacion, 'FMMonth/YYYY') AS mes_anio, COUNT(t) " +
       "FROM Tramite t " +
       "WHERE t.estado = :estado1 OR t.estado = :estado2 " +
       "GROUP BY TO_CHAR(t.fechaRadicacion, 'FMMonth/YYYY') " +
       "ORDER BY MIN(t.fechaRadicacion)")
      List<Object[]> countTramitesByMesAndEstado(@Param("estado1") EstadoTramite estado1, @Param("estado2") EstadoTramite estado2);

      @Query("SELECT TO_CHAR(t.fechaRadicacion, 'FMMonth/YYYY') AS mes_anio, COUNT(t.id) " +
            "FROM Tramite t " +
            "WHERE t.tipoTramite = 'NACIONAL' " +
            "GROUP BY TO_CHAR(t.fechaRadicacion, 'FMMonth/YYYY') " +
            "ORDER BY MIN(t.fechaRadicacion)")
      List<Object[]> countTramitesNacionalesByMes();

      @Query("SELECT TO_CHAR(t.fechaRadicacion, 'FMMonth/YYYY') AS mes_anio, COUNT(t) " +
            "FROM Tramite t " +
            "GROUP BY TO_CHAR(t.fechaRadicacion, 'FMMonth/YYYY') " +
            "ORDER BY MIN(t.fechaRadicacion)")
      List<Object[]> countAllTramitesByMes();
      @Query("SELECT TO_CHAR(t.fechaRadicacion, 'Month YYYY') AS mes_anio, COUNT(t.id) " +
       "FROM Tramite t " +
       "WHERE t.tipoTramite = 'INTERNACIONAL' " +
       "GROUP BY TO_CHAR(t.fechaRadicacion, 'Month YYYY') " +
       "ORDER BY mes_anio")
    List<Object[]> countTramitesInternacionalesByMes();
    */
    @Query("SELECT TO_CHAR(t.fechaRadicacion, 'MM/YYYY') AS mes_anio, COUNT(t) " +
          "FROM Tramite t " +
          "WHERE t.estado = :estado1 OR t.estado = :estado2 " +
          "GROUP BY TO_CHAR(t.fechaRadicacion, 'MM/YYYY') " +
          "ORDER BY MIN(t.fechaRadicacion)")
    List<Object[]> countTramitesByMesAndEstado(@Param("estado1") EstadoTramite estado1, @Param("estado2") EstadoTramite estado2);

    @Query("SELECT TO_CHAR(t.fechaRadicacion, 'MM/YYYY') AS mes_anio, COUNT(t.id) " +
          "FROM Tramite t " +
          "WHERE t.tipoTramite = 'NACIONAL' " +
          "GROUP BY TO_CHAR(t.fechaRadicacion, 'MM/YYYY') " +
          "ORDER BY MIN(t.fechaRadicacion)")
    List<Object[]> countTramitesNacionalesByMes();

    @Query("SELECT TO_CHAR(t.fechaRadicacion, 'MM/YYYY') AS mes_anio, COUNT(t) " +
          "FROM Tramite t " +
          "GROUP BY TO_CHAR(t.fechaRadicacion, 'MM/YYYY') " +
          "ORDER BY MIN(t.fechaRadicacion)")
    List<Object[]> countAllTramitesByMes();
    @Query("SELECT TO_CHAR(t.fechaRadicacion, 'MM/YYYY') AS mes_anio, COUNT(t.id) " +
          "FROM Tramite t " +
          "WHERE t.tipoTramite = 'INTERNACIONAL' " +
          "GROUP BY TO_CHAR(t.fechaRadicacion, 'MM/YYYY') " +
          "ORDER BY mes_anio")
    List<Object[]> countTramitesInternacionalesByMes();


    
    @Query("SELECT t.tipoTramite, " +
    "SUM(CASE WHEN t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE' THEN 1 ELSE 0 END) AS activos, " +
    "SUM(CASE WHEN t.estado = 'APROBADO' OR t.estado = 'RECHAZADO' THEN 1 ELSE 0 END) AS cerrados " +
    "FROM Tramite t " +
    "GROUP BY t.tipoTramite")
   List<Object[]> countTramitesActivosYCerradosByTipoTramite();

   @Query("SELECT TO_CHAR(t.fechaRadicacion, 'Month') AS mes, COUNT(t.id) " +
       "FROM Tramite t " +
       "WHERE EXTRACT(YEAR FROM t.fechaRadicacion) = :year " +
       "GROUP BY TO_CHAR(t.fechaRadicacion, 'Month'), EXTRACT(MONTH FROM t.fechaRadicacion) " +
       "ORDER BY EXTRACT(MONTH FROM t.fechaRadicacion)")
      List<Object[]> countTramitesByYear(@Param("year") int year);




      @Query("SELECT t.tipoProducto, " +
            "SUM(CASE WHEN (t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE') AND t.tipoTramite = 'NACIONAL' THEN 1 ELSE 0 END) AS activos, " +
            "SUM(CASE WHEN (t.estado = 'APROBADO' OR t.estado = 'RECHAZADO') AND t.tipoTramite = 'NACIONAL' THEN 1 ELSE 0 END) AS cerrados " +
            "FROM Tramite t " +
            "GROUP BY t.tipoProducto")
      List<Object[]> countTramitesNacionalesActivosYCerradosByProducto();

      @Query("SELECT t.tipoProducto, " +
            "SUM(CASE WHEN (t.estado = 'EN_REVISION' OR t.estado = 'PENDIENTE') AND t.tipoTramite = 'INTERNACIONAL' THEN 1 ELSE 0 END) AS activos, " +
            "SUM(CASE WHEN (t.estado = 'APROBADO' OR t.estado = 'RECHAZADO') AND t.tipoTramite = 'INTERNACIONAL' THEN 1 ELSE 0 END) AS cerrados " +
            "FROM Tramite t " +
            "GROUP BY t.tipoProducto")
      List<Object[]> countTramitesInternacionalesActivosYCerradosByProducto();



      
      @Query("SELECT to_char(t.fechaRadicacion, 'Month') AS mes, COUNT(t.id) " +
            "FROM Tramite t " +
            "WHERE t.fechaRadicacion IS NOT NULL " +
            "GROUP BY to_char(t.fechaRadicacion, 'Month'), extract(month from t.fechaRadicacion) " +
            "ORDER BY extract(month from t.fechaRadicacion)")
      List<Object[]> countRegistrosPorVencer();
      



    //********************************************************************** */
    //FIN NUEVOS
   

}
