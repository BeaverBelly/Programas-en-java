package repository;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio genérico para persistencia binaria de listas de objetos {@link Serializable}.
 * Permite configurar la carpeta base ya sea en ./data/<carpetaDatos> (modo proyecto)
 * o en un directorio arbitrario del proyecto (constructor con {@link Path}).
 *
 * @param <T> Tipo serializable a persistir (Mesa, Producto, etc.).
 */
public class ArchivoRepository<T extends Serializable> {

    /** Carpeta base donde se guardan los .dat */
    private final Path baseDir;

    /** Crea la carpeta dentro del proyecto (./data/<carpetaDatos>) */
    public ArchivoRepository(String carpetaDatos) {
        this(Paths.get(System.getProperty("user.dir"), "data", carpetaDatos));
    }

    /** Permitir carpeta base arbitraria (por ej. ./data/mesas) */
    public ArchivoRepository(Path baseDir) {
        this.baseDir = baseDir;
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo crear la carpeta de datos: " + baseDir, e);
        }
    }

    /** Guarda una lista en archivo binario */
    public void guardar(List<T> datos, String nombreArchivo) {
        Path file = baseDir.resolve(nombreArchivo);
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
            oos.writeObject(datos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Carga una lista desde archivo binario; si falla o no existe, devuelve lista vacía */
    @SuppressWarnings("unchecked")
    public List<T> cargar(String nombreArchivo) {
        Path file = baseDir.resolve(nombreArchivo);
        if (!Files.exists(file)) return new ArrayList<>();
        try (ObjectInputStream ois =
                     new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
            Object obj = ois.readObject();
            return (obj instanceof List<?> l) ? (List<T>) l : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
