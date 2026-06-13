/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

/**
 *
 * @author Jorge
 */
public class SalidaRequest {
    
    private String claveUsuario;
    private String placa;

    public SalidaRequest() {
    }

    public SalidaRequest(String claveUsuario, String placa) {
        this.claveUsuario = claveUsuario;
        this.placa = placa;
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
    
}
