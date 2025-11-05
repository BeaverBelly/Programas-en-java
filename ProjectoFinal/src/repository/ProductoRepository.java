package repository;

import model.Producto;
import java.nio.file.Paths;
import java.util.List;

public class ProductoRepository extends ArchivoRepository<Producto> {

    private static final String ARCHIVO = "productos.dat";

    public ProductoRepository() {
        // Guarda los datos en ProyectoFinal/data/carta/productos.dat
        super(Paths.get(System.getProperty("user.dir"), "data", "carta"));
    }

    public void guardarProductos(List<Producto> productos) {
        guardar(productos, ARCHIVO);
    }

    public List<Producto> cargarProductos() {
        return cargar(ARCHIVO);
    }
}