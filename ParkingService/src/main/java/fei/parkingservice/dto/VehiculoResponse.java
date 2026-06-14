/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

import java.util.List;

/**
 *
 * @author Jorge
 */
public class VehiculoResponse {
    
    private Boolean error;
    private String mensaje;
    private List<VehiculoData> vehiculos;

    public VehiculoResponse() {
    }

    public VehiculoResponse(Boolean error, String mensaje, List<VehiculoData> vehiculos) {
        this.error = error;
        this.mensaje = mensaje;
        this.vehiculos = vehiculos;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<VehiculoData> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<VehiculoData> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    
}
