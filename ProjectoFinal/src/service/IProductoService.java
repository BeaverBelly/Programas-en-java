package service;

import model.Producto;
import java.util.List;

public interface IProductoService {

    /**
     * Devuelve una lista de productos adaptada para mostrar en una tabla.
     */
    List<Object[]> obtenerProductosParaTabla();

    /**
     * Devuelve la lista completa de productos del menú.
     */
    List<Producto> listar();

    /**
     * Agrega un nuevo producto de la categoría especificada.
     */
    void agregar(String nombre, String categoria, double precio, int stock, boolean activo);

    /**
     * Elimina un producto por su ID.
     */
    void eliminar(int id);

    /**
     * Modifica los datos de un producto existente.
     */
    void modificar(int id, String nombre, String categoria, double precio, int stock, boolean activo);
}
