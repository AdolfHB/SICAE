/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fei.parkingservice.dto.EntradaRequest;
import fei.parkingservice.dto.EntradaResponse;
import fei.parkingservice.model.pojos.Espacio;
import fei.parkingservice.dto.EspaciosResponse;
import fei.parkingservice.repositories.ParkingRepository;
import java.util.ArrayList;
import java.util.List;
import fei.parkingservice.dto.EspacioDTO;
import fei.parkingservice.dto.SalidaRequest;
import fei.parkingservice.dto.SalidaResponse;
import fei.parkingservice.dto.UsuarioData;
import fei.parkingservice.dto.UsuarioResponse;
import fei.parkingservice.dto.VehiculoData;
import fei.parkingservice.dto.VehiculoResponse;
import fei.parkingservice.model.pojos.Movimiento;
import fei.parkingservice.utils.AppProperties;
import fei.parkingservice.utils.HttpClientUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Jorge
 */
public class ParkingService {
    
    private ParkingRepository pr;
    
    private ObjectMapper mapper;

    
    public ParkingService() {
        this.pr = new ParkingRepository();
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }
    
    
    // ***************** CONSULTAR ESPACIOS *****************
    
    public EspaciosResponse consultarEspacios() {

        List<Espacio> espaciosLibres = pr.getEspaciosLibres();

        if (espaciosLibres == null) {
            return new EspaciosResponse();
        }

        List<EspacioDTO> espaciosDTO = new ArrayList<>();
        for (Espacio espacio : espaciosLibres) {
            EspacioDTO dto = new EspacioDTO();
            dto.setIdEspacio(espacio.getIdEspacio());
            dto.setClaveEspacio(espacio.getClaveEspacio());
            dto.setTipo(espacio.getTipo());
            espaciosDTO.add(dto);
        }

        EspaciosResponse response = new EspaciosResponse();
        response.setEspaciosLibres(espaciosDTO);
        response.setTotalEspacios(espaciosDTO.size());
        response.setMensaje("Espacios libres obtenidos correctamente");

        return response;
    }
    
    // ***************** ENTRADA *****************
    public EntradaResponse registrarEntrada(EntradaRequest request, String token) {
        
        if (request.getClaveUsuario() == null || request.getClaveUsuario().trim().isEmpty()) {
            return new EntradaResponse("Error: claveUsuario es obligatoria");
        }
        if (request.getPlaca() == null || request.getPlaca().trim().isEmpty()) {
            return new EntradaResponse("Error: placa es obligatoria");
        }
        if (request.getTarifaHora() == null) {
            return new EntradaResponse("Error: tarifaHora es obligatoria");
        }
        if (request.getIdEspacio() == null) {
            return new EntradaResponse("Error: idEspacio es obligatorio");
        }
        
        UsuarioData usuario = null;
            try {
                usuario = getUserByClave(request.getClaveUsuario(), token);
            } catch (Exception e) {
                return new EntradaResponse("Error: No se pudo conectar con el servicio de usuarios. " + e.getMessage());
            }

            if (usuario == null) {
                return new EntradaResponse("Error: Usuario no encontrado");
            }

            if (!"1".equals(usuario.getEstatus())) {
                return new EntradaResponse("Error: El usuario no está activo");
            }
        
        Integer idUsuario = usuario.getIdUsuario();
        
        List<VehiculoData> vehiculos = null;
        try {
                vehiculos = getVehiculosByUsuario(token);
            } catch (Exception e) {
                return new EntradaResponse("Error: No se pudo conectar con el servicio de vehículos. " + e.getMessage());
            }

            if (vehiculos == null || vehiculos.isEmpty()) {
                return new EntradaResponse("Error: No se encontraron vehículos para este usuario");
            }
        
        VehiculoData vehiculoEntrada = null;
        for (VehiculoData v : vehiculos) {
            if (v.getPlaca().equals(request.getPlaca())) {
                vehiculoEntrada = v;
                break;
            }
        }
        
        if (vehiculoEntrada == null) {
            return new EntradaResponse("Error: El vehículo no pertenece al usuario");
        }
        
        if (vehiculoEntrada.getEstatus() != 1) {
            return new EntradaResponse("Error: El vehículo no está activo");
        }
        
        Integer idVehiculo = vehiculoEntrada.getIdVehiculo();
        
        
        boolean tieneEntradaActiva = pr.tieneEntradaActiva(idVehiculo);
        if (tieneEntradaActiva) {
            return new EntradaResponse("Error: El vehículo ya tiene una entrada activa");
        }
        
        List<Integer> idsVehiculos = new ArrayList<>();
        for (VehiculoData v : vehiculos) {
            idsVehiculos.add(v.getIdVehiculo());
        }
        
        int vehiculosActivos = pr.countVehiculosActivosByListaIds(idsVehiculos);
        if (vehiculosActivos >= 2) {
            return new EntradaResponse("Error: El usuario ya tiene 2 vehículos en el estacionamiento");
        }
        
        Espacio espacio = pr.getEspacioById(request.getIdEspacio());
        if (espacio == null) {
            return new EntradaResponse("Error: El espacio no existe");
        }
        if (espacio.getOcupado()) {
            return new EntradaResponse("Error: El espacio ya está ocupado");
        }
        
        Map<String, Object> params = new HashMap<>();
        params.put("idVehiculo", idVehiculo);
        params.put("tarifaHora", request.getTarifaHora());
        params.put("idEspacio", request.getIdEspacio());
        LocalDateTime ahora = LocalDateTime.now();
        params.put("tiempoEntrada", ahora);
        params.put("tiempoCreacion", ahora);
        
        Integer idMovimiento = pr.insertMovimiento(params);
        if (idMovimiento == null || idMovimiento == 0) {
            return new EntradaResponse("Error: No se pudo registrar la entrada");
        }
        
        pr.ocuparEspacio(request.getIdEspacio());
        
        EntradaResponse response = new EntradaResponse();
        response.setIdMovimiento(idMovimiento);
        response.setTiempoEntrada(LocalDateTime.now().toString());
        response.setIdEspacio(request.getIdEspacio());
        response.setClaveEspacio(espacio.getClaveEspacio());
        response.setTarifaHora(request.getTarifaHora());
        response.setMensaje("Entrada registrada correctamente");
        
        return response;
    }
    
    
    // ***************** SALIDA *****************
    public SalidaResponse registrarSalida(SalidaRequest request, String token) {

        if (request.getClaveUsuario() == null || request.getClaveUsuario().trim().isEmpty()) {
            return new SalidaResponse("Error: claveUsuario es obligatoria");
        }
        if (request.getPlaca() == null || request.getPlaca().trim().isEmpty()) {
            return new SalidaResponse("Error: placa es obligatoria");
        }

        UsuarioData usuario = null;
        try {
            usuario = getUserByClave(request.getClaveUsuario(), token);
        } catch (Exception e) {
            return new SalidaResponse("Error: No se pudo conectar con el servicio de usuarios. " + e.getMessage());
        }

        if (usuario == null) {
            return new SalidaResponse("Error: Usuario no encontrado");
        }
        
        if (!"1".equals(usuario.getEstatus())) {
            return new SalidaResponse("Error: El usuario no está activo");
        }

        Integer idUsuario = usuario.getIdUsuario();

        List<VehiculoData> vehiculos = null;
        try {
            vehiculos = getVehiculosByUsuario(token);
        } catch (Exception e) {
            return new SalidaResponse("Error: No se pudo conectar con el servicio de vehículos. " + e.getMessage());
        }

        if (vehiculos == null || vehiculos.isEmpty()) {
            return new SalidaResponse("Error: No se encontraron vehículos para este usuario");
        }

        VehiculoData vehiculoSalida = null;
        for (VehiculoData v : vehiculos) {
            if (v.getPlaca().equals(request.getPlaca())) {
                vehiculoSalida = v;
                break;
            }
        }

        if (vehiculoSalida == null) {
            return new SalidaResponse("Error: El vehículo no pertenece al usuario");
        }

        Integer idVehiculo = vehiculoSalida.getIdVehiculo();


        Movimiento movimientoActivo = pr.getMovimientoActivo(idVehiculo);
        if (movimientoActivo == null) {
            return new SalidaResponse("Error: No se encontró una entrada activa para este vehículo");
        }

        Integer idMovimiento = movimientoActivo.getIdMovimiento();
        Integer idEspacio = movimientoActivo.getIdEspacio();
        BigDecimal tarifaHora = movimientoActivo.getTarifaHora();
        LocalDateTime tiempoEntrada = movimientoActivo.getTiempoEntrada();

        LocalDateTime tiempoSalida = LocalDateTime.now();

        long minutos = java.time.Duration.between(tiempoEntrada, tiempoSalida).toMinutes();
        int minutosEstacionado = (int) minutos;

        int horasCobradas = (int) Math.ceil(minutos / 60.0);
        if (horasCobradas == 0) horasCobradas = 1;

        BigDecimal costoTotal = tarifaHora.multiply(BigDecimal.valueOf(horasCobradas));

        Map<String, Object> params = new HashMap<>();
        params.put("idMovimiento", idMovimiento);
        params.put("minutosEstacionado", minutosEstacionado);
        params.put("horasCobradas", horasCobradas);
        params.put("costoTotal", costoTotal);
        params.put("tiempoSalida", tiempoSalida);
        params.put("tiempoActualizacion", tiempoSalida);

        boolean actualizado = pr.updateMovimientoSalida(params);
        if (!actualizado) {
            return new SalidaResponse("Error: No se pudo registrar la salida");
        }

        pr.liberarEspacio(idEspacio);

        Espacio espacio = pr.getEspacioById(idEspacio);
        String claveEspacio = espacio != null ? espacio.getClaveEspacio() : "Desconocido";

        SalidaResponse response = new SalidaResponse();
        response.setIdMovimiento(idMovimiento);
        response.setTiempoEntrada(tiempoEntrada.toString());
        response.setTiempoSalida(tiempoSalida.toString());
        response.setIdEspacio(idEspacio);
        response.setClaveEspacio(claveEspacio);
        response.setTarifaHora(tarifaHora);
        response.setCostoTotal(costoTotal);
        response.setHorasCobradas(horasCobradas);
        response.setMensaje("Salida registrada correctamente");

        return response;
    }
    
    // ***************** LLAMADAS A OTROS SERVICIOS *****************
    private UsuarioData getUserByClave(String claveUsuario, String token) throws Exception {
        String url = AppProperties.get("user.service.url") + "/profile-clave/" + claveUsuario;
        String json = HttpClientUtil.get(url, token);

        UsuarioResponse response = mapper.readValue(json, UsuarioResponse.class);

        if (response.getError() != null && response.getError()) {
            return null;
        }
        return response.getData();
    }
    
    private List<VehiculoData> getVehiculosByUsuario(String token) throws Exception {
        String url = AppProperties.get("vehicle.service.url") + "/vehiculos/usuario/vehiculos";
        String json = HttpClientUtil.get(url, token);

        VehiculoResponse response = mapper.readValue(json, VehiculoResponse.class);

        if (response.getError() != null && response.getError()) {
            return null;
        }
        return response.getVehiculos();
    }

}