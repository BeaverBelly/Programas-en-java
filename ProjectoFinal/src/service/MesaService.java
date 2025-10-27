package service;

import repository.MesaRepository;
import Model.mesa;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servicio que contiene la lógica de negocio para la gestión de Mesas.
 * Actúa como intermediario entre la capa UI/Controlador y la capa de Persistencia (Repository).
 * * Trabaja directamente con objetos Mesa.
 */
public class MesaService {

    private final MesaRepository repo;
    private final List<mesa> mesas; // Almacenamiento principal: List<Mesa>
    private final AtomicInteger nextId;

    /**
     * Constructor: carga los datos iniciales y determina el próximo ID disponible.
     */
    public MesaService() {
        this.repo = new MesaRepository();
        // Carga la lista de objetos Mesa
        this.mesas = repo.cargarMesas();

        // Determina el ID más alto de las mesas cargadas
        int maxId = mesas.stream()
                .mapToInt(mesa::getId) // Usa el getter del objeto Mesa
                .max()
                .orElse(0);
        this.nextId = new AtomicInteger(maxId + 1);
    }

    // ------------------- OPERACIONES DE LECTURA -------------------

    /**
     * Devuelve una copia de la lista de mesas almacenadas.
     * @return List<Mesa> con todas las mesas.
     */
    public List<mesa> listar() {
        return new ArrayList<>(mesas);
    }

    // ------------------- OPERACIONES DE ESCRITURA -------------------

    /**
     * Agrega una nueva mesa al sistema.
     * @param mozo Nombre del mozo (o "—").
     * @param nombreMesa Nombre descriptivo de la mesa.
     * @param estado Estado inicial (LIBRE, OCUPADA, RESERVADA).
     */
    public void agregar(String mozo, String nombreMesa, String estado) {
        // Crea un nuevo objeto Mesa
        mesa nueva = new mesa(
                nextId.getAndIncrement(),
                mozo.isEmpty() ? "—" : mozo, // Asigna "—" si el mozo está vacío
                nombreMesa,
                estado
        );
        mesas.add(nueva);
        repo.guardarMesas(mesas);
    }

    /**
     * Elimina una mesa por su ID.
     * @param id El ID de la mesa a eliminar.
     */
    public void eliminar(int id) {
        // Usa el getter getId() para la condición de eliminación
        mesas.removeIf(m -> m.getId() == id);
        repo.guardarMesas(mesas);
    }

    /**
     * Cambia el estado de una mesa.
     * @param id El ID de la mesa a modificar.
     * @param nuevoEstado El nuevo estado (LIBRE, OCUPADA, RESERVADA).
     */
    public void cambiarEstado(int id, String nuevoEstado) {
        for (mesa mesa : mesas) {
            if (mesa.getId() == id) {
                // Usa el setter setEstado()
                mesa.setEstado(nuevoEstado);
                break;
            }
        }
        repo.guardarMesas(mesas);
    }

    /**
     * Asigna o reasigna un mozo a una mesa.
     * @param id El ID de la mesa a modificar.
     * @param mozo El nombre del mozo a asignar.
     */
    public void asignarMozo(int id, String mozo) {
        for (mesa mesa : mesas) {
            if (mesa.getId() == id) {
                // Usa el setter setMozo()
                mesa.setMozo(mozo.isEmpty() ? "—" : mozo);
                break;
            }
        }
        repo.guardarMesas(mesas);
    }
    /**
     * Obtiene los nombres de todas las mesas para poblar el JComboBox de la UI.
     * @return List<String> con los nombres de las mesas.
     */
    public List<String> obtenerNombresDeMesas() {
        return mesas.stream()
                .map(mesa::getNombre) // Usa el getter getNombre() del modelo Mesa
                .collect(java.util.stream.Collectors.toList());
    }
}