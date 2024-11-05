package com.proyectosEnergia.controller.dao.implement;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;

public class AdapterDao<T> implements InterfazDao<T> {
    private Class<T> clazz;
    private Gson gson;
    public static String URL = "media/";

    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.gson = new Gson();
    }

    private String readFile() throws Exception {
        Scanner in = new Scanner(new FileReader(URL + clazz.getSimpleName() + ".json"));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        String data = sb.toString();
        return data;
    }

    @Override
    public Arreglo<T> getAll() {
        Arreglo<T> arreglo = new Arreglo<>(); 
        try {
            String data = readFile();
            T[] matriz = (T[]) gson.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            for (T item : matriz) {
                arreglo.add(item); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arreglo;
    }

    @Override
    public void persist(T obj) throws Exception {
        Arreglo<T> arreglo = getAll();
        arreglo.add(obj);
        String info = gson.toJson(arreglo.toArray());
        saveFile(info);
    }

    @Override
    public void merge(T obj, Integer index) throws Exception {
        Arreglo<T> arreglo = getAll();
        arreglo.update(obj, index);
        String info = gson.toJson(arreglo.toArray());
        saveFile(info);
    }

    @Override
    public T get(Integer id) throws Exception {
        Arreglo<T> arreglo = getAll();
        if (id >= 0 && id < arreglo.getSize()) {
            return arreglo.get(id); 
        }
        return null;
    }

    @Override
    public void delete(Integer id) throws Exception {
        Arreglo<T> arreglo = getAll();
        if (id >= 0 && id < arreglo.getSize()) {
            arreglo.delete(id); 
            String info = gson.toJson(arreglo.toArray());
            saveFile(info);
        }
    }


    public void saveFile(String data) throws Exception {
        FileWriter file = new FileWriter(URL + clazz.getSimpleName() + ".json");
        file.write(data);
        file.flush();
        file.close();
    }
}
