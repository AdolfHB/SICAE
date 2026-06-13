/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

/**
 *
 * @author Jorge
 */
public class UsuarioResponse {
    
    private Boolean error;
    private String message;
    private UsuarioData data;

    public UsuarioResponse() {
    }

    public UsuarioResponse(Boolean error, String message, UsuarioData data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UsuarioData getData() {
        return data;
    }

    public void setData(UsuarioData data) {
        this.data = data;
    }
    
}
