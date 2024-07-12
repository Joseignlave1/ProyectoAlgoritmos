package Proyecto.Package;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TElementoBTest {
    private TElementoB<Integer> elementoB;

    @BeforeEach
    public void setUp() {
        elementoB = new TElementoB<>(2);
    }

    @Test
    public void testInsertarEnNodoNoLleno() {
        elementoB.insertarEnNodoNoLleno(10, 10);
        elementoB.insertarEnNodoNoLleno(20, 20);
        elementoB.insertarEnNodoNoLleno(5, 5);

        List<Comparable> expectedClaves = List.of(5, 10, 20);
        assertEquals(expectedClaves, elementoB.getClaves());
    }


    @Test
    public void testDividirHijo() {
        int t = 3;
        TElementoB<Integer> nodoPadre = new TElementoB<>(t);
        TElementoB<Integer> nodoLleno = new TElementoB<>(t);

        // Llenar el nodoLleno con claves y datos (suponiendo t = 3)
        nodoLleno.getClaves().add(10);
        nodoLleno.getClaves().add(20);
        nodoLleno.getClaves().add(30);
        nodoLleno.getClaves().add(40);
        nodoLleno.getClaves().add(50);

        nodoLleno.getDatos().add(100);
        nodoLleno.getDatos().add(200);
        nodoLleno.getDatos().add(300);
        nodoLleno.getDatos().add(400);
        nodoLleno.getDatos().add(500);

        for (int i = 0; i < 6; i++) {
            nodoLleno.getHijos().add(new TElementoB<>(t));
        }

        nodoPadre.getHijos().add(nodoLleno);

        nodoPadre.dividirHijo(0, nodoLleno);

        assertEquals(1, nodoPadre.getClaves().size());
        assertEquals(30, nodoPadre.getClaves().get(0));

        assertEquals(1, nodoPadre.getClaves().size());

        assertEquals(2, nodoLleno.getClaves().size());
        assertEquals(2, nodoPadre.getHijos().get(1).getClaves().size());

        assertEquals(10, nodoLleno.getClaves().get(0));
        assertEquals(20, nodoLleno.getClaves().get(1));

        assertEquals(40, nodoPadre.getHijos().get(1).getClaves().get(0));
        assertEquals(50, nodoPadre.getHijos().get(1).getClaves().get(1));

        assertEquals(100, nodoLleno.getDatos().get(0));
        assertEquals(200, nodoLleno.getDatos().get(1));

        assertEquals(400, nodoPadre.getHijos().get(1).getDatos().get(0));
        assertEquals(500, nodoPadre.getHijos().get(1).getDatos().get(1));

        if (!nodoLleno.getHijos().isEmpty()) {
            assertEquals(3, nodoLleno.getHijos().size());
            assertEquals(3, nodoPadre.getHijos().get(1).getHijos().size());
        }
    }
    @Test
    void testEncontrarClave() {
        TElementoB<Integer> nodo = new TElementoB<>(3); // Asumiendo t = 3

        nodo.getClaves().add(10);
        nodo.getClaves().add(20);
        nodo.getClaves().add(30);
        nodo.getClaves().add(40);
        nodo.getClaves().add(50);

        // Caso 1: clave está en el nodo
        assertEquals(0, nodo.encontrarClave(10)); // Primera posición
        assertEquals(2, nodo.encontrarClave(30)); // Posición en el medio
        assertEquals(4, nodo.encontrarClave(50)); // Última posición

        // Caso 2: clave no está en el nodo y debería insertarse antes de la primera clave
        assertEquals(0, nodo.encontrarClave(5));

        // Caso 3: clave no está en el nodo y debería insertarse en una posición intermedia
        assertEquals(1, nodo.encontrarClave(15));
        assertEquals(3, nodo.encontrarClave(35));

        // Caso 4: clave no está en el nodo y debería insertarse después de la última clave
        assertEquals(5, nodo.encontrarClave(55));
    }

    @Test
    public void testObtenerPredecesor() {
        // Crear un nodo con hijos para encontrar el predecesor
        TElementoB<Integer> nodoHijoIzq = new TElementoB<>(2);
        TElementoB<Integer> nodoHijoDer = new TElementoB<>(2);

        nodoHijoIzq.insertarEnNodoNoLleno(3, 3);
        nodoHijoIzq.insertarEnNodoNoLleno(7, 7);
        nodoHijoDer.insertarEnNodoNoLleno(15, 15);
        nodoHijoDer.insertarEnNodoNoLleno(20, 20);

        elementoB.insertarEnNodoNoLleno(10, 10);
        elementoB.insertarEnNodoNoLleno(12, 12);
        elementoB.getHijos().add(nodoHijoIzq);
        elementoB.getHijos().add(nodoHijoDer);

        Comparable predecesor = elementoB.obtenerPredecesor(1); // Predecesor de 12

        assertEquals(20, predecesor);
    }

    @Test
    public void testObtenerSucesor() {
        // Crear un nodo con hijos para encontrar el sucesor
        TElementoB<Integer> nodoHijoIzq = new TElementoB<>(2);
        TElementoB<Integer> nodoHijoDer = new TElementoB<>(2);

        nodoHijoIzq.insertarEnNodoNoLleno(5, 5);
        nodoHijoDer.insertarEnNodoNoLleno(15, 15);
        nodoHijoDer.insertarEnNodoNoLleno(20, 20);

        elementoB.insertarEnNodoNoLleno(10, 10);
        elementoB.getHijos().add(nodoHijoIzq);
        elementoB.getHijos().add(nodoHijoDer);

        Comparable sucesor = elementoB.obtenerSucesor(0); // Sucesor de 10

        assertEquals(15, sucesor);
    }
    @Test
    void testEliminarDesdeNodoInterno() {
        int t = 3;
        TElementoB<Integer> nodo = new TElementoB<>(t);

        // Agregar claves y datos al nodo
        nodo.getClaves().add(10);
        nodo.getClaves().add(20);
        nodo.getClaves().add(30);
        nodo.getDatos().add(100);
        nodo.getDatos().add(200);
        nodo.getDatos().add(300);

        // Crear hijos y agregar claves a los hijos
        TElementoB<Integer> hijoIzquierdo = new TElementoB<>(t);
        hijoIzquierdo.getClaves().add(5);
        hijoIzquierdo.getClaves().add(8);
        hijoIzquierdo.getDatos().add(50);
        hijoIzquierdo.getDatos().add(80);

        TElementoB<Integer> hijoDerecho = new TElementoB<>(t);
        hijoDerecho.getClaves().add(25);
        hijoDerecho.getClaves().add(28);
        hijoDerecho.getDatos().add(250);
        hijoDerecho.getDatos().add(280);

        // Agregar hijos al nodo
        nodo.getHijos().add(hijoIzquierdo);
        nodo.getHijos().add(hijoDerecho);

        // Caso: Eliminar clave con predecesor
        nodo.eliminarDesdeNodoInterno(0);

        assertEquals(2, nodo.getClaves().size());
        assertEquals(20, nodo.getClaves().get(0));
        assertEquals(30, nodo.getClaves().get(1));
    }

    @Test
    void testRellenarTomarPrestadoDeAnterior() {
        int t = 3;
        TElementoB<Integer> nodo = new TElementoB<>(t);

        // Crear hijos
        TElementoB<Integer> hijoIzquierdo = new TElementoB<>(t);
        hijoIzquierdo.getClaves().add(5);
        hijoIzquierdo.getClaves().add(8);
        hijoIzquierdo.getClaves().add(12); // Tiene más de t claves
        hijoIzquierdo.getDatos().add(50);
        hijoIzquierdo.getDatos().add(80);
        hijoIzquierdo.getDatos().add(120);

        TElementoB<Integer> hijoDerecho = new TElementoB<>(t);
        hijoDerecho.getClaves().add(20);
        hijoDerecho.getDatos().add(200);

        // Agregar hijos al nodo
        nodo.getHijos().add(hijoIzquierdo);
        nodo.getHijos().add(hijoDerecho);

        nodo.getClaves().add(15); // Clave en el nodo padre
        nodo.getDatos().add(150); // Dato correspondiente en el nodo padre

        // Ejecutar rellenar
        nodo.rellenar(1);

        // Verificar que se tomó prestado del hijo izquierdo
        assertEquals(2, hijoIzquierdo.getClaves().size());
        assertEquals(2, hijoDerecho.getClaves().size());
        assertEquals(Integer.valueOf(15), hijoDerecho.getClaves().get(0));
        assertEquals(Integer.valueOf(12), nodo.getClaves().get(0));
    }
    @Test
    void testRellenarTomarPrestadoDeSiguiente() {
        int t = 3;
        TElementoB<Integer> nodo = new TElementoB<>(t);

        // Crear hijos
        TElementoB<Integer> hijoIzquierdo = new TElementoB<>(t);
        hijoIzquierdo.getClaves().add(5);
        hijoIzquierdo.getDatos().add(50);

        TElementoB<Integer> hijoDerecho = new TElementoB<>(t);
        hijoDerecho.getClaves().add(20);
        hijoDerecho.getClaves().add(25);
        hijoDerecho.getClaves().add(30); // Tiene más de t claves
        hijoDerecho.getDatos().add(200);
        hijoDerecho.getDatos().add(250);
        hijoDerecho.getDatos().add(300);

        // Agregar hijos al nodo
        nodo.getHijos().add(hijoIzquierdo);
        nodo.getHijos().add(hijoDerecho);

        nodo.getClaves().add(15); // Clave en el nodo padre
        nodo.getDatos().add(150); // Dato correspondiente en el nodo padre

        // Ejecutar rellenar
        nodo.rellenar(0);

        // Verificar que se tomó prestado del hijo derecho
        assertEquals(2, hijoIzquierdo.getClaves().size());
        assertEquals(2, hijoDerecho.getClaves().size());
        assertEquals(Integer.valueOf(15), hijoIzquierdo.getClaves().get(1));
        assertEquals(Integer.valueOf(25), hijoDerecho.getClaves().get(0));
    }

    @Test
    void testRellenarCombinar() {
        int t = 3;
        TElementoB<Integer> nodo = new TElementoB<>(t);

        // Crear hijos
        TElementoB<Integer> hijoIzquierdo = new TElementoB<>(t);
        hijoIzquierdo.getClaves().add(5);
        hijoIzquierdo.getDatos().add(50);

        TElementoB<Integer> hijoDerecho = new TElementoB<>(t);
        hijoDerecho.getClaves().add(20);
        hijoDerecho.getDatos().add(200);

        // Agregar hijos al nodo
        nodo.getHijos().add(hijoIzquierdo);
        nodo.getHijos().add(hijoDerecho);

        nodo.getClaves().add(15); // Clave en el nodo padre
        nodo.getDatos().add(150); // Dato correspondiente en el nodo padre

        // Ejecutar rellenar
        nodo.rellenar(0);

        // Verificar que se combinaron los hijos
        assertEquals(3, hijoIzquierdo.getClaves().size());
        assertEquals(1, nodo.getHijos().size());
        assertEquals(Integer.valueOf(5), hijoIzquierdo.getClaves().get(0));
        assertEquals(Integer.valueOf(15), hijoIzquierdo.getClaves().get(1));
        assertEquals(Integer.valueOf(20), hijoIzquierdo.getClaves().get(2));
    }

    @Test
    void testObtenerTamaño() {
        int t = 3;

        // Crear nodo raíz
        TElementoB<Integer> nodoRaiz = new TElementoB<>(t);
        nodoRaiz.getClaves().add(10);
        nodoRaiz.getClaves().add(20);

        // Crear nodos hijos
        TElementoB<Integer> hijoIzquierdo = new TElementoB<>(t);
        hijoIzquierdo.getClaves().add(5);

        TElementoB<Integer> hijoDerecho = new TElementoB<>(t);
        hijoDerecho.getClaves().add(30);
        hijoDerecho.getClaves().add(40);

        // Agregar hijos a la raíz
        nodoRaiz.getHijos().add(hijoIzquierdo);
        nodoRaiz.getHijos().add(hijoDerecho);

        // Ejecutar el método obtenerTamaño
        int tamaño = nodoRaiz.obtenerTamaño();

        // Verificar el tamaño total del árbol
        assertEquals(5, tamaño); // 2 claves en la raíz + 1 en el hijo izquierdo + 2 en el hijo derecho
    }

    @Test
    void testInsertarClaveYDato() {
        int t = 3;
        TElementoB<Integer> nodo = new TElementoB<>(t);

        nodo.insertarClaveYDato(10, 100);
        nodo.insertarClaveYDato(20, 200);
        nodo.insertarClaveYDato(15, 150);

        assertEquals(3, nodo.getClaves().size());
        assertEquals(3, nodo.getDatos().size());

        assertEquals(10, nodo.getClaves().get(0));
        assertEquals(15, nodo.getClaves().get(1));
        assertEquals(20, nodo.getClaves().get(2));

        assertEquals(100, nodo.getDatos().get(0));
        assertEquals(150, nodo.getDatos().get(1));
        assertEquals(200, nodo.getDatos().get(2));
    }

    @Test
    void testDividirNodo() {
        int t = 2;
        TElementoB<Integer> nodo = new TElementoB<>(t);

        // Insertar claves y datos en el nodo para llenarlo
        nodo.insertarClaveYDato(10, 100);
        nodo.insertarClaveYDato(20, 200);
        nodo.insertarClaveYDato(30, 300);
        nodo.insertarClaveYDato(40, 400);

        // Ejecutar el método dividirNodo
        nodo.dividirNodo();

        // Verificar la división del nodo
        TElementoB<Integer> padre = nodo.obtenerPadre();
        assertNotNull(padre);
        assertEquals(1, padre.getClaves().size());
        assertEquals(30, padre.getClaves().get(0));

        TElementoB<Integer> nuevoNodo = padre.getHijos().get(1);
        assertNotNull(nuevoNodo);
        assertEquals(1, nuevoNodo.getClaves().size());
        assertEquals(40, nuevoNodo.getClaves().get(0));
        assertEquals(20, nodo.getClaves().get(1)); // Verificar que la clave en el nodo original es 20

        // Verificar datos en el nodo padre
        assertEquals(1, padre.getDatos().size());
        assertEquals(300, padre.getDatos().get(0));

        // Verificar datos en el nuevo nodo
        assertEquals(1, nuevoNodo.getDatos().size());
        assertEquals(400, nuevoNodo.getDatos().get(0));
    }
}

