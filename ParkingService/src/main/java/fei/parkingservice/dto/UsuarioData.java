/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

/**
 *
 * @author Jorge
 */
public class UsuarioData {
    private Integer idUsuario;
    private String claveUsuario;
    private String nombreCompleto;
    private String estatus;
    private Integer idRol;
    private String rol;

    public UsuarioData() {
    }

    public UsuarioData(Integer idUsuario, String claveUsuario, String nombreCompleto, String estatus, Integer idRol, String rol) {
        this.idUsuario = idUsuario;
        this.claveUsuario = claveUsuario;
        this.nombreCompleto = nombreCompleto;
        this.estatus = estatus;
        this.idRol = idRol;
        this.rol = rol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
