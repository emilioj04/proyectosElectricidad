package com.proyectosEnergia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.services.ProyectoServices;

import com.proyectosEnergia.models.Proyecto;

@Path("/proyecto")
public class ProyectoApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllProyectos() {
        String responseJson = "";
        ProyectoServices ps = new ProyectoServices();
        Gson gson = new Gson();
        
        try {
            responseJson = "{\"data\":\"success!\",\"info\":" + 
            gson.toJson(ps.getAll().toArray()) + "}";            
        } catch (Exception e) {
            e.printStackTrace();
            responseJson = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
        }

        return Response.ok(responseJson).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getProyectoById(@PathParam("id") Integer id) {
        String jsonResponse = "";
        ProyectoServices ps = new ProyectoServices();
        
        try {
            jsonResponse = "{\"data\":\"success!\",\"info\":" + 
            ps.getProyectoJsonById(id) + "}";            
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
    @Path("/createProyecto")
    public Response createProyecto(String json) {
        String jsonResponse = "";
        ProyectoServices ps = new ProyectoServices();
        Gson gson = new Gson();
        try {
            Proyecto p = gson.fromJson(json, Proyecto.class);
            ps.setProyecto(p);
            ps.save(p);
            jsonResponse = "{\"data\":\"Proyecto created!\",\"info\":" 
            + ps.toJson() + "}";
            
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
    public Response deleteProyecto(@PathParam("id") Integer id) {
        String jsonResponse = "";
        ProyectoServices ps = new ProyectoServices();
        
        try {
            ps.deleteProyecto(id);
            jsonResponse = "{\"data\":\"Proyecto deleted!\"}";
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
    public Response updateProyecto(@PathParam("id") Integer id, String json) {
        String responseJson = "";
        ProyectoServices ps = new ProyectoServices();
        Gson gson = new Gson();
        
        try {
            Proyecto proyecto = gson.fromJson(json, Proyecto.class);
            ps.updateProyecto(proyecto, id);
            responseJson = "{\"message\":\"Proyecto updated successfully!\"}";
            return Response.ok(responseJson).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseJson = "{\"error\":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson).build();
        }
    }
}

