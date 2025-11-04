package Model;

/**
 * Subclase que representa una Bebida en el menú.
 */
public class Bebida extends Producto {

    public Bebida(int id, String nombre, double precio, int stock, boolean activo) {
        super(id, nombre, "Bebida", precio, stock, activo);
    }

    @Override
    public String obtenerTipo() {
        return "Bebida";
    }
}
