package exceptions;

/**
 * Excepción que representa un problema relacionado con el stock o los datos del producto.
 */
public class StockInsuficienteException extends Exception {

    public enum Codigo {
        STOCK_INSUFICIENTE,
        NOMBRE_VACIO,
        NOMBRE_SOLO_NUMEROS,
        PRECIO_NEGATIVO,
        STOCK_NEGATIVO
    }

    private final Codigo codigo;

    public StockInsuficienteException(String mensaje, Codigo codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public Codigo getCodigo() {
        return codigo;
    }
}
