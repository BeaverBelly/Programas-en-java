package repository;

import java.nio.file.Paths;
import java.util.List;

public class MesaRepository extends ArchivoRepository<Object[]> {

    private static final String ARCHIVO = "mesas.dat";

    public MesaRepository() {
        // Guarda los datos dentro del proyecto, en /data/mesas/
        super(Paths.get(System.getProperty("user.dir"), "data", "mesas"));
    }

    public void guardarMesas(List<Object[]> mesas) {
        guardar(mesas, ARCHIVO);
    }

    public List<Object[]> cargarMesas() {
        return cargar(ARCHIVO);
    }
}
