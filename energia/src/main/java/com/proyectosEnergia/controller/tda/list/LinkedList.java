package com.proyectosEnergia.controller.tda.list;

import java.lang.reflect.Method;

import com.proyectosEnergia.controller.excepcion.ListEmptyException;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;

    // LINKED LIST
    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }
    
    //METODOS C.R.U.D para los objetos de la lista Enlazada

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {

        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }

    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {

            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
        
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {

            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            // 2 5 6 9 --> 2
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();

            preview.setNext(actually.getNext());
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }

    // METODOS DE ORDENAMIENTO

    // Ordenacion por ShellSHort para Objetos(por atributo en orden descendente o ascendete)
    public LinkedList<E> ordenarShellSort(String atributo, Integer orden) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                Integer intervalo = lista.length / 2;
                while (intervalo >= 1) {
                    for (int i = 0; i+intervalo < lista.length; i++) {
                        int j = i;
                        while (j >= 0) {
                            int k = j + intervalo;
                            if (compare_attribute(atributo, lista[j], lista[k], orden)) {
                                E aux = lista[j];
                                lista[j] = lista[k];
                                lista[k] = aux;
                                j -= intervalo;
                            } else {
                                j = -1;
                            }
                        }
                    }
                    intervalo = intervalo / 2;  
                }
                this.toList(lista);
            }
        }
        return this;
    }

    //Ordenacion por ShellShort para Numeros (mediantes su orden descendente o ascendente)
    public LinkedList<E> ordenarShellSort(Integer orden) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                Integer intervalo = lista.length / 2;
                while (intervalo >= 1) {
                    for (int i = 0; i+intervalo < lista.length; i++) {
                        int j = i;
                        while (j >= 0) {
                            int k = j + intervalo;
                            if (compare(lista[j], lista[k], orden)) {
                                E aux = lista[j];
                                lista[j] = lista[k];
                                lista[k] = aux;
                                j -= intervalo;
                            } else {
                                j = -1;
                            }
                        }
                    }
                    intervalo = intervalo / 2;  
                }
                this.toList(lista);
            }
        }
        return this;
    }

    //Ordenacion por MergeSort para Objetos (por atributo en orden descendente o ascendente)
    public LinkedList<E> ordenarMergeSort(String atributo, Integer orden) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                this.toList(mergeSort(lista, atributo, orden));
            }
        }
        return this;
    }

    //Ordenacion por MergeSort para Numeros (mediante su orden descendente o ascendente)
    public LinkedList<E> ordenarMergeSort(Integer orden) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Number || data instanceof String) {
                E[] lista = this.toArray();
                reset();
                this.toList(mergeSort(lista, orden));
            }
        }
        return this;
    }

    //Metodo de MergeSort para Objetos, divide el arreglo en subarreglos, los combina y devuelve la lista ordenada
    private E[] mergeSort(E[] lista, String atributo, Integer orden) throws Exception {
        if (lista.length <= 1) {
            return lista;
        } else {
            int middle = lista.length / 2;

            E[] left = (E[])new Object[middle];
            E[] right = (E[])new Object[lista.length - middle];

            for (int i = 0; i < middle; i++) {
                left[i] = lista[i];
            }

            int aux = 0;
            for (int k = middle; k < lista.length; k++) {
                right[aux] = lista[k];
                aux++;
            }
            
            //Realiza el proceso de recursion con los subarreglos izquierda y derecha
            left = mergeSort(left, atributo, orden);
            right = mergeSort(right, atributo, orden);
            
            //Combina los subarreglos izquierda y derecha ya ordenados
            if (left.length >= 1 && right.length >= 1) {
                merge(lista, left, right, atributo, orden);
            }
        }
        return lista;
    }


    //Combina los arreglos izquierda y derecha
    private void merge(E[] lista, E[] left, E[] right, String atributo, Integer orden) throws Exception {
        int i = 0;
        int j = 0; 
        int k = 0;

        while (i < left.length && j < right.length) {
            if (compare_attribute(atributo, left[i], right[j], orden)) {
                lista[k] = right[j];
                j++;
            } else {
                lista[k] = left[i];
                i++;
            }
            k++;
        }

        while (i < left.length) {
            lista[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            lista[k] = right[j];
            j++;
            k++;
        }
    }


    //Metodos para números
    private E[] mergeSort(E[] lista, Integer orden) throws Exception {
        if (lista.length <= 1) {
            return lista;
        } else {
            int middle = lista.length / 2;

            E[] left = (E[])new Object[middle];
            E[] right = (E[])new Object[lista.length - middle];

            for (int i = 0; i < middle; i++) {
                left[i] = lista[i];
            }

            int j = 0;
            for (int k = middle; k < lista.length; k++) {
                right[j] = lista[k];
                j++;
            }

            left = mergeSort(left, orden);
            right = mergeSort(right, orden);
            
            if (left.length >= 1 && right.length >= 1) {
                merge(lista, left, right, orden);
            }
        }
        return lista;
    }

    private void merge(E[] lista, E[] left, E[] right, Integer orden) throws Exception {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j], orden)) {
                lista[k] = right[j];
                j++;
            } else {
                lista[k] = left[i];
                i++;
            }
            k++;
        }

        while (i < left.length) {
            lista[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            lista[k] = right[j];
            j++;
            k++;
        }
    }

    //Ordenacion por QuickSort para Objetos (por atributo en orden descendente o ascendente)
    public LinkedList<E> ordenarQuickSort(String atributo, Integer orden) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                quickSort(lista, 0, lista.length-1, atributo, orden);
                this.toList(lista);
            }
        }
        return this;
    }

    //Ordenacion por QuickSort para Numeros (mediante su orden descendente o ascendente)
    public LinkedList<E> ordenarQuickSort(Integer orden) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Number || data instanceof String) {
                E[] lista = this.toArray();
                reset();
                this.toList(quickSort(lista, 0, lista.length-1, orden));
            }
        }
        return this;
    }

    //Metodos QuickSort llama a la partition y realiza la recursion
    private E[] quickSort(E[] lista, int low, int high, String atributo, Integer orden) throws Exception {
        if (low < high) {
            int pivote = partition(lista, low, high, atributo, orden);

            quickSort(lista, low, pivote - 1, atributo, orden);
            quickSort(lista, pivote + 1, high, atributo, orden);
            
        } 
        return lista;
    }

    //Metodo para la partition del metodo QuickSort
    private int partition(E[] lista, int low, int high, String atributo, Integer orden) throws Exception {
        E pivote = lista[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare_attribute(atributo, pivote, lista[j], orden)) {
                i++;
                E aux = lista[i];
                lista[i] = lista[j];
                lista[j] = aux;
            }
        }

        E aux = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = aux;

        return i + 1;
    }


    //Metodos para ordenar por QuickSort para numeros
    private E[] quickSort(E[] lista, int low, int high, Integer orden) throws Exception {
        if (low < high) {
            int pivote = partition(lista, low, high, orden);

            quickSort(lista, low, pivote - 1, orden);
            quickSort(lista, pivote + 1, high, orden);
            
        } 
        return lista;
    }

    private int partition(E[] lista, int low, int high, Integer orden) throws Exception {
        E pivote = lista[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(pivote, lista[j], orden)) {
                i++;
                E aux = lista[i];
                lista[i] = lista[j];
                lista[j] = aux;
            }
        }

        E aux = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = aux;

        return i + 1;
    }

    //Busqueda Lineal para Objetos (por atributo y valor)
    public LinkedList<E> linealSearch(String atributo, Object valor) throws Exception {
        LinkedList<E> lista = new LinkedList<>();
        if (!this.isEmpty()) {
            E[] aux = this.toArray();
            for (int i = 0; i < aux.length; i++) {
                Object valorAtributo = exist_attribute(aux[i], atributo);
                if (valorAtributo != null) {
                    String valorAtributroStr = valorAtributo.toString();
                    String valorStr = valor.toString();
                    if (valorAtributroStr.equals(valorStr) || valorAtributroStr.toLowerCase().startsWith(valorStr.toLowerCase())) {
                        lista.add(aux[i]);
                    }
                }
            }
        }
        return lista;
    }

    //Busqueda Lineal para números
    public LinkedList<E> linealSearch(Object valor) throws Exception {
        LinkedList<E> list = new LinkedList<>();
        if (!this.isEmpty()) {
            E[] aux = this.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].equals(valor)) {
                    list.add(aux[i]);
                    break;
                }
            }
        }
        return list;
    }

    //Busqueda Binaria para Objetos (por atributo y valor)
    public LinkedList<E> binarySearch(String atributo, Object valor) throws Exception {
        LinkedList<E> lista = new LinkedList<>();
        if (!this.isEmpty()) {
            this.ordenarQuickSort(atributo, 0);
            E[] aux = this.toArray();
            int first = 0;
            int last = aux.length - 1;
            Boolean find = false;
            while (first <= last && find == false) {
                int mid = (first + last) / 2;
                Object valorAtributo = exist_attribute(aux[mid], atributo);
            
                if (valorAtributo != null) {
                    String valorAtributoStr = valorAtributo.toString();
                    String valorStr = valor.toString();
                    if (valorAtributoStr.equals(valorStr) || valorAtributoStr.toLowerCase().startsWith(valorStr.toLowerCase())) {
                        lista.add(aux[mid]);
                        find = true;
                    } else {
                        if (compare(valorAtributoStr, valorStr, 0)) {
                            last = mid - 1;
                        } else {
                            first = mid + 1;
                        }
                    }
                    
                }
            }
        }
        return lista;
    }

    
    public E binarySearch(Object valor) throws Exception {
        E obj = null;
        if (!this.isEmpty()) {
            this.ordenarQuickSort(0);
            E[] aux = this.toArray();
            int first = 0;
            int last = aux.length - 1;
            Boolean find = false;
            while (first <= last && find == false) {
                int mid = (first + last) / 2;
                if (aux[mid].equals(valor)) {
                    obj = aux[mid];
                    find = true;
                } else {
                    if (compare(aux[mid], valor, 0)) {
                        last = mid - 1;
                    } else {
                        first = mid + 1;
                    }
                }
            }
        }
        return obj;
    }
    
    //METODOS AUXILIARES PARA ORDENAMIENTO
    //Comparar los objetos a y b segun el tipo de ordenamiento
    private Boolean compare(Object a, Object b, Integer orden) {
        switch (orden) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
            default:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
        }

    }


    //Comparar los atributos de los objetos a y b segun el tipo de ordenamiento

    private Boolean compare_attribute(String attribute, E a, E b, Integer orden) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), orden);
    }

    // Obtener el valor de un atributo de un objeto
    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {           
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {              
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {            
            return method.invoke(a);
        }
        
        return null;
    }

    public LinkedList<E> clone() {
        LinkedList<E> clonedList = new LinkedList<>();
        for (int i = 0; i < this.size; i++) {
            clonedList.add(this.toArray()[i]);
        }
        return clonedList;
    }
}
