package com.proyectosEnergia.rest;

import java.util.Random;
import java.util.Scanner;

import com.proyectosEnergia.controller.tda.list.LinkedList;

public class pruebaNumeros {
    
    //Crear lista y llenarla con numeros aleatorios
    static LinkedList<Integer> crearLista(Integer size) {
        Integer[] aux = new Integer[size];
        LinkedList<Integer> lista = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            aux[i] = random.nextInt(10000);
        }
        lista.toList(aux);
        return lista;
    }

    static Double pruebaOrden(LinkedList<Integer> lista, String metodo, Integer tipo) throws Exception {
        Long inicio = System.nanoTime();
        switch (metodo) {
            case "shell":
                lista.ordenarShellSort(tipo);
                break;
            case "merge":
                lista.ordenarMergeSort(tipo);
                break;
            case "quick":
                lista.ordenarQuickSort(tipo);
                break;
            default:
                break;
        }
        Long fin = System.nanoTime();
        return (fin - inicio)/1000000.0;
    }
    
    static Long pruebaBusqueda(LinkedList<Integer> lista, String metodo, Object valor) throws Exception {
        Long total;
        Long inicio = System.nanoTime();
        switch (metodo) {
            case "lineal":
                lista.linealSearch(valor);
                break;
            case "binario":
                lista.binarySearch(valor);
                break;
            default:
                break;
        }
        Long fin = System.nanoTime();
        total = (fin - inicio);
        return total;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el tamaño de la lista: ");
        Integer size = sc.nextInt();

        LinkedList<Integer> lista = crearLista(size);
        try {
            
            LinkedList<Integer> listaquick = lista.clone();
            System.out.println("Ordenacion por QuickSort: " + pruebaOrden(listaquick, "quick", 0) + " ms");
            LinkedList<Integer> listashell = lista.clone();
            System.out.println("Ordenacion por ShellSort: " + pruebaOrden(listashell, "shell", 0) + " ms");
            LinkedList<Integer> listamerge = lista.clone();
            System.out.println("Ordenacion por MergeSort: " + pruebaOrden(listamerge, "merge", 0) + " ms");
            LinkedList<Integer> listabusqueda = crearLista(size);
            System.out.println("Busqueda Lineal: " + pruebaBusqueda(listabusqueda, "lineal", 200) + " ns");
            System.out.println("Busqueda Binaria: " + pruebaBusqueda(listabusqueda, "binaria", 200) + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
