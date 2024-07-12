package Proyecto.Package;

//Clase encargada de simular el registro para la base de datos

public class RegistroBD {
    private int id;
    private String data;

    public RegistroBD(int id, String data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Obtiene el identificador del registro.
     *
     * @return El identificador del registro.
     */

    public int getId() {
        return id;
    }

    /**
     * Obtiene los datos del registro.
     *
     * @return Los datos del registro.
     */

    public String getData() {
        return data;
    }

    /**
     * Devuelve una representación en forma de cadena del registro.
     *
     * @return Una cadena que representa el registro.
     */

    @Override
    public String toString() {
        return "ID: " + id + ", Data: " + data;
    }
}
