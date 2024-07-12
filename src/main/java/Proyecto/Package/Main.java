package Proyecto.Package;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        baseDeDatos();
        medirPerformance(1000000);
    }

    //Clase encargada de generar datos aleatorios
    public static void cargarDatosAleatorios(TArbolB<RegistroBD> arbolB, int cantidad) {
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int id = random.nextInt(10000000);
            String data = "Data" + id;
            arbolB.insertar(id, new RegistroBD(id, data));
        }
    }

    //Clase encargada de la simulación de la base de datos
    public static void baseDeDatos() {
        BaseDeDatos baseDeDatos = new BaseDeDatos(2);
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("Bienvenido a la base de datos de Data Secure");
                System.out.println("\n1. Insertar registro");
                System.out.println("2. Buscar registro");
                System.out.println("3. Eliminar registro");
                System.out.println("4. Listar registros (InOrden)");
                System.out.println("5. Listar registros (PreOrden)");
                System.out.println("6. Listar registros (PostOrden)");
                System.out.println("7. Generar registros");
                System.out.println("8. Salir");
                System.out.print("Seleccione una opción: ");

                if (!scanner.hasNextInt()) {
                    throw new IllegalArgumentException("Debe ingresar un número.");
                }
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese ID: ");
                        if (!scanner.hasNextInt()) {
                            throw new IllegalArgumentException("ID debe ser un número.");
                        }
                        int id = scanner.nextInt();
                        System.out.print("Ingrese Datos: ");
                        scanner.nextLine();
                        String datos = scanner.nextLine();
                        baseDeDatos.insertarRegistro(id, datos);
                        System.out.println("Registro insertado.");
                        break;
                    case 2:
                        System.out.print("Ingrese ID a buscar: ");
                        if (!scanner.hasNextInt()) {
                            throw new IllegalArgumentException("ID debe ser un número.");
                        }
                        id = scanner.nextInt();
                        RegistroBD registro = baseDeDatos.buscarRegistro(id);
                        if (registro != null) {
                            System.out.println("Registro encontrado: " + registro);
                        } else {
                            System.out.println("Registro no encontrado.");
                        }
                        break;
                    case 3:
                        System.out.print("Ingrese ID a eliminar: ");
                        if (!scanner.hasNextInt()) {
                            throw new IllegalArgumentException("ID debe ser un número.");
                        }
                        id = scanner.nextInt();
                        boolean eliminado = baseDeDatos.eliminarRegistro(id);
                        if (eliminado) {
                            System.out.println("Registro eliminado.");
                        } else {
                            System.out.println("Registro no encontrado.");
                        }
                        break;
                    case 4:
                        List<RegistroBD> registrosInOrden = baseDeDatos.listarRegistrosInOrden();
                        System.out.println("Registros en la base de datos (InOrden):");
                        for (RegistroBD reg : registrosInOrden) {
                            System.out.println(reg);
                        }
                        break;
                    case 5:
                        List<RegistroBD> registrosPreOrden = baseDeDatos.listarRegistrosPreOrden();
                        System.out.println("Registros en la base de datos (PreOrden):");
                        for (RegistroBD reg : registrosPreOrden) {
                            System.out.println(reg);
                        }
                        break;
                    case 6:
                        List<RegistroBD> registrosPostOrden = baseDeDatos.listarRegistrosPostOrden();
                        System.out.println("Registros en la base de datos (PostOrden):");
                        for (RegistroBD reg : registrosPostOrden) {
                            System.out.println(reg);
                        }
                        break;
                    case 7:
                        System.out.print("Ingrese la cantidad de registros a generar: ");
                        if (!scanner.hasNextInt()) {
                            throw new IllegalArgumentException("La cantidad debe ser un número.");
                        }
                        int cantidad = scanner.nextInt();
                        Random random = new Random();
                        for (int i = 0; i < cantidad; i++) {
                            int idRandom = random.nextInt(1000000);
                            String datosRandom = "Datos" + idRandom; // Genera datos asociados a los IDs
                            baseDeDatos.insertarRegistro(idRandom, datosRandom);
                        }
                        System.out.println("Registros generados e insertados.");
                        break;
                    case 8:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    //Clase encargada de realizar las mediciónes de performance de las operaciónes básicas del TDA, insertar, buscar y eliminar.
    public static void medirPerformance(int numElements) {
        TArbolB<Integer> arbolB = new TArbolB<>(2);
        TArbolBB<Integer> arbolBB = new TArbolBB<>();
        Random random = new Random();

        Medicion medicionInsercionB = medirInsercion(arbolB, numElements, random);
        Medicion medicionInsercionBB = medirInsercion(arbolBB, numElements, random);
        System.out.println(medicionInsercionB);
        System.out.println(medicionInsercionBB);

        Medicion medicionBusquedaB = medirBusqueda(arbolB, numElements, random);
        Medicion medicionBusquedaBB = medirBusqueda(arbolBB, numElements, random);
        System.out.println(medicionBusquedaB);
        System.out.println(medicionBusquedaBB);

        Medicion medicionEliminacionB = medirEliminacion(arbolB, numElements, random);
        Medicion medicionEliminacionBB = medirEliminacion(arbolBB, numElements, random);
        System.out.println(medicionEliminacionB);
        System.out.println(medicionEliminacionBB);
    }

    private static Medicion medirInsercion(Object arbol, int numElements, Random random) {
        long inicio = System.nanoTime();
        for (int i = 0; i < numElements; i++) {
            int clave = random.nextInt(numElements);
            if (arbol instanceof TArbolB) {
                ((TArbolB<Integer>) arbol).insertar(clave, clave);
            } else if (arbol instanceof TArbolBB) {
                ((TArbolBB<Integer>) arbol).insertar(clave, clave);
            }
        }
        long fin = System.nanoTime();
        long memoria = ObjectSizeFetcher.getObjectSize(arbol);
        String texto = "Inserción en " + arbol.getClass().getSimpleName();
        return new Medicion(texto, memoria, fin - inicio);
    }

    private static Medicion medirBusqueda(Object arbol, int numElements, Random random) {
        long inicio = System.nanoTime();
        for (int i = 0; i < numElements; i++) {
            int clave = random.nextInt(numElements);
            if (arbol instanceof TArbolB) {
                ((TArbolB<Integer>) arbol).buscar(clave);
            } else if (arbol instanceof TArbolBB) {
                ((TArbolBB<Integer>) arbol).buscar(clave);
            }
        }
        long fin = System.nanoTime();
        long memoria = ObjectSizeFetcher.getObjectSize(arbol);
        String texto = "Búsqueda en " + arbol.getClass().getSimpleName();
        return new Medicion(texto, memoria, fin - inicio);
    }

    private static Medicion medirEliminacion(Object arbol, int numElements, Random random) {
        long inicio = System.nanoTime();
        for (int i = 0; i < numElements; i++) {
            int clave = random.nextInt(numElements);
            if (arbol instanceof TArbolB) {
                ((TArbolB<Integer>) arbol).eliminar(clave);
            } else if (arbol instanceof TArbolBB) {
                ((TArbolBB<Integer>) arbol).eliminar(clave);
            }
        }
        long fin = System.nanoTime();
        long memoria = ObjectSizeFetcher.getObjectSize(arbol);
        String texto = "Eliminación en " + arbol.getClass().getSimpleName();
        return new Medicion(texto, memoria, fin - inicio);
    }
}