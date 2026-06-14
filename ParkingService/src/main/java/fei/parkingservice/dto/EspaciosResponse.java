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
public class EspaciosResponse {
    
    private List<EspacioDTO> espaciosLibres;
    private Integer totalEspacios;
    private String mensaje;

    public EspaciosResponse() {
    }

    public EspaciosResponse(List<EspacioDTO> espaciosLibres, Integer totalEspacios, String mensaje) {
        this.espaciosLibres = espaciosLibres;
        this.totalEspacios = totalEspacios;
        this.mensaje = mensaje;
    }

    public List<EspacioDTO> getEspaciosLibres() {
        return espaciosLibres;
    }

    public void setEspaciosLibres(List<EspacioDTO> espaciosLibres) {
        this.espaciosLibres = espaciosLibres;
    }

    public Integer getTotalEspacios() {
        return totalEspacios;
    }

    public void setTotalEspacios(Integer totalEspacios) {
        this.totalEspacios = totalEspacios;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}