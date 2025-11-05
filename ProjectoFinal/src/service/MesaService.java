package service;

import repository.MesaRepository;
import model.mesa;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MesaService {

    private final MesaRepository repo;
    private final List<mesa> mesas;
    private final AtomicInteger nextId;

    public MesaService() {
        this.repo = new MesaRepository();
        this.mesas = repo.cargarMesas();

        int maxId = mesas.stream()
                .mapToInt(mesa::getId)
                .max()
                .orElse(0);
        this.nextId = new AtomicInteger(maxId + 1);
    }

    public List<mesa> listar() {
        return new ArrayList<>(mesas);
    }

    public void agregar(String mozo, String nombreMesa, String estado) {
        mesa nueva = new mesa(nextId.getAndIncrement(),
                mozo.isEmpty() ? "—" : mozo,
                nombreMesa,
                estado);
        mesas.add(nueva);
        repo.guardarMesas(mesas);
    }

    public void eliminar(int id) {
        mesas.removeIf(m -> m.getId() == id);
        repo.guardarMesas(mesas);
    }

    public void cambiarEstado(int id, String nuevoEstado) {
        mesa encontrada = buscarPorId(id);
        if (encontrada == null) return;

        encontrada.setEstado(nuevoEstado);
        repo.guardarMesas(mesas);
    }

    public void asignarMozo(int id, String mozo) {
        mesa encontrada = buscarPorId(id);
        if (encontrada == null) return;

        encontrada.setMozo(mozo.isEmpty() ? "—" : mozo);
        repo.guardarMesas(mesas);
    }

    public List<String> obtenerNombresDeMesas() {
        return mesas.stream().map(mesa::getNombre).toList();
    }

    private mesa buscarPorId(int id) {
        for (mesa m : mesas) {
            if (m.getId() == id) return m;
        }
        return null;
    }
}
