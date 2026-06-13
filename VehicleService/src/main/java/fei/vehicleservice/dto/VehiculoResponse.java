/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.dto;

import fei.vehicleservice.model.pojos.vehiculo;
import java.util.List;

/**
 *
 * @author Ainz Oal Gown
 */
public class VehiculoResponse {
    private boolean error;
    private String mensaje;
    private List<vehiculo> vehiculos;

    public VehiculoResponse() {
    }
    
    public VehiculoResponse(boolean error, String mensaje, List<vehiculo> vehiculos) {
        this.error = error;
        this.mensaje = mensaje;
        this.vehiculos = vehiculos;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
}
