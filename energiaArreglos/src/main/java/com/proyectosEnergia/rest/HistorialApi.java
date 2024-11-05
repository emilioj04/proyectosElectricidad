package com.proyectosEnergia.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.services.HistorialServices;
 import com.proyectosEnergia.models.Historial;

@Path("/historial")
public class HistorialApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllHistorial() {
        String responseJson = "";
        HistorialServices hs = new HistorialServices();
        Gson gson = new Gson();
        
        try {
            responseJson = "{\"data\":\"success!\",\"info\":" + 
            gson.toJson(hs.getAll().toArray()) + "}";            
        } catch (Exception e) {
            e.printStackTrace();
            responseJson = "{\"data\":\"ErrorMsg\",\"info\":\"" + e.getMessage() + "\"}"; 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson).build();
        }

        return Response.ok(responseJson).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getHistorialById(@PathParam("id") Integer id) {
        String jsonResponse = "";
        HistorialServices hs = new HistorialServices();
        
        try {
            jsonResponse = "{\"data\":\"success!\",\"info\":" + 
            hs.toJson() + "}";            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
        }

        return Response.ok(jsonResponse).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createHistorial")
    public Response createHistorial(String json) {
        String jsonResponse = "";
        HistorialServices hs = new HistorialServices();
        Gson gson = new Gson();
        try {
            Historial historial = gson.fromJson(json, Historial.class);
            hs.save(historial);
            jsonResponse = "{\"data\":\"Historial created!\",\"info\":" 
            + hs.toJson() + "}";
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
        }
        
        return Response.ok(jsonResponse).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteHistorial(@PathParam("id") Integer id) {
        String jsonResponse = "";
        HistorialServices hs = new HistorialServices();
        
        try {
            hs.deleteHistorial(id);
            jsonResponse = "{\"data\":\"Historial deleted!\"}";
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
        }

        return Response.ok(jsonResponse).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateHistorial(@PathParam("id") Integer id, String json) {
        String jsonResponse = "";
        HistorialServices hs = new HistorialServices();
        Gson gson = new Gson();
        
        try {
            Historial historial = gson.fromJson(json, Historial.class);
            hs.updateHistorial(historial, id);
            jsonResponse = "{\"data\":\"Historial updated!\",\"info\":" 
            + hs.toJson() + "}";
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
        }
        
        return Response.ok(jsonResponse).build();
    }
}