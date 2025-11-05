package repository;

import model.Pedido;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class PedidoRepository extends ArchivoRepository<Pedido> {

    private static final String ARCHIVO_PEDIDOS = "pedidos_diarios.dat";

    private static final Path PEDIDOS_DIR =
            Paths.get(System.getProperty("user.dir"), "data", "pedidos");

    public PedidoRepository() {
        super(PEDIDOS_DIR);
    }

    /**
     * Carga todos los pedidos existentes, agrega el nuevo pedido y vuelve a guardar la lista completa.
     */
    public void guardarPedido(Pedido pedido) {
        List<Pedido> todosLosPedidos = cargarTodosLosPedidos();
        todosLosPedidos.add(pedido);

        System.out.println("DEBUG: Guardando " + todosLosPedidos.size() + " pedidos en el archivo único: " + ARCHIVO_PEDIDOS);
        guardar(todosLosPedidos, ARCHIVO_PEDIDOS); // Método de ArchivoRepository
    }

    /**
     * Carga la lista completa de pedidos desde el archivo único.
     */
    public List<Pedido> cargarTodosLosPedidos() {
        // Usa el metodo 'cargar' de la clase base, que devuelve una lista vacía si falla.
        System.out.println("DEBUG: Cargando pedidos desde el archivo único: " + ARCHIVO_PEDIDOS);
        List<Pedido> pedidosCargados = cargar(ARCHIVO_PEDIDOS);
        System.out.println("DEBUG: Total de pedidos cargados: " + pedidosCargados.size());
        return pedidosCargados;
    }
}