package repository;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase genérica encargada de la <b>persistencia de datos binarios</b> en el sistema de archivos.
 * <p>
 * Implementa un repositorio base que guarda y carga listas de objetos serializables
 * ({@link Serializable}) utilizando flujos de objetos ({@link ObjectOutputStream}, {@link ObjectInputStream}).
 * </p>
 *
 * <p>Se utiliza como clase auxiliar para los repositorios específicos
 * (por ejemplo, {@code MesaRepository}, {@code ProductoRepository}, etc.),
 * permitiendo centralizar la lógica de acceso a archivos.</p>
 *
 * <h2>Características principales:</h2>
 * <ul>
 *     <li>Crea automáticamente la carpeta de datos dentro del directorio del usuario (por defecto: <code>~/.resto/&lt;carpetaDatos&gt;/</code>).</li>
 *     <li>Permite guardar y recuperar listas completas de objetos.</li>
 *     <li>Maneja errores de E/S devolviendo listas vacías ante fallos.</li>
 * </ul>
 *
 * <h3>Ejemplo de uso:</h3>
 * <pre>{@code
 * // Repositorio de mesas
 * ArchivoRepository<Mesa> repo = new ArchivoRepository<>("mesas");
 *
 * // Guardar datos
 * repo.guardar(listaDeMesas, "mesas.dat");
 *
 * // Cargar datos
 * List<Mesa> mesas = repo.cargar("mesas.dat");
 * }</pre>
 *
 * @param <T> Tipo de dato serializable a persistir (por ejemplo, Mesa, Producto, Pedido, etc.).
 * @author Emanuel
 * @version 1.0
 */
public class ArchivoRepository<T extends Serializable> {

    /** Carpeta base donde se guardarán los archivos .dat (por ejemplo: ~/.resto/mesas/) */
    private final Path baseDir;

    /**
     * Constructor: inicializa el repositorio creando (si no existe)
     * la carpeta correspondiente dentro del directorio del usuario.
     *
     * @param carpetaDatos nombre de la subcarpeta donde se guardarán los archivos de datos
     *                     (por ejemplo, "mesas", "productos", etc.).
     * @throws UncheckedIOException si no se puede crear la carpeta de datos.
     */
    public ArchivoRepository(String carpetaDatos) {
        // Carpeta donde se guardarán los .dat, por ejemplo ~/.resto/mesas/
        this.baseDir = Paths.get(System.getProperty("user.home"), ".resto", carpetaDatos);
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo crear la carpeta de datos: " + baseDir, e);
        }
    }

    /**
     * Guarda una lista de objetos en un archivo binario (.dat) dentro de la carpeta base.
     *
     * @param datos         lista de objetos serializables a guardar.
     * @param nombreArchivo nombre del archivo (por ejemplo, "mesas.dat").
     */
    public void guardar(List<T> datos, String nombreArchivo) {
        Path file = baseDir.resolve(nombreArchivo);
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
            oos.writeObject(datos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga una lista de objetos desde un archivo binario previamente guardado.
     *
     * @param nombreArchivo nombre del archivo a leer (por ejemplo, "mesas.dat").
     * @return una lista con los objetos cargados o una lista vacía si:
     * <ul>
     *   <li>el archivo no existe,</li>
     *   <li>está corrupto,</li>
     *   <li>o se produce algún error de lectura.</li>
     * </ul>
     */
    @SuppressWarnings("unchecked")
    public List<T> cargar(String nombreArchivo) {
        Path file = baseDir.resolve(nombreArchivo);
        if (!Files.exists(file)) {
            return new ArrayList<>(); // Si no existe, devuelve lista vacía
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<T>) obj;
            } else {
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Si hay error (archivo corrupto, etc.), devuelve lista vacía
            return new ArrayList<>();
        }
    }
}
