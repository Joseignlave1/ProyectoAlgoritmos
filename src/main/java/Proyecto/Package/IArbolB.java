package Proyecto.Package;

import java.util.List;

/**
 * Interfaz para la estructura de datos de un Árbol B.
 *
 * @param <T> El tipo de los datos que contendrán los elementos del árbol.
 */
public interface IArbolB<T> {
    /**
     * Inserta un elemento en el árbol B. Si ya existe un elemento con la misma clave la inserción no se realiza y por lo tanto retornamos falso
     *
     * @param etiqueta Clave del elemento a insertar.
     * @param dato, Dato del elemento a insertar
     * @return Verdadero si la inserción fue exitosa, falso en caso contrario
     *
     * Pre condiciones:
     * El árbol no debe de ser nulo
     * La etiqueta del elemento a insertar no debe ser nula.
     * El dato del elemento a insertar no debe ser nulo.
     *
     * Post Condiciones:
     * Si la inserción es exitosa, la cantidad de elementos en el árbol aumenta en 1.
     * Si la inserción es exitosa, el elemento con la clave especificada estará en el árbol.
     * Si la inserción no es exitosa, la cantidad de elementos en el árbol no cambia.
     * El método retorna un booleano: verdadero si la inserción fue exitosa, falso en caso contrario.
     *
     * Orden de tiempo de ejecución: O(log n)
     *
     */
     boolean insertar(Comparable etiqueta, T dato);

    /**
     * Busca un elemento en el árbol B utilizando la etiqueta como clave de búsqueda.
     *
     * @param etiqueta, Etiqueta del elemento que vamos a buscar
     * @return El elemento encontrado, si no se encuentra, retorna nulo
     *
     * Pre condiciones:
     * El árbol no debe ser nulo
     * La etiqueta del elemento a buscar no debe ser nula.
     *
     * Postcondiciones:
     * Si el elemento se encuentra, el método retorna el elemento correspondiente
     * Si el elemento no se encuentra, el método retorna nulo
     * El árbol permanece sin cambios después la operación de búsqueda.
     *
     * Orden de tiempo de ejecución: O(log n)
     */

    T buscar(Comparable etiqueta);

    /**
     * Elimina un elemento del árbol B recibiendo como parámetro la etiqueta del mismo
     *
     * @param etiqueta La etiqueta del elemento a eliminar
     * @return Verdadero si la eliminación fue exitosa, falso en caso contrario.
     *
     * Precondiciones:
     * El árbol no debe ser nulo.
     * La etiqueta del elemento a eliminar no debe ser nula.
     *
     * Postcondiciones:
     * Si la eliminación es exitosa, la cantidad de elementos en el árbol disminuye en 1.
     * Si la eliminación es exitosa, el elemento con la clave especificada ya no estará en el árbol.
     * Si la eliminación no es exitosa, la cantidad de elementos en el árbol no cambia.
     * El método retorna un booleano: verdadero si la eliminación fue exitosa, falso en caso contrario.
     * La estructura del árbol se mantiene como un Árbol B válido después de la eliminación.
     *
     * Orden de tiempo de ejecución: O(log n)
     */

    boolean eliminar(Comparable etiqueta);

    /**
     *
     * @return Una lista con los elementos del recorrido en preorden.
     *
     * Precondiciones:
     * - El árbol no debe ser nulo.
     *
     * Postcondiciones:
     * La estructura del árbol no se modifica.
     * Se retorna una lista que contiene todos los elementos del árbol en el orden de preorden.
     *
     * Orden de tiempo de ejecución: O(n)
     */
    List<T> preOrden();

    /**
     * Realiza un recorrido en inOrden del árbol B.
     *
     * @return Una lista con los elementos del recorrido en inOrden.
     *
     * Precondiciones:
     * El árbol no debe ser nulo.
     *
     * Postcondiciones:
     * La estructura del árbol no se modifica.
     * Se retorna una lista que contiene todos los elementos del árbol en el orden de preorden.
     *
     * Orden de tiempo de ejecución: O(n)
     */
    List<T> inOrden();
    /**
     * Realiza un recorrido en postOrden del árbol B.
     *
     * @return Una lista con los elementos del recorrido en postOrden.
     *
     * Precondiciones:
     * El árbol no debe ser nulo.
     *
     * Postcondiciones:
     * La estructura del árbol no se modifica.
     * Se retorna una lista que contiene todos los elementos del árbol en el orden de preorden.
     *
     * Orden de tiempo de ejecución: O(n)
     */
    List<T> postOrden();

    /**
     * Fusiona este árbol B con otro árbol B
     * @param otroArbol
     * @return Verdadero si la fusión fue exitosa, falso en caso contrario.
     *
     * Precondiciones:
     * El árbol actual no debe ser nulo.
     * El otro árbol a fusionar no debe ser nulo.
     *
     * Postcondiciones:
     * Si la fusión es exitosa, los elementos del otro árbol se insertan en este árbol.
     * La estructura del árbol resultante sigue siendo un Árbol B válido.
     * El otro árbol permanece inalterado.
     *
     * Orden de tiempo de ejecución: O(n log n)
     */
    boolean merge(TArbolB<T> otroArbol);
}


