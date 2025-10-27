package service;

import repository.ProductoRepository;
import Model.Producto;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProductoService {

    private final ProductoRepository repo;
    private final List<Producto> productos;
    private final AtomicInteger nextId;

    public ProductoService() {
        this.repo = new ProductoRepository();
        this.productos = repo.cargarProductos(); // Carga los datos existentes

        // Calcula el siguiente ID basándose en los datos cargados.
        int maxId = productos.stream()
                .mapToInt(Producto::getId)
                .max()
                .orElse(0); // Si no hay datos, empieza en 0.
        this.nextId = new AtomicInteger(maxId + 1);

    }

    /**
     * Obtiene todos los productos y los mapea a un formato de fila de JTable.
     * {"ID","Nombre","Categoría","Precio","Stock","Activo"}
     */
    public List<Object[]> obtenerProductosParaTabla() {
        return productos.stream()
                .map(p -> new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getPrecio(),
                        p.getStock(),
                        p.isActivo()
                })
                .collect(Collectors.toList());
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

    public void eliminar(int id) {
        productos.removeIf(p -> p.getId() == id);
        repo.guardarProductos(productos);
    }

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
}