import java.io.Serializable;

public class DetalleCompra implements Serializable {
    private String producto;
    private double precio;
    private int cantidad;

    public DetalleCompra(String producto, double precio, int cantidad) {
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getProducto() { return producto; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    public double getSubtotal() {
        return precio * cantidad;
    }
}
