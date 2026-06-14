/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.services;

import fei.vehicleservice.dto.VehiculoRequest;
import fei.vehicleservice.dto.VehiculoResponse;
import fei.vehicleservice.dto.VehiculoStatusRequest;
import fei.vehicleservice.model.pojos.vehiculo;
import fei.vehicleservice.repositories.vehiculoRepository;
import java.util.List;

/**
 *
 * @author Ainz Oal Gown
 */
public class vehiculoService {
    private vehiculoRepository ur;
    
    public vehiculoService() {
        this.ur = new vehiculoRepository();
    }
    
    public VehiculoResponse buscarPorUsuario(Integer idUsuarioAutenticado){
        if(idUsuarioAutenticado == null || idUsuarioAutenticado <= 0){
            return new VehiculoResponse(true, "Usuario requerido", null);
        }
        List<vehiculo> vehiculos = ur.buscarPorUsuario(idUsuarioAutenticado);
        if(vehiculos == null){
            return new VehiculoResponse(true, "Error al consultar vehiculos", null);
        }
        if(vehiculos.isEmpty()){
            return new VehiculoResponse(false, "No se encontraron vehiculos del usuario", vehiculos);
        }
        return new VehiculoResponse(false, "Vehiculos encontrados", vehiculos);
    }
    
    public VehiculoResponse registrar(Integer idUsuarioAutenticado, VehiculoRequest request) {
        if (idUsuarioAutenticado == null || idUsuarioAutenticado <= 0) {
            return new VehiculoResponse(true, "Usuario autenticado invalido", null);
        }
        if (request == null) {
            return new VehiculoResponse(true, "Datos requeridos", null);
        }
        if (request.getIdModelo() == null
                || request.getPlaca() == null || request.getPlaca().trim().isEmpty()
                || request.getColor() == null || request.getColor().trim().isEmpty()
                || request.getAnio() == null
                || request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {

            return new VehiculoResponse(true, "Faltan datos obligatorios", null);
        }
        if (request.getPlaca().length() > 7) {
            return new VehiculoResponse(true, "La placa no debe exceder 7 caracteres", null);
        }
        if (request.getColor().length() > 20) {
            return new VehiculoResponse(true, "El color no debe exceder 20 caracteres", null);
        }
        int existePlaca = ur.existePlaca(request.getPlaca());
        if (existePlaca < 0) {
            return new VehiculoResponse(true, "Error al validar placa", null);
        }
        if (existePlaca > 0) {
            return new VehiculoResponse(true, "Ya existe un vehiculo con esa placa", null);
        }
        int activos = ur.vehiculosActivos(idUsuarioAutenticado);
        if (activos < 0) {
            return new VehiculoResponse(true, "Error al contar vehiculos activos", null);
        }
        if (activos >= 4) {
            return new VehiculoResponse(true, "El usuario ya tiene 4 vehiculos activos", null);
        }
        vehiculo vh = new vehiculo();
        vh.setIdUsuario(idUsuarioAutenticado);
        vh.setIdModelo(request.getIdModelo());
        vh.setPlaca(request.getPlaca().trim().toUpperCase());
        vh.setColor(request.getColor().trim());
        vh.setAnio(request.getAnio());
        vh.setDescripcion(request.getDescripcion().trim());
        vh.setEstatus(1);
        String clave = "V" + System.currentTimeMillis();
        if (clave.length() > 10) {
            clave = clave.substring(clave.length() - 10);
        }
        vh.setClaveVehiculo(clave);
        boolean guardado = ur.registrar(vh);
        if (guardado) {
            return new VehiculoResponse(false, "Vehiculo registrado correctamente", null);
        }
        return new VehiculoResponse(true, "Error al registrar vehiculo", null);
    }
        
    public VehiculoResponse editar(Integer idVehiculo, Integer idUsuarioAutenticado, VehiculoRequest request) {
        if (idVehiculo == null || idVehiculo <= 0) {
            return new VehiculoResponse(true, "idVehiculo requerido", null);
        }
        
        if (idUsuarioAutenticado == null || idUsuarioAutenticado <= 0) {
            return new VehiculoResponse(true, "Usuario autenticado invalido", null);
        }
        
        if (request == null) {
            return new VehiculoResponse(true, "Datos requeridos", null);
        }
        if (request.getIdModelo() == null
                || request.getPlaca() == null || request.getPlaca().trim().isEmpty()
                || request.getColor() == null || request.getColor().trim().isEmpty()
                || request.getAnio() == null
                || request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
            return new VehiculoResponse(true, "Faltan datos obligatorios", null);
        }
        int pertenece = ur.vehiculoDeUsuario(idVehiculo, idUsuarioAutenticado);
        if (pertenece < 0) {
            return new VehiculoResponse(true, "Error al validar vehiculo", null);
        }
        if (pertenece == 0) {
            return new VehiculoResponse(true, "El vehiculo no pertenece al usuario", null);
        }
        int placaOtro = ur.placaRepetida(request.getPlaca(), idVehiculo);
        if (placaOtro < 0) {
            return new VehiculoResponse(true, "Error al validar placa", null);
        }
        if (placaOtro > 0) {
            return new VehiculoResponse(true, "Ya existe otro vehiculo con esa placa", null);
        }
        vehiculo vh = new vehiculo();
        vh.setIdUsuario(idUsuarioAutenticado);
        vh.setIdModelo(request.getIdModelo());
        vh.setPlaca(request.getPlaca().trim().toUpperCase());
        vh.setColor(request.getColor().trim());
        vh.setAnio(request.getAnio());
        vh.setDescripcion(request.getDescripcion().trim());
        boolean actualizado = ur.editar(idVehiculo, vh);
        if (actualizado) {
            return new VehiculoResponse(false, "Vehiculo actualizado correctamente", null);
        }
        return new VehiculoResponse(true, "Error al actualizar vehiculo", null);
    }   
    
    public VehiculoResponse cambiarEstatus(
            Integer idVehiculo,
            Integer idUsuarioAutenticado,
            VehiculoStatusRequest request
    ) {
        if (idVehiculo == null || idVehiculo <= 0) {
            return new VehiculoResponse(true, "idVehiculo requerido", null);
        }
        if (idUsuarioAutenticado == null || idUsuarioAutenticado <= 0) {
            return new VehiculoResponse(true, "Usuario autenticado invalido", null);
        }
        if (request == null || request.getEstatus() == null) {
            return new VehiculoResponse(true, "Estatus requerido", null);
        }
        if (request.getEstatus() != 0 && request.getEstatus() != 1) {
            return new VehiculoResponse(true, "Estatus invalido", null);
        }
        int pertenece = ur.vehiculoDeUsuario(idVehiculo, idUsuarioAutenticado);
        if (pertenece < 0) {
            return new VehiculoResponse(true, "Error al validar vehiculo", null);
        }
        if (pertenece == 0) {
            return new VehiculoResponse(true, "El vehiculo no pertenece al usuario", null);
        }
        boolean actualizado = ur.cambiarEstatus(
                idVehiculo,
                idUsuarioAutenticado,
                request.getEstatus()
        );
        if (actualizado) {
            return new VehiculoResponse(false, "Estatus actualizado correctamente", null);
        }
        return new VehiculoResponse(true, "Error al actualizar estatus", null);
    }
}
