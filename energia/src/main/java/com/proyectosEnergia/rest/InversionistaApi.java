package com.proyectosEnergia.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.services.InversionistaServices;
import com.proyectosEnergia.controller.dao.services.RegistroServices;
import com.proyectosEnergia.models.Inversionista;


@Path("/inversionista")
public class InversionistaApi {
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInversionistas() throws Exception {
        HashMap map = new HashMap<>();
        InversionistaServices is = new InversionistaServices();
        RegistroServices rs = new RegistroServices();
        map.put("msg", "OK");
        map.put("data", is.listAll().toArray());
        if (is.listAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay inversionistas en la base de datos");

        }

        rs.getRegistro().setNombre("Inversionista");
        rs.getRegistro().setTipo("Consulta");
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        rs.save();

        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInversionista(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        InversionistaServices is = new InversionistaServices();
        RegistroServices rs = new RegistroServices();
        
        try {
            is.setInversionista(is.get(id));            
        } catch (Exception e) {
            //Todo: handle exception
        }
        map.put("msg", "OK");
        map.put("data", is.getInversionista());
        if (is.getInversionista().getId() == null) {
            map.put("data", "No existe el inversionista con ese id");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        rs.getRegistro().setNombre("Inversionista");
        rs.getRegistro().setTipo("Consulta "+ is.getInversionista().getNombre() + " " + is.getInversionista().getApellido() +""+ is.getInversionista().getIdentificacion());
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        rs.save();
        
        return Response.ok(map).build();
    }

    @Path("/listTypeIdentificacion")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        InversionistaServices is = new InversionistaServices();
        map.put("msg", "OK");
        map.put("data", is.getTipos());
        return Response.ok(map).build();
    }

    @Path("/listTypeInversionista")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTypeInversionista() {
        HashMap map = new HashMap<>();
        InversionistaServices is = new InversionistaServices();
        map.put("msg", "OK");
        map.put("data", is.getTiposInversionista());
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
            System.out.println("**** " + a);

            try {
                InversionistaServices is = new InversionistaServices();
                RegistroServices rs = new RegistroServices();
                is.getInversionista().setNombre(map.get(("nombre")).toString());
                is.getInversionista().setApellido(map.get(("apellido")).toString());
                is.getInversionista().setTipoIdentificacion(is.getTipoIdentificacion(map.get(("tipoIdentificacion")).toString().toUpperCase()));
                is.getInversionista().setIdentificacion(map.get(("identificacion")).toString());
                is.getInversionista().setTipoInversionista(is.getTipoInversionista(map.get(("tipoInversionista")).toString().toUpperCase()));
                is.save();
                res.put("msg", "ok");
                res.put("data", "Persona registrada correctamente");
                
                rs.getRegistro().setNombre("Inversionista");
                rs.getRegistro().setTipo("Creacion "+ is.getInversionista().getNombre() + " " + is.getInversionista().getApellido() +""+ is.getInversionista().getIdentificacion());
                rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm"))); 
                rs.save();
                    
                return Response.ok(map).build();
            } catch (Exception e) {
                System.out.println("Error en sav data " + e.toString());
                res.put("msg", "Error");
                res.put("data", e.toString());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            }
            
        }

    
    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInversionista(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            InversionistaServices is = new InversionistaServices();
            RegistroServices rs = new RegistroServices();

            Integer id =  Integer.parseInt(map.get("id").toString());
            Inversionista inv = is.get(id);

            if (inv == null || inv.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe el inversionista con ese id");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
            //
            is.setInversionista(inv);
            is.delete();
            
            res.put("msg", "ok");
            res.put("data", "Inversionista eliminado correctamente");

            rs.getRegistro().setNombre("Inversionista");
            rs.getRegistro().setTipo("Eliminacion "+ is.getInversionista().getNombre() + " " + is.getInversionista().getApellido() + " id:" + is.getInversionista().getId());
            rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
            rs.save();

            return Response.ok(res).build();
        } catch (Exception e) {
            System.out.println("Error en sav data EN DELETE" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            InversionistaServices is = new InversionistaServices();
            RegistroServices rs = new RegistroServices();
            is.setInversionista(is.get(Integer.parseInt(map.get("id").toString())));
            is.getInversionista().setApellido(map.get(("apellido")).toString());
            is.getInversionista().setNombre(map.get(("nombre")).toString());
            is.getInversionista().setTipoIdentificacion(is.getTipoIdentificacion(map.get(("tipoIdentificacion")).toString().toUpperCase()));
            is.getInversionista().setIdentificacion(map.get(("identificacion")).toString());
            is.getInversionista().setTipoInversionista(is.getTipoInversionista(map.get(("tipoInversionista")).toString().toUpperCase()));
            is.update();
            res.put("msg", "ok");
            res.put("data", "Persona registrada correctamente");

            rs.getRegistro().setNombre("Inversionista");
            rs.getRegistro().setTipo("Actualizacion "+ is.getInversionista().getNombre() + " " + is.getInversionista().getApellido() +""+ is.getInversionista().getIdentificacion()+ " id:" + is.getInversionista().getId());
            rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
            rs.save();

            return Response.ok(map).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}