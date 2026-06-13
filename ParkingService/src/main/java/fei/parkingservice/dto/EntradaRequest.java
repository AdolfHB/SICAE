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
public class EntradaRequest {
    
    private String claveUsuario;
    private String placa;
    private BigDecimal tarifaHora;
    private Integer idEspacio;

    public EntradaRequest() {
    }

    public EntradaRequest(String claveUsuario, String placa, BigDecimal tarifaHora, Integer idEspacio) {
        this.claveUsuario = claveUsuario;
        this.placa = placa;
        this.tarifaHora = tarifaHora;
        this.idEspacio = idEspacio;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getTarifaHora() {
        return tarifaHora;
    }

    public void setTarifaHora(BigDecimal tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    public Integer getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Integer idEspacio) {
        this.idEspacio = idEspacio;
    }
    
}
