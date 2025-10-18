package service;

import repository.MesaRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MesaService {

    private final MesaRepository repo;
    private final List<Object[]> mesas;
    private final AtomicInteger nextId;

    public MesaService() {
        this.repo = new MesaRepository();
        this.mesas = repo.cargarMesas();

        int maxId = mesas.stream()
                .mapToInt(f -> (Integer) f[0])
                .max()
                .orElse(0);
        this.nextId = new AtomicInteger(maxId + 1);
    }

    public List<Object[]> listar() {
        return new ArrayList<>(mesas);
    }

    public void agregar(String mozo, String nombreMesa, String estado) {
        Object[] nueva = {
                nextId.getAndIncrement(),
                mozo.isEmpty() ? "—" : mozo,
                nombreMesa,
                estado
        };
        mesas.add(nueva);
        repo.guardarMesas(mesas);
    }

    public void eliminar(int id) {
        mesas.removeIf(m -> ((Integer) m[0]) == id);
        repo.guardarMesas(mesas);
    }

    public void cambiarEstado(int id, String nuevoEstado) {
        for (Object[] fila : mesas) {
            if (((Integer) fila[0]) == id) {
                fila[3] = nuevoEstado;
                break;
            }
        }
        repo.guardarMesas(mesas);
    }

    public void asignarMozo(int id, String mozo) {
        for (Object[] fila : mesas) {
            if (((Integer) fila[0]) == id) {
                fila[1] = mozo;
                break;
            }
        }
        repo.guardarMesas(mesas);
    }
}
