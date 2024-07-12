package Proyecto.Package;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un nodo de un árbol B. Cada nodo almacena una lista de claves y datos asociados,
 * así como las referencias a sus hijos. En un árbol B, los nodos pueden tener múltiples hijos y claves.
 *
 * @param <T> Tipo de dato que se almacena en el nodo.
 */
public class TElementoB<T> implements IElementoB<T>, Serializable {
    private List<Comparable> claves; //Lista de claves en el nodo
    private List<T> datos;           //Lista de datos asociados a cada clave
    private List<TElementoB<T>> hijos; //Lista de hijos del nodo
    private int t;                     // Grado mínimo del árbol B
    private TElementoB<T> padre;       //Padre del nodo actual

    public TElementoB(int t) {
        this.claves = new ArrayList<>();
        this.datos = new ArrayList<>();
        this.hijos = new ArrayList<>();
        this.t = t;
        this.padre  = null; //Inicialmente el nodo no tiene padre
    }

    /**
     * Obtiene el padre del nodo actual.
     *
     * @return El nodo padre.
     */
    public TElementoB<T> obtenerPadre() {
        return this.padre;
    }

    /**
     * Establece el padre del nodo actual.
     *
     * @param padre El nodo padre a establecer.
     */
    public void setPadre(TElementoB<T> padre) {
        this.padre = padre;
    }
    /**
     * Obtiene la etiqueta del nodo, que es la primera clave en la lista de claves.
     *
     * @return La etiqueta del nodo.
     */
    @Override
    public Comparable getEtiqueta() {
        if(claves.isEmpty()) {
            return null;
        }
        return claves.getFirst(); //Retornamos la primera clave como etiqueta.
    }

    /**
     * Obtiene la lista de claves del nodo.
     *
     * @return La lista de claves del nodo.
     */
    @Override
    public List<Comparable> getClaves() {
        return claves;
    }

    /**
     * Obtiene la lista de hijos del nodo.
     *
     * @return La lista de hijos del nodo.
     */
    @Override
    public List<TElementoB<T>> getHijos() {
        return hijos;
    }

    @Override
    public boolean insertar(Comparable clave, T dato) {
        if(claves.size() == 2 * t - 1) {
            TElementoB<T> nuevoRaiz = new TElementoB<>(t);
            nuevoRaiz.hijos.add(this);
            nuevoRaiz.dividirHijo(0 , this);
            nuevoRaiz.insertarEnNodoNoLleno(clave, dato);
            return true;
        } else {
            insertarEnNodoNoLleno(clave, dato);
            return true;
        }
    }

    /**
     * Inserta una clave y su dato asociado en un nodo que no está lleno.
     * Si el nodo es una hoja, inserta la clave y el dato directamente.
     * Si el nodo no es una hoja, encuentra el hijo apropiado para descender e inserta la clave y el dato allí.
     * Si el hijo está lleno, se divide antes de continuar con la inserción.
     *
     * @param clave La clave del elemento a insertar.
     * @param dato El dato asociado al elemento a insertar.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     *
     * Precondiciones:
     * - El nodo actual no debe estar lleno.
     *
     * Postcondiciones:
     * - La clave y el dato se insertan en la posición correcta en el nodo o en el subárbol correspondiente.
     * - Si se divide un hijo, la clave mediana se sube al nodo actual.
     */
    public void insertarEnNodoNoLleno(Comparable clave, T dato) {
        int i = claves.size() -1; //Empezamos a recorrerlo desde el nodo de la última posición

        //Caso 1: Si el nodo es una hoja (no tiene hijos)

        if(hijos.isEmpty()) {
            while(i >= 0 && clave.compareTo(claves.get(i)) < 0) {
                i--;
            }
            //Insertamos la nueva clave y dato en la posición correcta
            claves.add(i + 1, clave);
            datos.add(i + 1, dato);
        } else {
            //Caso2: Si el nodo no es una hoja(tiene hijos)
            //Encuentramos el hijo apropiado para descender
            while(i >= 0 && clave.compareTo(claves.get(i)) < 0) {
                i--;
            }
            i++;
            TElementoB<T> hijo = hijos.get(i);

            // Si el hijo está lleno sube la mediana, la cuál se convierte en padre ya qué dividimos al hijo en dos hijos más pequeños.
            if(hijo.getNumClaves() == 2 * t - 1) {
                dividirHijo(i, hijo);

                //Verificamos si después de dividir es necesario ajustar la posición de i

                if(clave.compareTo(claves.get(i)) > 0) {
                    i++;
                }
            }
            hijos.get(i).insertarEnNodoNoLleno(clave, dato);
        }
    }

    /**
     * Divide un nodo hijo lleno en dos nodos más pequeños y mueve la clave mediana al nodo padre.
     * Este método se llama cuando un nodo hijo está lleno y se necesita espacio para insertar una nueva clave.
     *
     * @param i El índice del hijo en el nodo actual que se va a dividir.
     * @param nodoLleno El nodo hijo que está lleno y necesita ser dividido.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     *
     * Precondiciones:
     * - El nodo hijo especificado por i debe estar lleno.
     * - El nodo actual no debe estar lleno.
     *
     * Postcondiciones:
     * - El nodo hijo se divide en dos nodos más pequeños.
     * - La clave mediana del nodo hijo se mueve al nodo actual.
     * - Los hijos del nodo lleno se redistribuyen entre los dos nuevos nodos.
     */
    public void dividirHijo(int i, TElementoB<T> nodoLleno) {
        // Crear un nuevo nodo que almacenará t-1 claves de nodoLleno
        TElementoB<T> nodoNuevo = new TElementoB<>(t);

        // Transferir las últimas t-1 claves y datos de nodoLleno a nodoNuevo
        for (int j = 0; j < t - 1; j++) {
            nodoNuevo.claves.add(nodoLleno.claves.remove(t));
            nodoNuevo.datos.add(nodoLleno.datos.remove(t));
        }

        // Si nodoLleno no es una hoja, transferir los últimos t hijos de nodoLleno a nodoNuevo
        if (!nodoLleno.hijos.isEmpty()) {
            for (int j = 0; j < t; j++) {
                nodoNuevo.hijos.add(nodoLleno.hijos.remove(t));
            }
        }

        // Insertar nodoNuevo en la lista de hijos del nodo actual
        this.hijos.add(i + 1, nodoNuevo);

        // Mover la clave mediana de nodoLleno al nodo actual en la posición i
        this.claves.add(i, nodoLleno.claves.remove(t - 1));
        this.datos.add(i, nodoLleno.datos.remove(t - 1));
    }

    @Override
    public TElementoB<T> buscar(Comparable clave) {
        //Encuentra la primera clave mayor o igual a la clave buscada
        int i = 0;
        while(i < claves.size() && clave.compareTo(claves.get(i)) > 0) {
            i++;
        }

        //Si la clave está presente en este nodo, entonces devolvemos este nodo
        if(i < claves.size() && clave.compareTo(claves.get(i)) == 0) {
            return this;
        }

        //Si la clave no está presente y el nodo es una hoja, retornamos null
        if(hijos.isEmpty()) {
            return null;
        }

        //Si la clave no está presente en este nodo, descender al hijo adecuado

        return hijos.get(i).buscar(clave);
    }

    @Override
    public boolean eliminar(Comparable clave) {
        int indice = encontrarClave(clave);

        //La clave está en el nodo actual
        if(indice < claves.size() && claves.get(indice).compareTo(clave) == 0) {
            if(hijos.isEmpty()) {
                //Caso 1: El nodo es una hoja
                //Eliminamos directamente
                claves.remove(indice);
                datos.remove(indice);
                return true;
            } else {
                //Caso 2: El nodo es un nodo interno
                eliminarDesdeNodoInterno(indice);
                return true;
            }
        } else {
            //Caso 3: La clave no está en el nodo actual

            if(hijos.isEmpty()) {
                //Si el nodo es una hoja, entonces la clave no está en el árbol

                return false;
            }
            //Verificamos si el hijo donde se encuentra la clave tiene al menos t claves

            boolean flag = (indice == claves.size());

            if(hijos.get(indice).claves.size() < t) {
                //Si no tiene al menos t claves, debemos rellenar
                rellenar(indice);
            }

            //Después de rellenar, el hijo puede haberse combinado con su vecino
            //Así que utilizamos indice(indice del nodo actual) o indice -1(indice del vecino)

            if(flag && indice > claves.size()) {
                hijos.get(indice -1).eliminar(clave);
            } else {
                hijos.get(indice).eliminar(clave);
            }
        }
        return true;
    }
    /**
     * Encuentra el índice de la clave en el nodo actual.
     * Si la clave no está presente, devuelve el índice donde debería estar.
     *
     * @param clave La clave a buscar.
     * @return El índice de la clave en el nodo actual, o el índice donde debería estar si no está presente.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    public int encontrarClave(Comparable clave) {
        int indice = 0;

        while(indice < claves.size() && claves.get(indice).compareTo(clave) < 0) {
            indice++;
        }
        return indice;
    }
    /**
     * Elimina una clave de un nodo interno en el árbol B.
     * Si la clave tiene suficientes claves en su hijo izquierdo, reemplaza la clave con el predecesor.
     * Si no, pero el hijo derecho tiene suficientes claves, reemplaza la clave con el sucesor.
     * Si ambos hijos tienen menos de t claves, combina los hijos y luego elimina la clave del nodo combinado.
     *
     * @param indice El índice de la clave a eliminar en el nodo actual.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     *
     * Precondiciones:
     * - El nodo actual es un nodo interno.
     * - El nodo tiene al menos t claves.
     *
     * Postcondiciones:
     * - La clave se elimina del nodo actual y el árbol se mantiene balanceado.
     */
    public void eliminarDesdeNodoInterno(int indice) {
        Comparable clave = claves.get(indice);

        //Si el hijo anterior(que precede a la clave) hijo[indice] tiene al menos t claves

        if(hijos.get(indice).claves.size() >= t) {
            Comparable predecesor = obtenerPredecesor(indice);
            claves.set(indice, predecesor);
            datos.set(indice, hijos.get(indice).buscar(predecesor).getDatos().getFirst());
            hijos.get(indice).eliminar(predecesor);
        } else if(hijos.get(indice + 1).claves.size() >= t) {
            //Si el siguiente hijo [indice + 1] tiene al menos t claves
            Comparable sucesor = obtenerSucesor(indice);
            claves.set(indice, sucesor);
            datos.set(indice, hijos.get(indice + 1).buscar(sucesor).getDatos().getFirst());
            hijos.get(indice + 1).eliminar(sucesor);
        } else {
            //Si ambos hijos o sea hijo[indice] digamos el actual y hijos[indice + 1] el siguiente, tienen menos de t claves, entonces tenemos que combinarlos
            combinar(indice);
            hijos.get(indice).eliminar(clave);
        }
    }
    /**
     * Obtiene la clave predecesora de la clave en el índice dado en el nodo actual.
     * El predecesor es la clave más grande en el subárbol izquierdo del nodo actual.
     *
     * @param indice El índice de la clave en el nodo actual.
     * @return La clave predecesora.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     *
     * Precondiciones:
     * - El nodo actual no es una hoja.
     * - El hijo izquierdo del nodo actual tiene al menos t claves.
     *
     * Postcondiciones:
     * - Se devuelve la clave predecesora de la clave en el índice dado.
     */
    public Comparable obtenerPredecesor(int indice) {
        // Mantenemos el nodo actual, movemos el nodo más a la derecha hasta llegar a una hoja

        TElementoB<T> actual = hijos.get(indice);

        while(!actual.hijos.isEmpty()) {
            actual = actual.getHijos().get(actual.claves.size());
        }
        return actual.claves.get(actual.claves.size() - 1);
    }
    /**
     * Obtiene la clave sucesora de la clave en el índice dado en el nodo actual.
     * El sucesor es la clave más pequeña en el subárbol derecho del nodo actual.
     *
     * @param indice El índice de la clave en el nodo actual.
     * @return La clave sucesora.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     *
     * Precondiciones:
     * - El nodo actual no es una hoja.
     * - El hijo derecho del nodo actual tiene al menos t claves.
     *
     * Postcondiciones:
     * - Se devuelve la clave sucesora de la clave en el índice dado.
     */
    public Comparable obtenerSucesor(int indice) {
        //Mantenemos el nodo actual, movemos más a la izquierda hasta llegar a una hoja

        TElementoB<T> actual = hijos.get(indice + 1);

        while(!actual.hijos.isEmpty()) {
            actual = actual.getHijos().getFirst();
        }
        return actual.claves.getFirst();
    }

    /**
     * Rellena el nodo hijo en la posición dada si tiene menos de t claves.
     * Si es posible, toma prestado de un hermano con suficientes claves. Si no, combina el nodo con un hermano.
     *
     * @param indice La posición del nodo hijo que necesita ser rellenado.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    public void rellenar(int indice) {
        //Si el hijo anterior tiene al menos t claves, tomamos prestado de él

        if(indice != 0 && hijos.get(indice - 1).claves.size() >= t) {
            tomarPrestadoDeAnterior(indice);
        }

        //Si el siguiente hijo tiene al menos t claves, tomamos prestado de él

        else if(indice != claves.size() && hijos.get(indice + 1).claves.size() >= t) {
            tomarPrestadoDeSiguiente(indice);
        }

        //si ambos hijos tienen menos de t claves, entonces combinamos con un hermano

        else {
            if(indice != claves.size()) {
                combinar(indice);
            } else {
                combinar(indice - 1);
            }
        }
    }

    /**
     * Toma prestado una clave del hijo anterior del nodo en la posición dada.
     * Mueve una clave del padre al nodo hijo y ajusta los hijos según sea necesario.
     *
     * @param indice La posición del nodo hijo que necesita ser rellenado.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */

    private void tomarPrestadoDeAnterior(int indice) {
        TElementoB<T> hijo = hijos.get(indice);
        TElementoB<T> hermano = hijos.get(indice - 1);

        //Movemos la clave del padre al hijo.

        hijo.claves.addFirst(claves.get(indice - 1));
        claves.set(indice - 1, hermano.claves.removeLast());

        //Movemos el último hijo del hermano al hijo

        if(!hermano.hijos.isEmpty()) {
            hijo.hijos.addFirst(hermano.hijos.removeLast());
        }

        //Movemos el dato correspondiente del padre al hijo

        hijo.datos.addFirst(datos.get(indice - 1));
        datos.set(indice - 1, hermano.datos.removeLast());
    }

    /**
     * Toma prestado una clave del siguiente hijo del nodo en la posición dada.
     * Mueve una clave del padre al nodo hijo y ajusta los hijos según sea necesario.
     *
     * @param indice La posición del nodo hijo que necesita ser rellenado.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */

    private void tomarPrestadoDeSiguiente(int indice) {
        TElementoB<T> hijo = hijos.get(indice);
        TElementoB<T> hermano = hijos.get(indice + 1);

        //Movemos la clave del padre al hijo

        hijo.claves.add(claves.get(indice));
        claves.set(indice, hermano.claves.removeFirst());

        //Movemos el primer hijo del hermano al hijo.

        if(!hermano.hijos.isEmpty()) {
            hijo.hijos.add(hermano.hijos.removeFirst());
        }

        //Movemos el dato correspondiente del apdre al hijo

        hijo.datos.add(datos.get(indice));
        datos.set(indice, hermano.datos.removeFirst());
    }

    /**
     * Combina el nodo hijo en la posición dada con su siguiente hermano.
     * Mueve una clave del nodo padre al nodo hijo y ajusta los hijos según sea necesario.
     *
     * @param indice La posición del nodo hijo que necesita ser combinado.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */

    private void combinar(int indice) {
        TElementoB<T> hijo = hijos.get(indice);
        TElementoB<T> hermano = hijos.get(indice + 1);

        //Movemos la clave del padre al hijo

        hijo.claves.add(claves.remove(indice));
        hijo.datos.add(datos.remove(indice));

        //Movemos todas las claves y datos del hermano al hijo

        for(int i = 0; i < hermano.claves.size(); i++) {
            hijo.claves.add(hermano.claves.get(i));
            hijo.datos.add(hermano.datos.get(i));
        }

        //Movemos todos los hijos del hermano al hijo

        if(!hijo.hijos.isEmpty()) {
            hijo.hijos.addAll(hermano.hijos);
        }

        //Eliminamos el hermano de la lista de hijos

        hijos.remove(hermano);
    }



    @Override
    public void preOrden(List<T> lista) {
        for(T dato : datos) {
            lista.add(dato);
        }

        for(TElementoB<T> hijo : hijos) {
            hijo.preOrden(lista);
        }
    }


    @Override
    public void inOrden(List<T> lista) {
        //Recorremos recursivamente los hijos agregando los datos en orden

        for(int i = 0; i < claves.size(); i++) {
            if(i < hijos.size()) {
                hijos.get(i).inOrden(lista);
            }
            lista.add(datos.get(i));
        }

        //Si hay más datos en el nodo actual que hijos, entonces agregamos los datos al final.
        if(hijos.size() > claves.size()) {
            hijos.get(claves.size()).inOrden(lista);
        }
    }


    @Override
    public void postOrden(List<T> lista) {
        //Recorremos recursivamente a los hijos en orden
        for(TElementoB<T> hijo : hijos) {
            hijo.postOrden(lista);
        }

        //Agregamos los datos a la lista

        for(T dato : datos) {
            lista.add(dato);
        }
    }


    @Override
    public List<T> getDatos() {
        return this.datos;
    }


    @Override
    public int getNumClaves() {
        return claves.size();
    }


    @Override
    public int obtenerTamaño() {
        int tamaño = claves.size(); //Empezamos con el número de claves en el nodo actual

        //Agregamos recursivamente el tamaño de cada hijo
        for(TElementoB<T> hijo : hijos) {
            tamaño += hijo.obtenerTamaño();
        }
        return tamaño;
    }

    /**
     * Combina las claves, datos y hijos del nodo proporcionado con el nodo actual.
     * Si el número de claves excede el límite, divide el nodo.
     *
     * Precondiciones:
     * - El nodo proporcionado (unNodo) no debe ser nulo.
     * - El nodo actual (this) no debe ser nulo.
     *
     * Postcondiciones:
     * - Las claves y datos del nodo proporcionado se han agregado al nodo actual de manera ordenada.
     * - Si el número de claves del nodo actual excede el límite permitido, se divide el nodo.
     *
     * @param unNodo El nodo cuyos elementos serán combinados con el nodo actual.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    @Override
    public void merge(TElementoB<T> unNodo) {
        //Insertar las claves y datos del unNodo de manera ordenada
        for(int i = 0; i < unNodo.claves.size(); i++) {
            insertarClaveYDato(unNodo.claves.get(i), unNodo.datos.get(i));
        }

        //Agregar los hijos de unNodo al nodo actual

        for(int i = 0; i < unNodo.hijos.size(); i++) {
            this.hijos.add(unNodo.hijos.get(i));
        }

        //Si el número de claves excede el límite, dividimos el nodo

        if(this.claves.size() > 2 * t - 1) {
            dividirNodo();
        }
    }

    /**
     * Inserta una clave y un dato en el nodo actual de manera ordenada.
     *
     * Precondiciones:
     * - La clave a insertar no debe ser nula.
     * - El dato asociado a la clave no debe ser nulo.
     *
     * Postcondiciones:
     * - La clave y el dato se insertan en el nodo actual de manera ordenada.
     *
     * @param clave La clave a insertar.
     * @param dato El dato asociado a la clave.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */

    public void insertarClaveYDato(Comparable clave, T dato) {
        int i = 0;
        while(i < this.claves.size() && this.claves.get(i).compareTo(clave) < 0) {
            i++;
        }
        this.claves.add(i, clave);
        this.datos.add(i, dato);
    }


    /**
     * Divide el nodo actual si el número de claves excede el límite permitido.
     *
     * Precondiciones:
     * - El nodo actual (this) debe tener más de 2 * t - 1 claves.
     *
     * Postcondiciones:
     * - El nodo actual se divide en dos nodos.
     * - La clave y el dato del medio se mueven al nodo padre.
     * - Si no hay nodo padre, se crea una nueva raíz.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    public void dividirNodo() {
        int indiceMedio = claves.size() / 2;
        Comparable claveMedio = claves.get(indiceMedio);
        T datoMedio = datos.get(indiceMedio);

        TElementoB<T> nuevoNodo = new TElementoB<>(t);

        //Movemos las claves y datos del nodo actual al nuevo nodo
        for(int i = indiceMedio + 1; i < claves.size(); i++) {
            nuevoNodo.claves.add(claves.remove(indiceMedio + 1));
            nuevoNodo.datos.add(datos.remove(indiceMedio + 1));
        }

        //Movemos los hijos del nodo actual al nuevo nodo

        if(!hijos.isEmpty()) {
            for(int i = indiceMedio + 1; i < hijos.size(); i++) {
                nuevoNodo.hijos.add(hijos.remove(indiceMedio + 1));
            }
        }

        //Agregamos la clave y dato del medio al nodo padre

        TElementoB<T> padre = obtenerPadre();

        if(padre != null) {
            padre.insertarClaveYDato(claveMedio, datoMedio);
            padre.hijos.add(padre.hijos.indexOf(this) + 1, nuevoNodo);

            if(padre.getNumClaves() > 2 * t - 1) {
                padre.dividirNodo();
            }
        } else {
            //Si no hay padre, creamos otra raíz y esa raíz pasa a ser el padre
            TElementoB<T> nuevaRaiz = new TElementoB<>(t);
            nuevaRaiz.claves.add(claveMedio);
            nuevaRaiz.datos.add(datoMedio);
            nuevaRaiz.hijos.add(this);
            nuevaRaiz.hijos.add(nuevoNodo);
            setPadre(nuevaRaiz);
        }
    }
}
