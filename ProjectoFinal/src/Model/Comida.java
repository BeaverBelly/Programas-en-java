package Model;

/**
 * Subclase que representa una Comida en el menú.
 */
public class Comida extends Producto {

    public Comida(int id, String nombre, double precio, int stock, boolean activo) {
        super(id, nombre, "Comida", precio, stock, activo);
    }

    @Override
    public String obtenerTipo() {
        return "Comida";
    }
}

