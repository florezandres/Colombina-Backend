package com.example.colombina.model;

public class EstadisticasDTO {

    private Integer mes;  
    private Long cantidad;
    private String tipo;

    // Constructor para la consulta por mes y cantidad
    public EstadisticasDTO(Integer mes, Long cantidad) {
        this.mes = mes;
        this.cantidad = cantidad;
    }

    // Constructor para la consulta por tipo y cantidad
    public EstadisticasDTO(String tipo, Long cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
