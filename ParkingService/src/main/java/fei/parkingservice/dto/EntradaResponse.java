/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

import java.math.BigDecimal;

/**
 *
 * @author Jorge
 */
public class EntradaResponse {
    
    private Integer idMovimiento;
    private String tiempoEntrada;
    private Integer idEspacio;
    private String claveEspacio;
    private BigDecimal tarifaHora;
    private String mensaje;

    public EntradaResponse() {
    }

    public EntradaResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public EntradaResponse(Integer idMovimiento, String tiempoEntrada, Integer idEspacio, String claveEspacio, BigDecimal tarifaHora, String mensaje) {
        this.idMovimiento = idMovimiento;
        this.tiempoEntrada = tiempoEntrada;
        this.idEspacio = idEspacio;
        this.claveEspacio = claveEspacio;
        this.tarifaHora = tarifaHora;
        this.mensaje = mensaje;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getTiempoEntrada() {
        return tiempoEntrada;
    }

    public void setTiempoEntrada(String tiempoEntrada) {
        this.tiempoEntrada = tiempoEntrada;
    }

    public Integer getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Integer idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getClaveEspacio() {
        return claveEspacio;
    }

    public void setClaveEspacio(String claveEspacio) {
        this.claveEspacio = claveEspacio;
    }

    public BigDecimal getTarifaHora() {
        return tarifaHora;
    }

    public void setTarifaHora(BigDecimal tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
