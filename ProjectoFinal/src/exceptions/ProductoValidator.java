package exceptions;

/**
 * Clase que contiene las validaciones relacionadas con productos.
 */
public final class ProductoValidator {

    private ProductoValidator() {
        // Constructor privado para impedir instanciación
    }

    //Valida el stock antes de realizar una venta o descuento
    public static void validarStock(int stockActual, int cantidadSolicitada, String nombreProducto)
            throws StockInsuficienteException {
        if (cantidadSolicitada > stockActual) {
            throw new StockInsuficienteException(
                    "Stock insuficiente para " + nombreProducto + ". Disponible: " + stockActual,
                    StockInsuficienteException.Codigo.STOCK_INSUFICIENTE
            );
        }
    }

    // Valida el nombre del producto
    public static void validarNombre(String nombre) throws StockInsuficienteException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new StockInsuficienteException(
                    "Debe ingresar un nombre para el producto.",
                    StockInsuficienteException.Codigo.NOMBRE_VACIO
            );
        }
        if (nombre.matches("\\d+")) {
            throw new StockInsuficienteException(
                    "El nombre del producto no puede ser solo un número.",
                    StockInsuficienteException.Codigo.NOMBRE_SOLO_NUMEROS
            );
        }
    }

    //Valida el precio del producto
    public static void validarPrecio(double precio) throws StockInsuficienteException {
        if (precio < 0) {
            throw new StockInsuficienteException(
                    "El precio no puede ser negativo.",
                    StockInsuficienteException.Codigo.PRECIO_NEGATIVO
            );
        }
    }

    //Valida la cantidad en stock
    public static void validarCantidad(int stock) throws StockInsuficienteException {
        if (stock < 0) {
            throw new StockInsuficienteException(
                    "El stock no puede ser negativo.",
                    StockInsuficienteException.Codigo.STOCK_NEGATIVO
            );
        }
    }
}
