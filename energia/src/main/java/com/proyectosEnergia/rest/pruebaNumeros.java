package com.proyectosEnergia.rest;

import java.util.Random;

import com.proyectosEnergia.controller.tda.list.LinkedList;

public class pruebaNumeros {

    //CREAR NUMEROS ALEATORIOS EN UNA LISTA
    static LinkedList<Integer> crearLista(Integer size) {
        Integer[] arr = new Integer[size];
        LinkedList<Integer> lista = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000);
        }
        lista.toList(arr);
        return lista;
    }

    static Double testOrder(LinkedList<Integer> lista, String metodo, Integer tipo) throws Exception {
        Long total;
        Long start = System.nanoTime();
        switch (metodo) {
            case "shell":
                lista.orderByShellSort(tipo);
                break;
            case "merge":
                lista.orderByMergeSort(tipo);
                break;
            case "quick":
                lista.orderByQuickSort(tipo);
                break;
            default:
                break;
        }
        Long end = System.nanoTime();
        total = (end - start);
        return total / 1000000.0;
    }

    static Double testSearch(LinkedList<Integer> lista, String metodo, Object valor) throws Exception {
        Long start = System.nanoTime();
        switch (metodo) {
            case "m_lineal":
                lista.multipleLinealSearch(valor);
                break;
            case "m_binary":
                lista.multipleBinarySearch(valor);
                break;
            case "a_lineal":
                lista.atomicLinealSearch(valor);
                break;
            case "a_binary":
                lista.atomicLinealSearch(valor);
                break;
            default:
                break;
        }
        Long end = System.nanoTime();
        return (end - start)/1.0;
    }

    public static void main(String[] args) {
        LinkedList<Integer> lista = crearLista(10000);
        try {
            System.out.println("Shell: " + testOrder(lista, "shell", 0) + " ms");
            System.out.println("Merge: " + testOrder(lista, "merge", 0) + " ms");
            System.out.println("Quick: " + testOrder(lista, "quick", 0) + " ms");
            System.out.println("Multiple Lineal: " + testSearch(lista, "lineal", 200) + " ns");
            System.out.println("Multiple Binary: " + testSearch(lista, "binary", 200) + " ns");
            System.out.println("Atomic Lineal: " + testSearch(lista, "lineal", 666) + " ns");
            System.out.println("Atomic Binary: " + testSearch(lista, "binary", 666) + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
