package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Proyecto;
import com.proyectosEnergia.models.Enumerator.TipoEnergia;


public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto Proyecto;
    private LinkedList listAll;

    public ProyectoDao() {
        super(Proyecto.class);
        this.listAll = this.listAll();
    }

    public Proyecto getProyecto() {
        if (Proyecto == null) {
            Proyecto = new Proyecto();
        }
        return Proyecto;
    }

    public void setProyecto(Proyecto Proyecto) {
        this.Proyecto = Proyecto;
    }

    public LinkedList getListAll() {
        return listAll;
    }

    public Boolean save() throws Exception {     
        Integer id = getListAll().getSize() + 1;
        Proyecto.setId(id);
        this.persist(this.Proyecto);
        this.listAll = this.listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getId() - 1);
        this.listAll = this.listAll();
        return true;
    }

    public Boolean delete() throws Exception {
        this.delete(getProyecto().getId());
        this.listAll = this.listAll();
        return true;
    }

    public TipoEnergia getTipoEnergia(String tipo){
        return TipoEnergia.valueOf(tipo);
    }

    public TipoEnergia[] getTipos(){
        return TipoEnergia.values();
    }

    public LinkedList orderInserSort(Integer type_order, String atributo){
        LinkedList listita = listAll();
        if (!listAll().isEmpty()){
            Proyecto[] lista = (Proyecto[]) listita.toArray();
            listita.reset();
            for (int i = 1; i < lista.length; i++){
                Proyecto aux = lista[i];   
                int j = i - 1;
                while (j >= 0 && (verify(lista[j], aux, type_order, atributo))){
                    lista[j + 1] = lista[j--];
                }
                lista[j + 1] = aux;
            } 
            listita.toList(lista);
        }
        return listita;
    }

    private Boolean verify(Proyecto a, Proyecto b, Integer type_order, String atributo){
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) > 0;
            }  else if (atributo.equalsIgnoreCase("id")) {
                return a.getId() > b.getId();
            }
        } else {
            if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) < 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getId() < b.getId();
            }
        }
        return false;
    }

    public LinkedList<Proyecto> buscar_Nombre (String texto) {
        LinkedList<Proyecto> lista = new LinkedList<>();
        LinkedList<Proyecto> listita = listAll();
        if (!listita.isEmpty()) {
            Proyecto[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {

                if (aux[i].getNombre().toLowerCase().startsWith(texto.toLowerCase())) {
                    //System.out.println("**** "+aux[i].get);
                    lista.add(aux[i]);
                }
            }
        }
        return lista;
    }

    public LinkedList orderMergeSort(Integer type_order, String atributo){
        LinkedList listita = listAll();
        if (!listAll().isEmpty()){
            Proyecto[] lista = (Proyecto[]) listita.toArray();
            listita.reset();
            mergeSort(lista, type_order, atributo);
            listita.toList(lista);
        }
        return listita;
    }

    private void mergeSort(Proyecto[] lista, Integer type_order, String atributo){
        if (lista.length > 1){
            int med = lista.length / 2;
            Proyecto[] izquierda = new Proyecto[med];
            Proyecto[] derecha = new Proyecto[lista.length - med];
            for (int i = 0; i < med; i++){
                izquierda[i] = lista[i];
            }
            for (int i = med; i < lista.length; i++){
                derecha[i - med] = lista[i];
            }
            mergeSort(izquierda, type_order, atributo);
            mergeSort(derecha, type_order, atributo);
            merge(lista, izquierda, derecha, type_order, atributo);
        }
    }

    private void merge(Proyecto[] lista, Proyecto[] izquierda, Proyecto[] derecha, Integer type_order, String atributo){
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < izquierda.length && j < derecha.length){
            if (verify(izquierda[i], derecha[j], type_order, atributo)){
                lista[k++] = izquierda[i++];
            } else {
                lista[k++] = derecha[j++];
            }
        }
        while (i < izquierda.length){
            lista[k++] = izquierda[i++];
        }
        while (j < derecha.length){
            lista[k++] = derecha[j++];
        }
    }
}
