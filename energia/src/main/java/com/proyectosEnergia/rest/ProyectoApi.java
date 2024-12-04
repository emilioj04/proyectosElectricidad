package com.proyectosEnergia.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.proyectosEnergia.controller.dao.services.ProyectoServices;
import com.proyectosEnergia.controller.dao.services.RegistroServices;
import com.proyectosEnergia.models.Proyecto;

@Path("/proyecto")
public class ProyectoApi {

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response getAllProyectos() throws Exception {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        RegistroServices rs = new RegistroServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay proyectos en la base de datos");
        }

        rs.getRegistro().setNombre("Proyecto");
        rs.getRegistro().setTipo("Consulta de proyectos");
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        rs.save();

        return Response.ok(map).build();
    }

   
    @Path("/get/{id}") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyecto(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        RegistroServices rs = new RegistroServices();
        
        try {
            ps.setProyecto(ps.get(id));            
        } catch (Exception e) {
            //Todo: handle exception
        }
        map.put("msg", "OK");
        map.put("data", ps.getProyecto());
        if (ps.getProyecto().getId() == null) {
            map.put("data", "No existe el proyecto con ese id");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        rs.getRegistro().setNombre("Proyecto");
        rs.getRegistro().setTipo("Consulta: "+ ps.getProyecto().getNombre());
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        rs.save();

        return Response.ok(map).build();
    }

    @Path("/listType")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");
        map.put("data", ps.getTipos());
        return Response.ok(map).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        System.out.println("**** " + a);
        try {
            ProyectoServices ps = new ProyectoServices();
            RegistroServices rs = new RegistroServices();
            ps.getProyecto().setNombre(map.get(("nombre")).toString());
            ps.getProyecto().setTipoEnergia(ps.getTipoEnergia(map.get("tipoEnergia").toString().toUpperCase()));
            ps.getProyecto().setTiempoConstruccion(Integer.parseInt(map.get("tiempoConstruccion").toString()));
            ps.getProyecto().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ps.getProyecto().setInversionTotal(Double.parseDouble(map.get("inversionTotal").toString()));
            ps.getProyecto().setCapacidadGeneracionDiaria(Double.parseDouble(map.get("capacidadGeneracionDiaria").toString()));
            ps.getProyecto().setCostoGeneracionDiaria(Double.parseDouble(map.get("costoGeneracionDiaria").toString()));
            ps.save();

            rs.getRegistro().setNombre("Proyecto");
            rs.getRegistro().setTipo("Creacion: "+ ps.getProyecto().getNombre());
            rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
            rs.save();
            
                        
            res.put("msg", "OK");
            res.put("data", "Proyecto guardado correctamente");
            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProyecto(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            ProyectoServices ps = new ProyectoServices();
            RegistroServices rs = new RegistroServices();

            Integer id = Integer.parseInt(map.get("id").toString());
            Proyecto proyecto = ps.get(id);

            if (proyecto == null || proyecto.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe el proyecto con ese id");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            ps.setProyecto(proyecto);
            ps.delete();

            res.put("msg", "OK");
            res.put("data", "Proyecto eliminado correctamente");

            rs.getRegistro().setNombre("Proyecto");
            rs.getRegistro().setTipo("Eliminacion: "+ ps.getProyecto().getNombre());
            rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
            rs.save();

            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
        
    }
    
    @Path("/update/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProyecto(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            ProyectoServices ps = new ProyectoServices();
            RegistroServices rs = new RegistroServices();
            ps.setProyecto(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getProyecto().setNombre(map.get(("nombre")).toString());
            ps.getProyecto().setTipoEnergia(ps.getTipoEnergia(map.get("tipoEnergia").toString().toUpperCase()));
            ps.getProyecto().setTiempoConstruccion(Integer.parseInt(map.get("tiempoConstruccion").toString()));
            ps.getProyecto().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ps.getProyecto().setInversionTotal(Double.parseDouble(map.get("inversionTotal").toString()));
            ps.getProyecto().setCapacidadGeneracionDiaria(Double.parseDouble(map.get("capacidadGeneracionDiaria").toString()));
            ps.getProyecto().setCostoGeneracionDiaria(Double.parseDouble(map.get("costoGeneracionDiaria").toString()));
            ps.update();

            rs.getRegistro().setNombre("Proyecto");
            rs.getRegistro().setTipo("Actualizacion: "+ ps.getProyecto().getNombre());
            rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
            rs.save();
            
            res.put("msg", "OK");
            res.put("data", "Proyecto actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}

