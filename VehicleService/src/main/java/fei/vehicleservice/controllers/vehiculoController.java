/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.controllers;

import fei.vehicleservice.dto.VehiculoRequest;
import fei.vehicleservice.dto.VehiculoResponse;
import fei.vehicleservice.dto.VehiculoStatusRequest;
import fei.vehicleservice.services.vehiculoService;
import fei.vehicleservice.utils.JwtUtils;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Ainz Oal Gown
 */
@Path("/vehiculos")
public class vehiculoController {
    private vehiculoService us;

    public vehiculoController() {
        this.us = new vehiculoService();
    }

    @GET
    @Path("usuario/vehiculos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorUsuario(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token requerido").build();
        }

        String token = authHeader.substring(7);

        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token invalido").build();
        }

        Integer idUsuarioAutenticado = JwtUtils.getIdUsuarioFromToken(token);

        if (idUsuarioAutenticado == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No se pudo obtener el usuario del token").build();
        }

        VehiculoResponse response = us.buscarPorUsuario(idUsuarioAutenticado);
        if (response.isError()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(
            @HeaderParam("Authorization") String authHeader,VehiculoRequest request){

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token requerido").build();
        }
        String token = authHeader.substring(7);

        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token invalido").build();
        }

        Integer idUsuarioAutenticado = JwtUtils.getIdUsuarioFromToken(token);

        if (idUsuarioAutenticado == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No se pudo obtener el usuario del token").build();
        }

        VehiculoResponse response = us.registrar(idUsuarioAutenticado, request);

        if (response.isError()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        return Response.ok(response).build();
    }

    @PUT
    @Path("editar/{idVehiculo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editar(@HeaderParam("Authorization") String authHeader,
            @PathParam("idVehiculo") Integer idVehiculo,VehiculoRequest request){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token requerido").build();
        }
        
        String token = authHeader.substring(7);

        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token invalido").build();
        }
        Integer idUsuarioAutenticado = JwtUtils.getIdUsuarioFromToken(token);

        if (idUsuarioAutenticado == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No se pudo obtener el usuario del token").build();
        }

        VehiculoResponse response = us.editar(idVehiculo,idUsuarioAutenticado,request);
        if (response.isError()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        return Response.ok(response).build();
    }

    @PUT
    @Path("/estatus/{idVehiculo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarEstatus(@HeaderParam("Authorization") String authHeader,
            @PathParam("idVehiculo") Integer idVehiculo,VehiculoStatusRequest request){
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token requerido").build();
        }

        String token = authHeader.substring(7);
        if (!JwtUtils.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token invalido").build();
        }

        Integer idUsuarioAutenticado = JwtUtils.getIdUsuarioFromToken(token);
        if (idUsuarioAutenticado == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No se pudo obtener el usuario del token").build();
        }
        
        VehiculoResponse response = us.cambiarEstatus(idVehiculo,idUsuarioAutenticado,request);
        if (response.isError()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        return Response.ok(response).build();
    }
}
