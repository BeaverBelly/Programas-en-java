import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoCompras {
    private static final String ARCHIVO = "compras.dat";

    public static void guardar(List<DetalleCompra> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("⚠ La lista está vacía, no se guardará nada.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(lista);
            System.out.println(" Compras guardadas correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<DetalleCompra> leer() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            return new ArrayList<>(); // archivo no existe → lista vacía
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<DetalleCompra>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
