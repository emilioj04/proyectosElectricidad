package com.proyectosEnergia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.services.InversionServices;
import com.proyectosEnergia.models.Inversion;

@Path("/inversion")
public class InversionApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllInversions(){
        String responseJson = "";
        InversionServices is = new InversionServices();
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
    public Response getInversionById(@PathParam("id") Integer id) {
        String jsonResponse = "";
        InversionServices is = new InversionServices();
        
        try {
            jsonResponse = "{\"data\":\"success!\",\"info\":" + 
            is.getInversionJsonById(id) + "}";            
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
    @Path("/createInversion")
    public Response createInversion(String json) {
        String jsonResponse = "";
        InversionServices is = new InversionServices();
        Gson gson = new Gson();
        try {
            Inversion inv = gson.fromJson(json, Inversion.class);
            is.setInversion(inv);
            is.save(inv);
            jsonResponse = "{\"data\":\"Inversion created!\",\"info\":" 
            + is.toJson() + "}";
            
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
    public Response deleteInversion(@PathParam("id") Integer id) {
        String jsonResponse = "";
        InversionServices is = new InversionServices();
        
        try {
            is.deleteInversion(id);
            jsonResponse = "{\"data\":\"Inversion deleted!\"}";
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + 
            e.getMessage() + "\"}"; 
        }

        return Response.ok(jsonResponse).build();
    }

}
