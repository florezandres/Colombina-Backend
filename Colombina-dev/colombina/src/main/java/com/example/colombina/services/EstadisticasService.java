package com.example.colombina.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.repositories.TramiteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstadisticasService {

    @Autowired
    private TramiteRepository tramiteRepository;

    public Map<String, Object> getChartData(String queGraficar, String enFuncionDe) {
        Map<String, Object> result = new HashMap<>();
        List<Object[]> data;

        switch (enFuncionDe.toLowerCase()) {
            case "usuario":
                data = queGraficar.equalsIgnoreCase("Trámites Activos") ?
                        tramiteRepository.countTramitesActivosByUsuario() :
                        queGraficar.equalsIgnoreCase("Trámites Rechazados") ?
                        tramiteRepository.countTramitesRechazadosByUsuario() :
                        queGraficar.equalsIgnoreCase("Trámites Nacionales") ?
                        tramiteRepository.countTramitesNacionalesByUsuario() :
                        queGraficar.equalsIgnoreCase("Trámites Internacionales") ?
                        tramiteRepository.countTramitesInternacionalesByUsuario() :
                        tramiteRepository.countAllTramitesByUsuario();
                break;

            case "pais":
                data = queGraficar.equalsIgnoreCase("Trámites Activos") ?
                        tramiteRepository.countTramitesActivosByPais() :
                        queGraficar.equalsIgnoreCase("Trámites Rechazados") ?
                        tramiteRepository.countTramitesRechazadosByPais() :
                        queGraficar.equalsIgnoreCase("Trámites Nacionales") ?
                        tramiteRepository.countTramitesNacionalesByPais() :
                        queGraficar.equalsIgnoreCase("Trámites Internacionales") ?
                        tramiteRepository.countTramitesInternacionalesByPais() :
                        tramiteRepository.countAllTramitesByPais();
                break;

            case "meses":
                data = queGraficar.equalsIgnoreCase("Trámites Activos") ?
                        tramiteRepository.countTramitesActivosByMes() :
                        queGraficar.equalsIgnoreCase("Trámites Rechazados") ?
                        tramiteRepository.countTramitesRechazadosByMes() :
                        queGraficar.equalsIgnoreCase("Trámites Nacionales") ?
                        tramiteRepository.countTramitesNacionalesByMes() :
                        queGraficar.equalsIgnoreCase("Trámites Internacionales") ?
                        tramiteRepository.countTramitesInternacionalesByMes() :
                        tramiteRepository.countAllTramitesByMes();
                break;

            case "productos":
                data = queGraficar.equalsIgnoreCase("Trámites Activos") ?
                        tramiteRepository.countTramitesActivosByProducto() :
                        queGraficar.equalsIgnoreCase("Trámites Rechazados") ?
                        tramiteRepository.countTramitesRechazadosByProducto() :
                        queGraficar.equalsIgnoreCase("Trámites Nacionales") ?
                        tramiteRepository.countTramitesNacionalesByProducto() :
                        queGraficar.equalsIgnoreCase("Trámites Internacionales") ?
                        tramiteRepository.countTramitesInternacionalesByProducto() :
                        tramiteRepository.countAllTramitesByProducto();
                break;

            default:
                throw new IllegalArgumentException("Filtro no soportado: " + enFuncionDe);
        }

        result.put("labels", data.stream().map(d -> d[0]).toArray());
        result.put("values", data.stream().map(d -> d[1]).toArray());

        return result;
    }
}
