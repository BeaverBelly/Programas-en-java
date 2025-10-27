package repository;

import Model.mesa;
import java.nio.file.Paths;
import java.util.List;

public class MesaRepository extends ArchivoRepository<mesa> {

    private static final String ARCHIVO = "mesas.dat";

    public MesaRepository() {

        super(Paths.get(System.getProperty("user.dir"), "data", "mesas"));
    }

    public void guardarMesas(List<mesa> mesas) {
        guardar(mesas, ARCHIVO);
    }

    public List<mesa> cargarMesas() {
        return cargar(ARCHIVO);
    }

}