package exceptions;

import javax.swing.JOptionPane;

/**
 * Clase que maneja validaciones de productos y excepciones de stock insuficiente.
 */
public class StockInsuficienteException {

    private StockInsuficienteException() {
        // Constructor privado para impedir instanciación
    }

    // Lanza excepción si el stock es insuficiente
    public static void validarStock(int stockActual, int cantidadSolicitada, String nombreProducto) throws Exception {
        if (cantidadSolicitada > stockActual) {
            throw new Exception("Stock insuficiente para " + nombreProducto + ". Disponible: " + stockActual);
        }
    }

    // Valida que el nombre no esté vacío ni sea solo número
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para el producto.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nombre.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El nombre del producto no puede ser solo un número.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Valida que el precio no sea negativo
    public static boolean validarPrecio(double precio) {
        if (precio < 0) {
            JOptionPane.showMessageDialog(null, "El precio no puede ser negativo.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Valida que el stock no sea negativo
    public static boolean validarCantidad(int stock) {
        if (stock < 0) {
            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
