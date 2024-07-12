package Proyecto.Package;

import java.util.List;

//Clase encargada de simular la base de datos
public class BaseDeDatos {
    private TArbolB<RegistroBD> indice;

    public BaseDeDatos(int t) {
        this.indice = new TArbolB<>(t);
    }

    /**
     * Inserta un nuevo registro en la base de datos.
     *
     * @param id   Identificador del registro.
     * @param data Datos del registro.
     */

    public void insertarRegistro(int id, String data) {
        RegistroBD registro = new RegistroBD(id, data);
        indice.insertar(id, registro);
    }

    /**
     * Busca un registro en la base de datos por su identificador.
     *
     * @param id Identificador del registro a buscar.
     * @return El registro encontrado, o null si no se encuentra.
     */

    public RegistroBD buscarRegistro(int id) {
        return indice.buscar(id);
    }

    /**
     * Elimina un registro de la base de datos por su identificador.
     *
     * @param id Identificador del registro a eliminar.
     * @return true si el registro fue eliminado, false en caso contrario.
     */

    public boolean eliminarRegistro(int id) {
        return indice.eliminar(id);
    }

    /**
     * Lista todos los registros de la base de datos en preorden.
     *
     * @return Lista de registros en preorden.
     */

    public List<RegistroBD> listarRegistrosPreOrden() {
        return indice.preOrden();
    }

    /**
     * Lista todos los registros de la base de datos en inorden.
     *
     * @return Lista de registros en inorden.
     */

    public List<RegistroBD> listarRegistrosInOrden() {
        return indice.inOrden();
    }

    /**
     * Lista todos los registros de la base de datos en postorden.
     *
     * @return Lista de registros en postorden.
     */

    public List<RegistroBD> listarRegistrosPostOrden() {
        return indice.postOrden();
    }

    /**
     * Fusiona la base de datos actual con otra base de datos.
     *
     * @param otraBD La otra base de datos a fusionar.
     */

    public void merge(BaseDeDatos otraBD) {
        indice.merge(otraBD.indice);
    }
}
