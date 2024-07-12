package Proyecto.Package;

import java.util.List;

/**
 * Interfaz para el elemento de un árbol B.
 *
 * @param <T> Tipo de los datos almacenados en el elemento.
 */
@SuppressWarnings({"rawtypes"})
public interface IElementoB<T> {

    /**
     * Obtiene el valor de la etiqueta del nodo.
     *
     * @return Etiqueta del nodo.
     *
     * Precondiciones:
     * La etiqueta no debe ser nula.
     *
     * Postcondiciones:
     * Se retorna el valor de la etiqueta del nodo.
     *
     * Orden de tiempo de ejecución: O(1)
     */
    Comparable getEtiqueta();

    /**
     * Obtiene la lista de claves del nodo.
     *
     * @return Lista de claves del nodo.
     *
     * Precondiciones:
     * La lista no debe contener claves nulas.
     *
     * Postcondiciones:
     *  Se retorna la lista de claves almacenadas en el nodo.
     *
     *  Orden de tiempo de ejecución: O(1)
     */
    List<Comparable> getClaves();

    /**
     * Obtiene la lista de hijos del nodo.
     *
     * @return Lista de hijos del nodo.
     *
     * Precondiciones:
     * La lista no debe contener claves con valores nulos.
     *
     * Postcondiciones:
     * Se retorna la lista de hijos del nodo.
     *
     * Orden de tiempo de ejecución: O(1)
     */
    List<TElementoB<T>> getHijos();

    /**
     * Inserta una clave y un dato en el nodo.
     *
     * @param clave Clave del elemento a insertar.
     * @param dato  Dato del elemento a insertar.
     * @return Verdadero si la inserción fue exitosa, falso en caso contrario.
     *
     * Precondiciones:
     *  La clave del elemento a insertar no debe ser nula.
     *  El dato del elemento a insertar no debe ser nulo.
     *
     * Postcondiciones:
     *  Si la inserción es exitosa, la clave y el dato se añaden al nodo.
     *  Si la inserción es exitosa y el nodo se llena, el nodo puede necesitar dividirse.
     *  El método retorna un booleano indicando si la inserción fue exitosa.
     *
     *  Orden de tiempo de ejecución: O(t) t = orden del árbol B.
     */
    boolean insertar(Comparable clave, T dato);

    /**
     * Busca un elemento dentro del nodo con la clave indicada.
     *
     * @param clave Clave del elemento a buscar.
     * @return Elemento encontrado. Si no se encuentra, retorna nulo.
     *
     * Precondiciones:
     * La clave del elemento a buscar no debe ser nula.
     *
     * Postcondiciones:
     *  Si el elemento se encuentra, se retorna el elemento correspondiente.
     *  Si el elemento no se encuentra, se retorna nulo.
     *
     *  Orden de tiempo de ejecución: O(log n)
     */
    TElementoB<T> buscar(Comparable clave);

    /**
     * Elimina una clave y su dato asociado del nodo.
     *
     * @param clave Clave del elemento a eliminar.
     * @return Verdadero si la eliminación fue exitosa, falso en caso contrario.
     *
     * Precondiciones:
     *  La clave del elemento a eliminar no debe ser nula.
     *
     * Postcondiciones:
     *  Si la eliminación es exitosa, la clave y el dato se eliminan del nodo.
     *  Si la eliminación es exitosa y el nodo tiene menos claves de las permitidas, el nodo puede necesitar fusionarse o redistribuirse.
     *  El método retorna un booleano indicando si la eliminación fue exitosa.
     *
     *  Orden de tiempo de ejecución: O(log n)
     */
    boolean eliminar(Comparable clave);

    /**
     * Realiza un recorrido en preorden del nodo y sus hijos.
     *
     * @param lista Lista en la cual se deben agregar los datos en preorden.
     *
     * Precondiciones:
     *  La lista proporcionada no debe ser nula.
     *
     * Postcondiciones:
     *  La lista proporcionada contiene todos los datos del nodo y sus hijos en orden de preorden.
     *  La estructura del nodo y sus hijos no se modifica.
     *
     * Orden de tiempo de ejecución: O(n)
     */
    void preOrden(List<T> lista);

    /**
     * Realiza un recorrido en inorden del nodo y sus hijos.
     *
     * @param lista Lista en la cual se deben agregar los datos en inorden.
     *
     * Precondiciones:
     *  La lista proporcionada no debe ser nula.
     *
     * Postcondiciones:
     *  La lista proporcionada contiene todos los datos del nodo y sus hijos en orden de inorden.
     *  La estructura del nodo y sus hijos no se modifica.
     *
     * Orden de tiempo de ejecución: O(n)
     */
    void inOrden(List<T> lista);

    /**
     * Realiza un recorrido en postorden del nodo y sus hijos.
     *
     * @param lista Lista en la cual se deben agregar los datos en postorden.
     *
     * Precondiciones:
     *  La lista proporcionada no debe ser nula.
     *
     * Postcondiciones:
     *  La lista proporcionada contiene todos los datos del nodo y sus hijos en orden de postorden.
     *  La estructura del nodo y sus hijos no se modifica.
     *
     * Orden de tiempo de ejecución: O(n)
     */
    void postOrden(List<T> lista);

    /**
     * Obtiene los datos contenidos en el nodo.
     *
     * @return Lista de datos del nodo.
     *
     * Precondiciones:
     * No debe contener datos nulos.
     *
     * Postcondiciones:
     *  Se retorna la lista de datos almacenados en el nodo.
     *
     *  Orden de tiempo de ejecución: O(1)
     */
    List<T> getDatos();

    /**
     * Obtiene el número de claves en el nodo.
     *
     * @return Número de claves en el nodo.
     *
     * Precondiciones:
     *  el número de claves no debe ser negativo.
     *
     * Postcondiciones:
     * Se retorna el número de claves almacenadas en el nodo.
     *
     *Orden de tiempo de ejecución: O(1)
     */
    int getNumClaves();

    /**
     * Obtiene el tamaño del subárbol (número de elementos).
     *
     * @return Tamaño del subárbol.
     *
     * Precondiciones:
     * El tamaño no debe ser negativo.
     *
     * Postcondiciones:
     *  Se retorna el número de elementos en el subárbol cuya raíz es este nodo.
     *
     *Orden de tiempo de ejecución: O(n)
     */
    int obtenerTamaño();

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
    void merge(TElementoB<T> unNodo);
}
