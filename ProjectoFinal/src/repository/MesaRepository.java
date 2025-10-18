package repository;

import java.util.List;

public class MesaRepository extends ArchivoRepository<Object[]> {

    private static final String ARCHIVO = "mesas.dat";

    public MesaRepository() {
        super("mesas"); // carpeta ~/.resto/mesas/
    }

    public void guardarMesas(List<Object[]> mesas) {
        guardar(mesas, ARCHIVO);
    }

    public List<Object[]> cargarMesas() {
        return cargar(ARCHIVO);
    }
}
