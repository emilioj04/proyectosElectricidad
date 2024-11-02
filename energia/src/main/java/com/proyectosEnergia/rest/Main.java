package com.proyectosEnergia.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversion;
import com.proyectosEnergia.models.Inversionista;
import com.proyectosEnergia.models.Proyecto;

import java.io.IOException;
import java.net.URI;

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
     * @throws IOException Excepción de entrada/salida
     */
    public static void main(String[] args) throws IOException {     
        
        final HttpServer server = startServer();
        System.out.println(String.format("La aplicación Jersey se ha iniciado con WADL disponible en "
                + "%sapplication.wadl\nPresiona enter para detenerlo...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}
