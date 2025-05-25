public class Producto {
    private String codigo;
    private String nombre;
    private int stock;
    private double precio;

    public Producto(String codigo, String nombre, int stock, double precio){
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    public void reponer(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reponer debe ser mayor a 0.");
        }
        stock += cantidad;
    }

    // Getters
    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    // Setter de precio con validación
    public void setPrecio(double nuevoPrecio) {
        if (nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El nuevo precio debe ser mayor a 0.");
        }
        this.precio = nuevoPrecio;
    }

}
