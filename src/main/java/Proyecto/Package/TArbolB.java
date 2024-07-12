package Proyecto.Package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes"})
/**
 * Clase que representa un árbol B. En un árbol B, cada nodo puede tener un número variable de claves y
 * subárboles. Los nodos internos actúan como puntos de separación para las claves y sus correspondientes
 * subárboles. Un árbol B está balanceado, lo que significa que todas las hojas están en el mismo nivel.
 *
 * @param <T> El tipo de los datos que contendrán los elementos del árbol.
 */
public class TArbolB<T> implements IArbolB<T>, Serializable {
    private TElementoB<T> raiz;
    private int t; //Grado mínimo del árbol

    public TArbolB(int t) {
        this.raiz = null;
        this.t = t;
    }
    @Override
    public boolean insertar(Comparable clave, T dato) {
        if(buscar(clave) != null) {
            return false;
        }
        //Caso Árbol vacío
        if(raiz == null) {
            raiz = new TElementoB<>(t);
            raiz.getClaves().add(clave);
            raiz.getDatos().add(dato);
        } else {
            //Raíz llena
            if(raiz.getNumClaves() == 2 * t - 1) {
                //Creamos una nueva raíz y dividimos la raíz antigua
                TElementoB<T> nuevaRaiz = new TElementoB<>(t);
                nuevaRaiz.getHijos().add(raiz);
                nuevaRaiz.dividirHijo(0, raiz);

                // Insertar la nueva clase en la nueva raíz
                nuevaRaiz.insertarEnNodoNoLleno(clave, dato);

                //Actualizar la raíz

                raiz = nuevaRaiz;
            } else {
                //la raíz no está llena
                raiz.insertarEnNodoNoLleno(clave, dato);
            }
        }
        return true;
    }

    @Override
    public T buscar(Comparable clave) {
        if(raiz != null) {
            TElementoB<T> nodo = raiz.buscar(clave);

            if(nodo != null) {
                //Buscamos la clave
                int i = 0;

                while(i < nodo.getClaves().size() && !nodo.getClaves().get(i).equals(clave)) {
                    i++;
                }
                //Si la clave se encuentra en el nodo, retornar el dato asociado.

                if(i < nodo.getClaves().size()) {
                    return nodo.getDatos().get(i);
                }
            }
        }
        // Si no encontramos la clave, devolvemos null

        return null;
    }

    @Override
    public boolean eliminar(Comparable clave) {
        if(raiz == null) {
            //Arbol vacío, nada que eliminar
            return false;
        }

        boolean eliminado = raiz.eliminar(clave);

        //Si la raíz está vacía después de la eliminación

        if(raiz.getClaves().isEmpty()) {
            //Si la raíz tiene hijos, entonces la nueva raíz es el primer hijo
            if(!raiz.getHijos().isEmpty()) {
                raiz = raiz.getHijos().getFirst();
            } else {
                //Si la raíz no tiene hijos, entonces el árbol está vacío
                raiz = null;
            }
        }
        return eliminado;
    }

    @Override
    public List preOrden() {
        List<T> lista = new ArrayList<>();
        if(raiz != null) {
            raiz.preOrden(lista);
        }
        return lista;
    }

    @Override
    public List inOrden() {
        List<T> lista = new ArrayList<>();
        if(raiz != null) {
            raiz.inOrden(lista);
        }
        return lista;
    }

    @Override
    public List postOrden() {
        List<T> lista = new ArrayList<>();
        if(raiz != null) {
            raiz.postOrden(lista);
        }
        return lista;
    }

    @Override
    public boolean merge(TArbolB otroArbol) {
        // Si el otro árbol está vacío, entonces no hay nada que combinar
        if(otroArbol == null || otroArbol.raiz == null) {
            return true;
        }

        //Si el árbol actual está vacío, entonces la raíz pasa a ser la del otro arbol
        if(this.raiz == null) {
            this.raiz = otroArbol.raiz;

            return true;
        }

        //Combinamos las raíces ordenadamente
        combinarRaices(this.raiz, otroArbol.raiz);

        //Si la raíz resultante excede el número de claves, entonces la dividimos
        if(this.raiz.getNumClaves() > 2 * t - 1) {
            dividirRaiz();
        }

        return true;
    }


    /**
     * Combina las claves, datos y hijos de dos nodos raíz de manera ordenada.
     *
     * Precondiciones:
     * - Ambos nodos raíz (raiz1 y raiz2) no deben ser nulos.
     * - Ambos nodos raíz deben pertenecer a árboles con el mismo grado mínimo t.
     *
     * Postcondiciones:
     * - Las claves y datos del nodo raiz2 se han agregado al nodo raiz1 de manera ordenada.
     * - Si el número de claves en raiz1 excede el límite permitido, se divide la raíz.
     * - Los hijos de raiz2 se agregan a raiz1 de manera ordenada.
     *
     * @param raiz1 La primera raíz.
     * @param raiz2 La segunda raíz.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    private void combinarRaices(TElementoB<T> raiz1, TElementoB<T> raiz2) {
        for(int i = 0; i < raiz2.getClaves().size(); i++) {
            raiz1.insertarClaveYDato(raiz2.getClaves().get(i), raiz2.getDatos().get(i));
            if(raiz1.getNumClaves() > 2 * t - 1) {
                dividirRaiz();
            }
        }

        //Combinamos los hijos del segundo nodo raíz al primero
        if(!raiz2.getHijos().isEmpty()) {
            for(int i = 0; i < raiz2.getHijos().size(); i++) {
                insertarHijoOrdenado(raiz1, raiz2.getHijos().get(i));
            }
        }

    }

    /**
     * Inserta un nuevo hijo en el nodo padre de manera ordenada.
     *
     * Precondiciones:
     * - El nodo padre (nodoPadre) y el nuevo hijo (nuevoHijo) no deben ser nulos.
     * - Ambos nodos deben pertenecer a árboles con el mismo grado mínimo t.
     *
     * Postcondiciones:
     * - El nuevo hijo se inserta en el nodo padre de manera ordenada.
     *
     * @param nodoPadre El nodo padre en el cual se insertará el nuevo hijo.
     * @param nuevoHijo El nuevo hijo a insertar.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    private void insertarHijoOrdenado(TElementoB<T> nodoPadre, TElementoB<T> nuevoHijo) {
        int i = 0;
        while (i < nodoPadre.getHijos().size() && nodoPadre.getHijos().get(i).getClaves().get(0).compareTo(nuevoHijo.getClaves().getFirst()) < 0) {
            i++;
        }
        nodoPadre.getHijos().add(i, nuevoHijo);
    }

    /**
     * Divide la raíz si el número de claves excede el límite permitido.
     *
     * Precondiciones:
     * - La raíz actual debe tener más de 2 * t - 1 claves.
     *
     * Postcondiciones:
     * - La raíz actual se divide en dos nodos.
     * - Una nueva raíz se crea y se convierte en la nueva raíz del árbol.
     *
     * Orden de tiempo de ejecución: O(t) en el peor de los casos.
     */
    private void dividirRaiz() {
        TElementoB<T> nuevaRaiz = new TElementoB<>(t);
        TElementoB<T> antiguaRaiz = this.raiz;

        nuevaRaiz.getHijos().add(antiguaRaiz);
        nuevaRaiz.dividirHijo(0, antiguaRaiz);
        this.raiz = nuevaRaiz;
    }
}
