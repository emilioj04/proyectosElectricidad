package com.proyectosEnergia.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.dao.services.InversionServices;
import com.proyectosEnergia.controller.dao.services.InversionistaServices;
import com.proyectosEnergia.controller.dao.services.ProyectoServices;
import com.proyectosEnergia.controller.dao.services.RegistroServices;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversion;
import com.proyectosEnergia.models.Proyecto;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.ws.rs.core.Response;

/**
 * Clase principal.
 */
public class Main {
    // URI base que el servidor HTTP Grizzly escuchará
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Inicia el servidor HTTP Grizzly exponiendo los recursos JAX-RS definidos en esta aplicación.
     * @return Servidor HTTP Grizzly.
     */
    public static HttpServer startServer() {
        // Crear una configuración de recursos que escanea los recursos JAX-RS y proveedores
        // en el paquete com.proyectosEnergia.rest
        final ResourceConfig rc = new ResourceConfig().packages("com.proyectosEnergia.rest");

        // Crear y iniciar una nueva instancia del servidor HTTP Grizzly
        // exponiendo la aplicación Jersey en BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Método principal.
     * @param args Argumentos de línea de comandos
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {     
        
        final HttpServer server = startServer();
        System.out.println(String.format("La aplicación Jersey se ha iniciado con WADL disponible en "
                + "%sapplication.wadl\nPresiona enter para detenerlo...", BASE_URI));

        /*
        ProyectoServices is = new InversionistaServices();
        is.getInversionista().setNombres("Juan Alberto");
        is.getInversionista().setApellidos("Perez martinez");
        is.getInversionista().setIdentificacion("1234567890");
        is.getInversionista().setTipoIdentificacion(is.getTipoIdentificacion("CEDULA"));
        is.getInversionista().setTipoInversionista(is.getTipoInversionista("Simple"));
        is.save();

        System.out.println("Nombre antes de guardar: " + is.getInversionista().getNombres());
        
         
        ProyectoServices ps = new ProyectoServices();
        ps.getProyecto().setNombre("Proyecto 1");
        ps.getProyecto().setTipoEnergia(ps.getTipoEnergia("EOLICA"));
        ps.getProyecto().setTiempoConstruccion(12);
        ps.getProyecto().setTiempoVida(20);
        //darle un valor flotante a la inversion total
        ps.getProyecto().setInversionTotal(100.25f);
        ps.getProyecto().setCostoGeneracionDiaria(12.5f);
        ps.getProyecto().setCapacidadGeneracionDiaria(15.55f);
        ps.save();

        System.out.println("Nombre antes de guardar: " + ps.getProyecto().getNombre());

        RegistroServices  rs = new RegistroServices();
        rs.getRegistro().setNombre("Proyecto");
        rs.getRegistro().setTipo("Creacion: "+ ps.getProyecto().getNombre());
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));

        rs.save();

        */

        InversionServices is = new InversionServices();
        is.getInversion().setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        is.getInversion().setIdInversionista(1);
        is.getInversion().setIdProyecto(1);
        is.getInversion().setMontoInvertido(100.25d);
        is.save();

        RegistroServices rs = new RegistroServices();
        rs.getRegistro().setNombre("Inversion");
        rs.getRegistro().setTipo("Creacion: "+ is.getInversion().getFecha());
        rs.getRegistro().setHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd//HH:mm")));
        



        System.in.read();
        server.shutdownNow();
    }
}
