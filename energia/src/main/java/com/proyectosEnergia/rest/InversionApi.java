package com.proyectosEnergia.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.proyectosEnergia.controller.tda.list.LinkedList;
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

    //Ordenar proyectos por shellSort por atributo y orden descendente o ascendente
    @Path("/ordenar/shellSort/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByShellSort (@PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        map.put("msg", "ok");
        try {
            LinkedList lista = is.listAll().ordenarShellSort(atributo, orden);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("msg", "No hay inversionistas en la base de datos");
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();

        }

        return Response.ok(map).build();

    }

    //Ordenar proyectos por mergeSort por atributo y orden descendente o ascendente
    @Path("/ordenar/mergeSort/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByMergeSort (@PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        map.put("msg", "ok");
        try {
            LinkedList lista = is.listAll().ordenarMergeSort(atributo, orden);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("msg", "No hay inversionistas en la base de datos");
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();

        }

        return Response.ok(map).build();

    }

    //Ordenar proyectos por quickSort por atributo y orden descendente o ascendente
    @Path("/ordenar/quickSort/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByQuickSort (@PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        map.put("msg", "ok");
        try {
            LinkedList lista = is.listAll().ordenarQuickSort(atributo, orden);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("msg", "No hay inversionistas en la base de datos");
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();

        }

        return Response.ok(map).build();

    }

    //Buscar inversionista por atributo y valor
    @Path("/busqueda/lineal/{atributo}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchLineal(@PathParam("atributo") String atributo, @PathParam("valor") String valor) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        try {
            LinkedList lista = is.listAll().linealSearch(atributo, valor);
            map.put("data", lista.toArray());

            if (lista.isEmpty()) {
                map.put("msg", "No hay inversionistas en la base de datos");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
                } 
            
            
        } catch (Exception e) { 
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        return Response.ok(map).build();
    }

    //Buscar inversionista por atributo y valor
    @Path("/busqueda/binaria/{atributo}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBinary(@PathParam("atributo") String atributo, @PathParam("valor") String valor) {
        HashMap map = new HashMap<>();
        InversionServices is = new InversionServices();
        try {
            LinkedList lista = is.listAll().binarySearch(atributo, valor);
            map.put("data", lista.toArray());

            if (lista.isEmpty()) {
                map.put("msg", "No hay Proyectos en la base de datos");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            } 
            
        } catch (Exception e) { 
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

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
            if (map.get("id") == null) {
                res.put("msg", "ERROR");
                res.put("data", "El ID de la inversión es requerido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            InversionServices is = new InversionServices();
            is.setInversion(is.get(Integer.parseInt(map.get("id").toString())));

            if (is.getInversion() == null || is.getInversion().getId() == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe la inversión con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (map.get("investor") != null && map.get("project") != null) {
                InversionistaServices inversionistaServices = new InversionistaServices();
                ProyectoServices proyectoServices = new ProyectoServices();
                
                inversionistaServices.setInversionista(inversionistaServices.get(Integer.parseInt(map.get("investor").toString())));

                if (inversionistaServices.getInversionista() == null) {
                    res.put("msg", "ERROR");
                    res.put("data", "No existe el inversionista con el ID proporcionado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
                
                proyectoServices.setProyecto(proyectoServices.get(Integer.parseInt(map.get("project").toString())));

                if (proyectoServices.getProyecto() == null) {
                    res.put("msg", "ERROR");
                    res.put("data", "No existe el proyecto con el ID proporcionado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }

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
            System.out.println("Error en update data: " + e.getMessage());
            e.printStackTrace();
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

}
