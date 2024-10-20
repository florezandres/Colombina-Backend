package com.example.colombina.services;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.DTOs.EstadisticasDTO;
import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.TramiteRepository;
import com.example.colombina.repositories.UsuarioRepository;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TramiteService {

    @Autowired
    private TramiteRepository tramiteRepository;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cambia el estado de un trámite a EN_REVISION
    public void abrirTramite(Long idTramite) {
        // 1. Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // 2. Verificar si el estado actual es PENDIENTE
        if (tramite.getEstado() != Tramite.EstadoTramite.PENDIENTE) {
            throw new IllegalArgumentException("El trámite no está en estado PENDIENTE, no puede ser abierto.");
        }

        // 3. Cambiar el estado del trámite a EN_REVISION
        tramite.setEstado(Tramite.EstadoTramite.EN_REVISION);

        // 4. Guardar los cambios en la base de datos
        tramiteRepository.save(tramite);
    }

      // Obtener trámites nacionales agrupados por mes
    public List<EstadisticasDTO> obtenerTramitesNacionalesPorMes() {
        return tramiteRepository.contarTramitesPorMesYTipo("nacional");
    }

    // Obtener trámites internacionales agrupados por mes
    public List<EstadisticasDTO> obtenerTramitesInternacionalesPorMes() {
        return tramiteRepository.contarTramitesPorMesYTipo("internacional");
    }

    // Obtener documentos devueltos agrupados por tipo
    public List<EstadisticasDTO> obtenerDocumentosDevueltosPorTipo() {
        return tramiteRepository.contarDocumentosDevueltosPorTipo();
    }
    //Filtra los tramites por periodos
    public List<EstadisticasDTO> obtenerTramitesPorPeriodo(String tipo, String periodo) {
        switch (periodo.toLowerCase()) {
            case "semanas":
                return tramiteRepository.contarTramitesPorSemanaYTipo(tipo);
            case "meses":
                return tramiteRepository.contarTramitesPorMesYTipo(tipo);
            case "años":
                return tramiteRepository.contarTramitesPorAnoYTipo(tipo);
            default:
                throw new IllegalArgumentException("Periodo no válido. Utiliza 'semanas', 'meses' o 'años'.");
        }
    }
  
    //Filtros para tramites
    public List<EstadisticasDTO> obtenerTramitesFiltrados(String tipo, String pais, String fechaInicio, String fechaFin) {
        return tramiteRepository.filtrarTramites(tipo, pais, fechaInicio, fechaFin);
    }

    //escalar un tramite
    public void escalarTramite(Long idTramite) {
        // Buscar el trámite por ID
        Tramite tramite = tramiteRepository.findById(idTramite).orElseThrow(() -> new IllegalArgumentException("Trámite no encontrado"));

        // Crear el mensaje de notificación
        String mensaje = "El tramite con numero de Radicado " + tramite.getNumeroRadicado() + " ha sido escalado por falta de respuesta.";

        // Obtener los usuarios con roles ADMIN o AGENTE
        List<Usuario> destinatarios = usuarioRepository.findByRolTipoRolIn(List.of("ADMIN", "AGENTE"));

        // Crear notificaciones para cada destinatario
        for (Usuario usuario : destinatarios) {
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje(mensaje);
            notificacion.setFecha(new Date());
            notificacion.setDestinatario(usuario);

            // Guardar la notificación
            notificacionService.crearNotificacion(notificacion);
        }
    }
    
    
}
