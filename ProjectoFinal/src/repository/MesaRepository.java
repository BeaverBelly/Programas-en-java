package repository;

import model.mesa;
import java.util.List;

public class MesaRepository extends ArchivoRepository<mesa> {

    private static final String ARCHIVO = "mesas.dat";

    public MesaRepository() {
        // Usa el constructor que ya definiste en ArchivoRepository
        super("mesas");
    }

    public void guardarMesas(List<mesa> mesas) {
        guardar(mesas, ARCHIVO);
    }

    public List<mesa> cargarMesas() {
        return cargar(ARCHIVO);
    }
}
