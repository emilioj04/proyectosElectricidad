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
import com.proyectosEnergia.controller.dao.services.InversionistaServices;
import com.proyectosEnergia.models.Inversionista;

@Path("/inversionista")
public class InversionistaApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllInversionistas() {
        String responseJson = "";
        InversionistaServices is = new InversionistaServices();
        Gson gson = new Gson();
        
        try {
            responseJson = "{\"data\":\"success!\",\"info\":" + 
            gson.toJson(is.listAll().toArray()) + "}";            
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
    public Response getInversionistaById(@PathParam("id") Integer id) {
        String jsonResponse = "";
        InversionistaServices is = new InversionistaServices();
        
        try {
            jsonResponse = "{\"data\":\"success!\",\"info\":" + 
            is.getInversionistaJsonById(id) + "}";            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
        }

        return Response.ok(jsonResponse).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createInversionista")
    public Response createInversionista(String json) {
        String responseJson = "";
        InversionistaServices is = new InversionistaServices();
        Gson gson = new Gson();
        
        try {
            Inversionista inversionista = gson.fromJson(json, Inversionista.class);
            is.save(inversionista);
            responseJson = "{\"message\":\"Inversionista created successfully!\"}";
            return Response.ok(responseJson).build();
        } catch (Exception e) {
            responseJson = "{\"error\":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteInversionista(@PathParam("id") Integer id) {
        String responseJson = "";
        InversionistaServices is = new InversionistaServices();
        
        try {
            is.deleteInversionista(id);
            responseJson = "{\"message\":\"Inversionista deleted successfully!\"}";
            return Response.ok(responseJson).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseJson = "{\"error\":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateInversionista(@PathParam("id") Integer id, String json) {
        String responseJson = "";
        InversionistaServices is = new InversionistaServices();
        Gson gson = new Gson();
        
        try {
            Inversionista inversionista = gson.fromJson(json, Inversionista.class);
            is.updateInversionista(inversionista, id);
            responseJson = "{\"message\":\"Inversionista updated successfully!\"}";
            return Response.ok(responseJson).build();
        } catch (Exception e) {
            e.printStackTrace();
            responseJson = "{\"error\":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson).build();
        }
    }


}