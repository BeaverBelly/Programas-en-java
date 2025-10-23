package service;

import repository.ProductoRepository;
import Model.Producto;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductoService {

    private final ProductoRepository repo;
    private final List<Producto> productos;
    private final AtomicInteger nextId;

    public ProductoService() {
        this.repo = new ProductoRepository();
        this.productos = repo.cargarProductos();

        int maxId = productos.stream()
                .mapToInt(Producto::getId)
                .max()
                .orElse(0);
        this.nextId = new AtomicInteger(maxId + 1);
    }

    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public void agregar(String nombre, String categoria, double precio, int stock, boolean activo) {
        Producto nuevo = new Producto(
                nextId.getAndIncrement(),
                nombre,
                categoria,
                precio,
                stock,
                activo
        );
        productos.add(nuevo);
        repo.guardarProductos(productos);
    }

    // Método para eliminar un producto
    public void eliminar(int id) {
        productos.removeIf(p -> p.getId() == id);
        repo.guardarProductos(productos);
    }

    // Método para modificar un producto (recibe el objeto completo y busca el ID)
    public void modificar(int id, String nombre, String categoria, double precio, int stock, boolean activo) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                p.setNombre(nombre);
                p.setCategoria(categoria);
                p.setPrecio(precio);
                p.setStock(stock);
                p.setActivo(activo);
                break;
            }
        }
        repo.guardarProductos(productos);
    }

    // Puedes agregar aquí un método para buscar si lo necesitas en el futuro
}