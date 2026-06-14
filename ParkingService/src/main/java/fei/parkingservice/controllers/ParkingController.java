/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.parkingservice.controllers;

import fei.parkingservice.dto.EntradaRequest;
import fei.parkingservice.dto.EntradaResponse;
import fei.parkingservice.services.ParkingService;
import fei.parkingservice.dto.EspaciosResponse;
import fei.parkingservice.dto.SalidaRequest;
import fei.parkingservice.dto.SalidaResponse;
import fei.parkingservice.utils.JwtUtils;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Jorge
 */

@Path("/parking")
public class ParkingController {
    
    private ParkingService ps;
    
    public ParkingController() {
        this.ps = new ParkingService();
    }
    
    @GET
    @Path("/espacios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEspacios(@HeaderParam("Authorization") String authHeader) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token requerido").build();
        }
        String token = authHeader.substring(7);
        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token inválido").build();
        }
        
        EspaciosResponse response = ps.consultarEspacios();
        
        if (response.getEspaciosLibres() == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al conectar con la base de datos").build();
        }

        if (response.getEspaciosLibres().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No hay espacios disponibles en este momento").build();
        }

        return Response.ok(response).build();
    }
    
    
    @POST
    @Path("/entrada")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarEntrada(@HeaderParam("Authorization") String authHeader,
                                      EntradaRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token requerido").build();
        }
        String token = authHeader.substring(7);
        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token inválido").build();
        }

        EntradaResponse response = ps.registrarEntrada(request, token);

        if (response.getMensaje() != null && response.getMensaje().startsWith("Error")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response.getMensaje()).build();
        }

        return Response.ok(response).build();
    }
    
    
    @PUT
    @Path("/salida")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarSalida(@HeaderParam("Authorization") String authHeader,
                                     SalidaRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token requerido").build();
        }
        String token = authHeader.substring(7);
        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token inválido").build();
        }

        SalidaResponse response = ps.registrarSalida(request, token);

        if (response.getMensaje() != null && response.getMensaje().startsWith("Error")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response.getMensaje()).build();
        }

        return Response.ok(response).build();
    }
}