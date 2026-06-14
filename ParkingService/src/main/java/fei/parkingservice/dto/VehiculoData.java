/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.dto;

/**
 *
 * @author Jorge
 */
public class VehiculoData {
    
    private Integer idUsuario;
    private Integer idVehiculo;
    private String placa;
    private Integer estatus;
    private String marca;
    private String modelo;

    public VehiculoData() {
    }

    public VehiculoData(Integer idUsuario, Integer idVehiculo, String placa, Integer estatus, String marca, String modelo) {
        this.idUsuario = idUsuario;
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.estatus = estatus;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    
}
