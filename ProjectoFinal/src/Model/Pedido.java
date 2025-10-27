package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombreMesa;
    private LocalDateTime fechaHora;
    private List<ItemPedido> items;
    private double total;
    private String metodoPago;

    public Pedido(String nombreMesa, double total, String metodoPago) {
        this.nombreMesa = nombreMesa;
        this.fechaHora = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.total = total;
        this.metodoPago = metodoPago;
    }

    public void addItem(String nombreProducto, int cantidad, double subtotalItem) {
        this.items.add(new ItemPedido(nombreProducto, cantidad, subtotalItem));
    }

    // GETTERS para Reporte
    public String getNombreMesa() {
        return nombreMesa;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    // CLASE INTERNA: ItemPedido (necesita Serializable)
    private static class ItemPedido implements Serializable {
        private String nombreProducto;
        private int cantidad;
        private double subtotalItem;

        public ItemPedido(String nombreProducto, int cantidad, double subtotalItem) {
            this.nombreProducto = nombreProducto;
            this.cantidad = cantidad;
            this.subtotalItem = subtotalItem;
        }

        @Override
        public String toString() {
            return String.format("%s (x%d) -> $%.2f", nombreProducto, cantidad, subtotalItem);
        }
    }

    // ... (Método toString para debug) ...
}