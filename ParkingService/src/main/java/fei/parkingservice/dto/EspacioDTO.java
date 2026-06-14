/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

/**
 *
 * @author Jorge
 */
public class EspacioDTO {
    
    private Integer idEspacio;
    private String claveEspacio;
    private String tipo;

    public EspacioDTO() {
    }

    public EspacioDTO(Integer idEspacio, String claveEspacio, String tipo) {
        this.idEspacio = idEspacio;
        this.claveEspacio = claveEspacio;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}