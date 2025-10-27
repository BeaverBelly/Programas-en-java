package service;

import Model.Pedido;
import repository.PedidoRepository;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ReporteService {

    private final PedidoRepository pedidoRepository;

    public ReporteService() {
        this.pedidoRepository = new PedidoRepository();
    }

    /**
     * Procesa todos los pedidos guardados y los transforma en filas para la JTable.
     */
    public List<Object[]> obtenerVentasParaTabla() {
        List<Pedido> pedidos = pedidoRepository.cargarTodosLosPedidos();
        List<Object[]> filas = new ArrayList<>();

        int ventaID = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Pedido p : pedidos) {
            try {
                // Fila: {"VentaID", "Mesa", "Fecha/Hora", "Total", "Método de Pago"}
                filas.add(new Object[]{
                        ventaID++,
                        p.getNombreMesa(),
                        p.getFechaHora().format(formatter),
                        p.getTotal(),
                        p.getMetodoPago()
                });
            } catch (Exception e) {
                System.err.println("Error al procesar los datos de un pedido. Omitiendo.");
            }
        }
        return filas;
    }

    /**
     * Suma el total de todas las ventas.
     */
    public double calcularTotalVentasDia() {
        List<Pedido> pedidos = pedidoRepository.cargarTodosLosPedidos();
        return pedidos.stream()
                .mapToDouble(Pedido::getTotal)
                .sum();
    }
}