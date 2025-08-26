package Ejercicio4;

import java.util.HashMap;

public class CatalogoProductos {
    private HashMap<String, Producto> productos;

    public CatalogoProductos() {
        this.productos = new HashMap<>();
    }

    public void agregarProducto(Producto producto) {
        if (productos.containsKey(producto.getId())) {
            System.out.println("Ya existe un producto con el ID: " + producto.getId());
        } else {
            productos.put(producto.getId(), producto);
            System.out.println("Producto agregado: " + producto);
        }
    }

    // Obtener producto por ID
    public Producto obtenerProductoPorId(String id) {
        return productos.get(id); // devuelve null si no existe
    }

    // Eliminar producto por ID
    public void eliminarProducto(String id) {
        if (productos.containsKey(id)) {
            productos.remove(id);
            System.out.println("Producto eliminado con ID: " + id);
        } else {
            System.out.println("No existe un producto con ID: " + id);
        }
    }

    // Mostrar todos los productos
    public void mostrarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos en el catálogo.");
        } else {
            System.out.println("Catálogo de productos:");
            for (Producto p : productos.values()) {
                System.out.println(p);
            }
        }
    }
}

