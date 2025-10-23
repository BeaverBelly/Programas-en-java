package Model;

import java.io.Serializable;

/**
 * Modelo de datos para representar un Producto de la Carta (Comida, Bebida, Postre).
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private boolean activo;

    public Producto(int id, String nombre, String categoria, double precio, int stock, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isActivo() { return activo; }

    // --- Setters (Para Modificar) ---
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }
    public void setActivo(boolean activo) { this.activo = activo; }
}