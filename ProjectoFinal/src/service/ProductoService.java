package service;

import repository.ProductoRepository;
import model.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Servicio que maneja productos del menú (Comida, Bebida, Postre).
 */
public class ProductoService {

    private final ProductoRepository repo;
    private final List<Producto> productos;
    private final AtomicInteger nextId;

    public ProductoService() {
        this.repo = new ProductoRepository();
        this.productos = repo.cargarProductos(); // Carga los productos existentes

        int maxId = productos.stream()
                .mapToInt(Producto::getId)
                .max()
                .orElse(0);
        this.nextId = new AtomicInteger(maxId + 1);
    }

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

    /**
     * Agrega un producto según su categoría.
     */
    public void agregar(String nombre, String categoria, double precio, int stock, boolean activo) {
        Producto nuevo;
        switch (categoria.toLowerCase()) {
            case "comida":
                nuevo = new Comida(nextId.getAndIncrement(), nombre, precio, stock, activo);
                break;
            case "bebida":
                nuevo = new Bebida(nextId.getAndIncrement(), nombre, precio, stock, activo);
                break;
            case "postre":
                nuevo = new Postre(nextId.getAndIncrement(), nombre, precio, stock, activo);
                break;
            default:
                throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }

        productos.add(nuevo);
        repo.guardarProductos(productos);
    }

    public void eliminar(int id) {
        productos.removeIf(p -> p.getId() == id);
        repo.guardarProductos(productos);
    }

    public void modificar(int id, String nombre, String categoria, double precio, int stock, boolean activo) {
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getId() == id) {
                Producto modificado;
                switch (categoria.toLowerCase()) {
                    case "comida":
                        modificado = new Comida(id, nombre, precio, stock, activo);
                        break;
                    case "bebida":
                        modificado = new Bebida(id, nombre, precio, stock, activo);
                        break;
                    case "postre":
                        modificado = new Postre(id, nombre, precio, stock, activo);
                        break;
                    default:
                        throw new IllegalArgumentException("Categoría no válida: " + categoria);
                }
                productos.set(i, modificado);
                break;
            }
        }
        repo.guardarProductos(productos);
    }
}
