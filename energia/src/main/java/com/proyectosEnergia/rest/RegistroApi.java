package com.proyectosEnergia.rest;

import java.time.LocalDate;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.proyectosEnergia.controller.dao.services.InversionistaServices;
import com.proyectosEnergia.controller.dao.services.RegistroServices;

@Path("/registro")
public class RegistroApi {

@Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInversionistas() throws Exception {
        HashMap map = new HashMap<>();
        RegistroServices rs = new RegistroServices();
        map.put("msg", "OK");
        map.put("data", rs.listAll().toArray());
        if (rs.listAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay inversionistas en la base de datos");

        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveRegistro() throws Exception {
        HashMap map = new HashMap<>();
        try {
            RegistroServices rs = new RegistroServices();  
            rs.getRegistro().setNombre(map.get("nombre").toString());
            rs.getRegistro().setTipo(map.get("tipo").toString());
            rs.getRegistro().setHora(LocalDate.now().toString());
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
        }
        return Response.ok(map).build();
    }
    
}
