package com.proyectosEnergia.rest;

import java.util.HashMap;

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
import com.proyectosEnergia.controller.dao.services.InversionistaServices;
import com.proyectosEnergia.controller.dao.services.ProyectoServices;
import com.proyectosEnergia.models.Inversion;

@Path("/inversion")
public class InversionApi {

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws Exception{
        HashMap map = new HashMap<>();  
        InversionServices is = new InversionServices();
        map.put("msg", "OK");
        map.put("data", is.listAll().toArray());

        if (is.listAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay inversiones en la base de datos");
        }   
        return Response.ok(map).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getInversionById(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        try {
            is.setInversion(is.get(id));
        } catch (Exception e) {
            //Todo: handle exception
        }

        map.put("msg", "OK");
        map.put("data", is.getInversion());
        if(is.getInversion() == null) {
            map.put("data", "No existe la inversion con el id: " + id);
            return Response.status(Response.Status.NOT_FOUND).entity(map).build();
        }
        return Response.ok(map).build();

    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
        public Response save(HashMap map) {
            HashMap res = new HashMap<>();
            Gson g = new Gson();
            String a = g.toJson(map);
            
            try {
                if  (map.get("investor") != null && map.get("project") != null) {
                    InversionistaServices invs = new InversionistaServices();
                    invs.setInversionista(invs.get(Integer.parseInt(map.get("investor").toString())));
                    ProyectoServices pros = new ProyectoServices();
                    pros.setProyecto(pros.get(Integer.parseInt(map.get("project").toString())));
                    if (invs.getInversionista().getId() != null && pros.getProyecto().getId() != null) {
                        InversionServices is = new InversionServices();
                        is.getInversion().setFecha(map.get("fecha").toString());
                        is.getInversion().setMontoInvertido(Double.parseDouble(map.get("montoInvertido").toString()));
                        is.getInversion().setIdInversionista(invs.getInversionista().getId());
                        is.getInversion().setIdProyecto(pros.getProyecto().getId());
                        is.save();

                        res.put("msg", "OK");
                        res.put("data", "Inversion guardada");
                        return Response.ok(res).build();
                    } else {
                        res.put("msg", "ERROR");
                        res.put("data", "No existe el inversionista o el proyecto");
                        return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                    } 
                } else {
                    res.put("msg", "ERROR");
                    res.put("data", "No existe el inversionista o el proyecto");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
            } catch (Exception e) {
                System.out.println("Error en sav data: " + e.getMessage());
                res.put("msg", "ERROR");
                res.put("data", e.toString());
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }            
    }
    /**
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
    */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateInversion(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            InversionServices is = new InversionServices();
            is.setInversion(is.get(Integer.parseInt(map.get("id").toString())));
            if (is.getInversion().getId() == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe la inversion con el id: " + map.get("id").toString());
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            } else {
                if (map.get("investor") != null && map.get("project") != null) {
                    InversionistaServices invs = new InversionistaServices();
                    invs.setInversionista(invs.get(Integer.parseInt(map.get("investor").toString())));
                    ProyectoServices pros = new ProyectoServices();
                    pros.setProyecto(pros.get(Integer.parseInt(map.get("project").toString())));
                    if (invs.getInversionista().getId() != null && pros.getProyecto().getId() != null) {
                        is.getInversion().setFecha(map.get("fecha").toString());
                        is.getInversion().setMontoInvertido(Double.parseDouble(map.get("montoInvertido").toString()));
                        is.getInversion().setIdInversionista(invs.getInversionista().getId());
                        is.getInversion().setIdProyecto(pros.getProyecto().getId());
                        is.update();

                        res.put("msg", "OK");
                        res.put("data", "Inversion actualizada");
                        return Response.ok(res).build();
                    } else {
                        res.put("msg", "ERROR");
                        res.put("data", "No existe el inversionista o el proyecto");
                        return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                    }
                } else {
                    res.put("msg", "ERROR");
                    res.put("data", "No existe el inversionista o el proyecto");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
            }
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }  
        
    }

}
