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
import com.proyectosEnergia.controller.dao.services.InversionServices;
import com.proyectosEnergia.controller.dao.services.HistorialServices;
import com.proyectosEnergia.models.Historial;
import com.proyectosEnergia.models.Inversion;

@Path("/inversion")
public class InversionApi {

    private final HistorialServices historialServices;
    private final InversionServices inversionServices;

    public InversionApi() {
        historialServices = new HistorialServices();
        inversionServices = new InversionServices();
        System.out.println("InversionApi initialized. historialServices: " + historialServices);
    }

    private void checkServices() {
        if (historialServices == null) {
            throw new IllegalStateException("HistorialServices is not initialized.");
        }
        if (inversionServices == null) {
            throw new IllegalStateException("InversionServices is not initialized.");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllInversions() {
        String responseJson = "";
        Gson gson = new Gson();
        
        try {
            responseJson = "{\"data\":\"success!\",\"info\":" + 
            gson.toJson(inversionServices.getAll().toArray()) + "}";            
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
        
        try {
            jsonResponse = "{\"data\":\"success!\",\"info\":" + 
            inversionServices.getInversionJsonById(id) + "}";            
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
    @Path("/createInversion")
    public Response createInversion(String json) {
        String jsonResponse = "";
        Gson gson = new Gson();
        try {
            Inversion inversion = gson.fromJson(json, Inversion.class);
            inversionServices.setInversion(inversion);
            inversionServices.save(inversion);

            int historialId = historialServices.getNextId();
            Historial historial = new Historial(historialId, "CREATE", String.valueOf(System.currentTimeMillis()), gson.toJson(inversion));
            historialServices.save(historial);

            jsonResponse = "{\"data\":\"Inversion created!\",\"info\":" + inversionServices.toJson() + "}";
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info \":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
        }

        return Response.ok(jsonResponse).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateInversion(@PathParam("id") Integer id, String json) {
        String jsonResponse = "";
        Gson gson = new Gson();
        try {
            Inversion inversion = gson.fromJson(json, Inversion.class);
            inversionServices.updateInversion(inversion, id);

            Historial historial = new Historial(historialServices.getNextId(), "UPDATE", String.valueOf(System.currentTimeMillis()), gson.toJson(inversion));
            historialServices.save(historial);

            jsonResponse = "{\"data\":\"Inversion updated!\",\"info\":" + inversionServices.toJson() + "}";
        } catch (Exception e) {
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
        }

        return Response.ok(jsonResponse).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteInversion(@PathParam("id") Integer id) {
        String jsonResponse = "";
        try {
            inversionServices.deleteInversion(id);

            Historial historial = new Historial(historialServices.getNextId(), "DELETE", String.valueOf(System.currentTimeMillis()), "Inversion ID: " + id);
            historialServices.save(historial);

            jsonResponse = "{\"data\":\"Inversion deleted!\"}";
        } catch (Exception e) {
            jsonResponse = "{\"data\":\"ErrorMsg\",\"info\":\"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
        }

        return Response.ok(jsonResponse).build();
    }
}