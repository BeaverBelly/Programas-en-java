import java.io.Serializable;

public class DetalleCompra implements Serializable {
    private String producto;
    private String categoria; // <-- agregado
    private double precio;
    private int cantidad;

    public DetalleCompra(String producto, String categoria, double precio, int cantidad) {
        this.producto = producto;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getProducto() { return producto; }
    public String getCategoria() { return categoria; } // <-- getter
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    public double getSubtotal() {
        return precio * cantidad;
    }
}
