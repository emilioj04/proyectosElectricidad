package com.proyectosEnergia.rest;

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
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.services.InversionServices;
import com.proyectosEnergia.controller.dao.services.InversionistaServices;
import com.proyectosEnergia.controller.dao.services.ProyectoServices;
import com.proyectosEnergia.controller.dao.services.RegistroServices;
import com.proyectosEnergia.models.Inversion;

@Path("/inversion")
public class InversionApi {

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws Exception{
        HashMap map = new HashMap<>();  
        InversionServices is = new InversionServices();
        RegistroServices rs = new RegistroServices();
        map.put("msg", "OK");
        map.put("data", is.listAll().toArray());

        if (is.listAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay inversiones en la base de datos");
        }   
        rs.getRegistro().setNombre("Inversion");
        rs.getRegistro().setTipo("Consulta");
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        rs.save();
        return Response.ok(map).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getInversionById(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        RegistroServices rs = new RegistroServices();
        try {
            is.setInversion(is.get(id));

            rs.getRegistro().setNombre("Inversion");
            rs.getRegistro().setTipo("Consulta de Inversion con idProyecto: " + is.getInversion().getIdProyecto());
            rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
            rs.save();  
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
                        RegistroServices rs = new RegistroServices();
                        is.getInversion().setFecha(map.get("fecha").toString());
                        is.getInversion().setMontoInvertido(Double.parseDouble(map.get("montoInvertido").toString()));
                        is.getInversion().setIdInversionista(invs.getInversionista().getId());
                        is.getInversion().setIdProyecto(pros.getProyecto().getId());
                        is.save();

                        res.put("msg", "OK");
                        res.put("data", "Inversion guardada");

                        rs.getRegistro().setNombre("Inversion");
                        rs.getRegistro().setTipo("Creacion de Inversion con idProyecto: " + is.getInversion().getIdProyecto());
                        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
                        rs.save();  
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
    
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInversion(HashMap map) {
        HashMap res = new HashMap<>();
        
        try {
            InversionServices is = new InversionServices();
            RegistroServices rs = new RegistroServices();

            Integer id = Integer.parseInt(map.get("id").toString());
            Inversion inv = is.get(id);
            
            if (inv == null || inv.getId() == null){
                res.put("mdg", "Error");
                res.put ("data","No existe el inversionista");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            is.setInversion(inv);
            is.delete();

            res.put("msg", "ok");
            res.put("data", "Inversion eliminado correctamente");

            rs.getRegistro().setNombre("Inversion");
            rs.getRegistro().setTipo("Eliminacion de inverison con IdProyecto"+ is.getInversion().getIdProyecto());
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
        // Verificar que el ID esté presente
        if (map.get("id") == null) {
            res.put("msg", "ERROR");
            res.put("data", "El ID de la inversión es requerido");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }

        // Obtener la inversión
        InversionServices is = new InversionServices();
        is.setInversion(is.get(Integer.parseInt(map.get("id").toString())));

        // Verificar si la inversión existe
        if (is.getInversion() == null || is.getInversion().getId() == null) {
            res.put("msg", "ERROR");
            res.put("data", "No existe la inversión con el ID proporcionado");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }

        // Verificar que los parámetros de inversionista y proyecto estén presentes
        if (map.get("investor") != null && map.get("project") != null) {
            InversionistaServices inversionistaServices = new InversionistaServices();
            ProyectoServices proyectoServices = new ProyectoServices();
            
            // Obtener inversionista
            inversionistaServices.setInversionista(inversionistaServices.get(Integer.parseInt(map.get("investor").toString())));
            // Verificar si el inversionista fue encontrado
            if (inversionistaServices.getInversionista() == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe el inversionista con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            
            // Obtener proyecto
            proyectoServices.setProyecto(proyectoServices.get(Integer.parseInt(map.get("project").toString())));
            // Verificar si el proyecto fue encontrado
            if (proyectoServices.getProyecto() == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe el proyecto con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            // Si todo está bien, actualizar la inversión
            is.getInversion().setFecha(map.get("fecha").toString());
            is.getInversion().setMontoInvertido(Double.parseDouble(map.get("montoInvertido").toString()));
            is.getInversion().setIdInversionista(inversionistaServices.getInversionista().getId());
            is.getInversion().setIdProyecto(proyectoServices.getProyecto().getId());
            is.update();

            res.put("msg", "OK");
            res.put("data", "Inversión actualizada");
            return Response.ok(res).build();
        } else {
            res.put("msg", "ERROR");
            res.put("data", "Faltan los parámetros 'investor' o 'project'");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    } catch (Exception e) {
        // Registrar el error completo para facilitar la depuración
        System.out.println("Error en update data: " + e.getMessage());
        e.printStackTrace();  // Añadir el rastreo completo del error para mayor información
        res.put("msg", "ERROR");
        res.put("data", e.toString());
        return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
    }
}

}
