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
public class SalidaResponse {
    
    private Integer idMovimiento;
    private String tiempoEntrada;
    private String tiempoSalida;
    private Integer idEspacio;
    private String claveEspacio;
    private BigDecimal tarifaHora;
    private BigDecimal costoTotal;
    private Integer horasCobradas;
    private String mensaje;

    public SalidaResponse() {
    }

    public SalidaResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public SalidaResponse(Integer idMovimiento, String tiempoEntrada, String tiempoSalida, Integer idEspacio, String claveEspacio, BigDecimal tarifaHora, BigDecimal costoTotal, Integer horasCobradas, String mensaje) {
        this.idMovimiento = idMovimiento;
        this.tiempoEntrada = tiempoEntrada;
        this.tiempoSalida = tiempoSalida;
        this.idEspacio = idEspacio;
        this.claveEspacio = claveEspacio;
        this.tarifaHora = tarifaHora;
        this.costoTotal = costoTotal;
        this.horasCobradas = horasCobradas;
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

    public String getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(String tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
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

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Integer getHorasCobradas() {
        return horasCobradas;
    }

    public void setHorasCobradas(Integer horasCobradas) {
        this.horasCobradas = horasCobradas;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
