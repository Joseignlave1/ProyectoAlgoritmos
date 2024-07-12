package Proyecto.Package;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TArbolBTest {

    private TArbolB<Integer> arbol;

    @BeforeEach
    public void setUp() {
        arbol = new TArbolB<>(2);
    }

    @Test
    public void testInsertar() {
        assertTrue(arbol.insertar(10, 10));
        assertTrue(arbol.insertar(20, 20));
        assertTrue(arbol.insertar(5, 5));
        assertTrue(arbol.insertar(6, 6));
        assertTrue(arbol.insertar(12, 12));
    }
    @Test
    public void testInsertarQueProvocaDivision() {
        assertTrue(arbol.insertar(10, 10));
        assertTrue(arbol.insertar(20, 20));
        assertTrue(arbol.insertar(5, 5));
        assertTrue(arbol.insertar(6, 6)); // Esto debería provocar una división

        // Después de la división, la estructura del árbol debe mantenerse correcta
        assertEquals(List.of(5, 6, 10, 20), arbol.inOrden());
    }

    @Test
    public void testInsertarClavesDuplicadas() {
        assertTrue(arbol.insertar(10, 10));
        assertFalse(arbol.insertar(10, 10)); // Intentar insertar una clave duplicada
        assertEquals(List.of(10), arbol.inOrden());
    }

    @Test
    public void testInsertarMultiplesElementos() {
        assertTrue(arbol.insertar(10, 10));
        assertTrue(arbol.insertar(20, 20));
        assertTrue(arbol.insertar(5, 5));
        assertTrue(arbol.insertar(6, 6));
        assertTrue(arbol.insertar(12, 12));
        assertTrue(arbol.insertar(30, 30));
        assertTrue(arbol.insertar(7, 7));
        assertTrue(arbol.insertar(17, 17)); // Esto debería provocar múltiples divisiones


        assertEquals(List.of(5, 6, 7, 10, 12, 17, 20, 30), arbol.inOrden());
    }

    @Test
    public void testBuscar() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);

        assertNotNull(arbol.buscar(10));
        assertEquals(10, arbol.buscar(10));
        assertNull(arbol.buscar(15));
    }
    @Test
    public void testBuscarClaveMinima() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        assertEquals(5, arbol.buscar(5));
    }

    @Test
    public void testBuscarClaveMaxima() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        assertEquals(20, arbol.buscar(20));
    }

    @Test
    public void testBuscarClaveNoExistente() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        assertNull(arbol.buscar(15));
    }

    @Test
    public void testBuscarClaveEnNodoHoja() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12); // Esto debería provocar una división
        assertEquals(6, arbol.buscar(6));
    }

    @Test
    public void testBuscarClaveEnNodoInterno() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12); // Esto debería provocar una división
        assertEquals(10, arbol.buscar(10));
    }

    @Test
    public void testEliminar() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);

        assertTrue(arbol.eliminar(10));
        assertNull(arbol.buscar(10));
        assertFalse(arbol.eliminar(15));
    }
    @Test
    public void testEliminarEnArbolVacio() {
        assertFalse(arbol.eliminar(10)); // No se puede eliminar de un árbol vacío
    }

    @Test
    public void testEliminarClaveMinima() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        assertTrue(arbol.eliminar(5));
        assertNull(arbol.buscar(5));
        assertEquals(List.of(10, 20), arbol.inOrden());
    }

    @Test
    public void testEliminarClaveMaxima() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        assertTrue(arbol.eliminar(20));
        assertNull(arbol.buscar(20));
        assertEquals(List.of(5, 10), arbol.inOrden());
    }

    @Test
    public void testEliminarClaveNoExistente() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        assertFalse(arbol.eliminar(15)); // La clave 15 no existe
    }

    @Test
    public void testEliminarClaveEnNodoHoja() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12);
        assertTrue(arbol.eliminar(6));
        assertNull(arbol.buscar(6));
        assertEquals(List.of(5, 10, 12, 20), arbol.inOrden());
    }


    @Test
    public void testEliminarClaveQueProvocaFusion() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12);
        arbol.insertar(30, 30);
        arbol.insertar(7, 7);
        arbol.insertar(17, 17);

        assertTrue(arbol.eliminar(12));
        assertNull(arbol.buscar(12));
        assertEquals(List.of(5, 6, 7, 10, 17, 20, 30), arbol.inOrden());
    }

    @Test
    public void testPreOrden() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12);
        arbol.insertar(30, 30);
        arbol.insertar(7, 7);
        arbol.insertar(17, 17);

        List<Integer> preOrden = arbol.preOrden();
        assertEquals(List.of(10, 20, 5, 6, 7, 12, 17, 30), preOrden);
    }

    @Test
    public void testInOrden() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12);
        arbol.insertar(30, 30);
        arbol.insertar(7, 7);
        arbol.insertar(17, 17);

        List<Integer> inOrden = arbol.inOrden();
        assertEquals(List.of(5, 6, 7, 10, 12, 17, 20, 30), inOrden);
    }

    @Test
    public void testPostOrden() {
        arbol.insertar(10, 10);
        arbol.insertar(20, 20);
        arbol.insertar(5, 5);
        arbol.insertar(6, 6);
        arbol.insertar(12, 12);
        arbol.insertar(30, 30);
        arbol.insertar(7, 7);
        arbol.insertar(17, 17);

        List<Integer> postOrden = arbol.postOrden();
        assertEquals(List.of(5, 6, 7, 12, 17, 30, 10, 20), postOrden);
    }
}