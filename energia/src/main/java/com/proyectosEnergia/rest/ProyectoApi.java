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
import com.proyectosEnergia.controller.dao.services.ProyectoServices;
import com.proyectosEnergia.controller.dao.services.RegistroServices;
import com.proyectosEnergia.controller.tda.list.LinkedList;
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

    //Ordenar Proyectos por ShellSort por atributo y orden
    @Path("/ordenar/shellSort/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByShellSort (@PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "ok");
        try {
            LinkedList data = ps.listAll().ordenarShellSort(atributo, orden);
            map.put("data", data.toArray());
            if (data.isEmpty()) {
                map.put("msg", "No hay Proyectos en la base de datos");
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();

        }
        return Response.ok(map).build();
    }

    //Ordenar Proyectos por MergeSort por atributo y orden
    @Path("/ordenar/mergeSort/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByMergeSort (@PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "ok");
        try {
            LinkedList data = ps.listAll().ordenarMergeSort(atributo, orden);
            map.put("data", data.toArray());
            if (data.isEmpty()) {
                map.put("msg", "No hay Proyectos en la base de datos");
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();

        }
        return Response.ok(map).build();
    }

    //Ordenar Proyectos por QuickSort por atributo y orden
    @Path("/ordenar/quickSort/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByQuickSort (@PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "ok");
        try {
            LinkedList data = ps.listAll().ordenarQuickSort(atributo, orden);
            map.put("data", data.toArray());
            if (data.isEmpty()) {
                map.put("msg", "No hay Proyectos en la base de datos");
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        return Response.ok(map).build();
    }

    //Busqueda Lineal de Proyectos por atributo y valor
    @Path("/busqueda/lineal/{atributo}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchLineal(@PathParam("atributo") String atributo, @PathParam("valor") String valor) {
        HashMap map = new HashMap<>();
        ProyectoServices is = new ProyectoServices();
        try {
            LinkedList lista = is.listAll().linealSearch(atributo, valor);
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

    //Busqueda Binaria de Proyectos por atributo y valor
    @Path("/busqueda/binaria/{atributo}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBinary(@PathParam("atributo") String atributo, @PathParam("valor") String valor) {
        HashMap map = new HashMap<>();
        ProyectoServices is = new ProyectoServices();
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

