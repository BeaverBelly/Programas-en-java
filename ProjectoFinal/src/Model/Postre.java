package model;

/**
 * Subclase que representa un Postre en el menú.
 */
public class Postre extends Producto {

    public Postre(int id, String nombre, double precio, int stock, boolean activo) {
        super(id, nombre, "Postre", precio, stock, activo);
    }

    @Override
    public String obtenerTipo() {
        return "Postre";
    }
}
