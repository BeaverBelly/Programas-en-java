package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import service.MesaService;
import service.ProductoService;
import repository.PedidoRepository;
import Model.Pedido;

public class Pedidos_Pagos {
    private JPanel contentPane;
    private JComboBox<String> cboMesa;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnConfirmar;
    private JButton btnVolver;
    private JComboBox<String> cboMetPago;
    private JLabel lblsubTotal;
    private JLabel lblTotal;
    private JLabel lblMetPago;
    private JSpinner spCantidad;
    private JButton btnAgregarItem;
    private JButton btnQuitarItem;
    private JLabel lblCantidad;
    private JTable tblProductos; // Tabla de Catálogo/Carta
    private JTable tblPedido;    // Tabla de Items del Pedido
    private JLabel lblCatProd;
    private JLabel lblItemPedido;

    public Pedidos_Pagos() {

        if (tblProductos == null) { this.tblProductos = new JTable(); }
        if (tblPedido == null) { this.tblPedido = new JTable(); }
        if (spCantidad == null) { this.spCantidad = new JSpinner(); }
        if (cboMetPago == null) { this.cboMetPago = new JComboBox<>(); }
        if (lblsubTotal == null) { this.lblsubTotal = new JLabel(); }
        if (lblTotal == null) { this.lblTotal = new JLabel(); }
        if (txtBuscar == null) { this.txtBuscar = new JTextField(); }


        // Tabla de Productos (Catálogo)
        DefaultTableModel modelProd = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Categoría", "Precio", "Stock", "Activo"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
            @Override public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0: case 4: return Integer.class;
                    case 3: return Double.class;
                    case 5: return Boolean.class;
                    default: return String.class;
                }
            }
        };
        tblProductos.setModel(modelProd);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Tabla de Items del Pedido
        DefaultTableModel modelItems = new DefaultTableModel(
                new Object[]{"ProdID", "Nombre", "Cant.", "Precio", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
            @Override public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0: case 2: return Integer.class;
                    case 3: case 4: return Double.class;
                    default: return String.class;
                }
            }
        };
        tblPedido.setModel(modelItems);
        tblPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Inicialización de Spinner y ComboBox
        spCantidad.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        cboMetPago.removeAllItems();
        cboMetPago.addItem("Efectivo");
        cboMetPago.addItem("Débito");
        cboMetPago.addItem("Crédito");
        cboMetPago.addItem("Transferencia");

        setTotales(0.0, 0.0);


        // Llama a cargarDatosIniciales (que a su vez llama a cargarCarta)
        cargarDatosIniciales();

        // Lógica de AGREGAR ITEM
        btnAgregarItem.addActionListener(e -> {
            int filaSeleccionada = tblProductos.getSelectedRow();
            int cantidad = getCantidad();

            if (filaSeleccionada == -1) { showError("Seleccione un producto de la carta para agregar."); return; }
            if (cantidad <= 0) { showError("La cantidad debe ser mayor a cero."); return; }

            try {
                var modelProdCheck = tblProductos.getModel();
                Integer prodId = (Integer) modelProdCheck.getValueAt(filaSeleccionada, 0);
                String nombre = modelProdCheck.getValueAt(filaSeleccionada, 1).toString();
                Double precioUnitario = (Double) modelProdCheck.getValueAt(filaSeleccionada, 3);
                Integer stockActual = (Integer) modelProdCheck.getValueAt(filaSeleccionada, 4);

                if (cantidad > stockActual) {
                    showError("No hay suficiente stock disponible (" + stockActual + ").");
                    return;
                }

                double subtotal = precioUnitario * cantidad;

                var modelPedido = (DefaultTableModel) tblPedido.getModel();
                Object[] nuevoItem = new Object[]{prodId, nombre, cantidad, precioUnitario, subtotal};
                modelPedido.addRow(nuevoItem);

                recalcularTotalesUI();
                spCantidad.setValue(1);
                tblProductos.clearSelection();

            } catch (Exception ex) {
                showError("Error al agregar el ítem. Detalle: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Lógica de QUITAR ITEM
        btnQuitarItem.addActionListener(e -> {
            int filaSeleccionada = tblPedido.getSelectedRow();
            if (filaSeleccionada == -1) { showError("Seleccione un ítem del pedido para quitar."); return; }

            if (confirm("¿Está seguro de quitar el ítem seleccionado del pedido?")) {
                var modelPedido = (DefaultTableModel) tblPedido.getModel();
                modelPedido.removeRow(filaSeleccionada);
                recalcularTotalesUI();
            }
        });

        // Lógica de CONFIRMAR PAGO y GUARDAR ARCHIVO
        btnConfirmar.addActionListener(e -> {
            var modelPedido = (DefaultTableModel) tblPedido.getModel();
            if (modelPedido.getRowCount() == 0) { showError("No hay ítems en el pedido para confirmar."); return; }

            String mesaSeleccionada = (String) cboMesa.getSelectedItem();
            double total = obtenerTotalNumerico();
            String metodoPago = (String) cboMetPago.getSelectedItem();

            if (!confirm(String.format("¿Desea confirmar el pago de $%.2f para la mesa '%s' con método '%s'?", total, mesaSeleccionada, metodoPago))) {
                return;
            }

            Pedido nuevoPedido = new Pedido(mesaSeleccionada, total, metodoPago);

            for (int i = 0; i < modelPedido.getRowCount(); i++) {
                try {
                    String nombre = modelPedido.getValueAt(i, 1).toString();
                    int cantidad = (Integer) modelPedido.getValueAt(i, 2);
                    double subtotalItem = (Double) modelPedido.getValueAt(i, 4);
                    nuevoPedido.addItem(nombre, cantidad, subtotalItem);
                } catch (Exception ex) {
                    showError("Error al procesar el ítem " + (i + 1) + ". " + ex.getMessage());
                    return;
                }
            }

            try {
                PedidoRepository repo = new PedidoRepository();
                repo.guardarPedido(nuevoPedido);

                showInfo("Pedido guardado y pago confirmado con éxito!");

                // Limpiar UI
                modelPedido.setRowCount(0);
                recalcularTotalesUI();
                tblProductos.clearSelection();
                spCantidad.setValue(1);

            } catch (Exception ex) {
                showError("Error al guardar el archivo del pedido. Detalle: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Lógica de LIMPIAR/RECARGAR CARTA (Mantenida como opción manual si se necesita)
        if (btnLimpiar != null) {
            btnLimpiar.addActionListener(e -> {
                txtBuscar.setText("");
                cargarCarta();
                showInfo("Carta recargada con éxito de forma manual.");
            });
        }

    }


    /**
     * Carga las mesas y hace la primera llamada para cargar la carta.
     */
    public void cargarDatosIniciales() {
        try {
            MesaService mesaService = new MesaService();
            setMesas(mesaService.obtenerNombresDeMesas());
            cargarCarta();

        } catch (Exception e) {
            showError("Error al cargar datos iniciales. Detalle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Recarga únicamente la tabla de productos (la Carta) leyendo el archivo de persistencia.
     * ESTE METODO ES LLAMADO DESDE Main.java
     */
    public void cargarCarta() {
        try {
            ProductoService productoService = new ProductoService();
            List<Object[]> filasProductos = productoService.obtenerProductosParaTabla();
            setProductosData(filasProductos);
        } catch (Exception e) {
            showError("Error al recargar la Carta (Productos). Detalle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void recalcularTotalesUI() {
        var model = (DefaultTableModel) tblPedido.getModel();
        double nuevoTotal = 0.0;
        final int COL_SUBTOTAL = 4;

        for (int i = 0; i < model.getRowCount(); i++) {
            nuevoTotal += (Double) model.getValueAt(i, COL_SUBTOTAL);
        }
        setTotales(nuevoTotal, nuevoTotal);
    }

    private double obtenerTotalNumerico() {
        String totalText = lblTotal.getText().replace("Total: $", "").replace(",", ".");
        try {
            return Double.parseDouble(totalText.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setMesas(List<String> nombresMesas) {
        cboMesa.removeAllItems();
        if (nombresMesas != null) {
            for (String m : nombresMesas) cboMesa.addItem(m);
            if (!nombresMesas.isEmpty()) cboMesa.setSelectedIndex(0);
        }
    }

    public void setProductosData(List<Object[]> filas) {
        var m = (DefaultTableModel) tblProductos.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    public void setTotales(double subTotal, double total) {
        lblsubTotal.setText(String.format("SubTotal: $ %.2f", subTotal));
        lblTotal.setText(String.format("Total: $ %.2f", total));
    }

    public int getCantidad() {
        Object val = spCantidad.getValue();
        return (val instanceof Integer) ? (Integer) val : Integer.parseInt(val.toString());
    }

    public void showInfo(String msg) { JOptionPane.showMessageDialog(contentPane, msg, "Info", JOptionPane.INFORMATION_MESSAGE); }
    public void showError(String msg) { JOptionPane.showMessageDialog(contentPane, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    public boolean confirm(String msg) { return JOptionPane.showConfirmDialog(contentPane, msg, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION; }


    public JPanel getContentPane() {
        return contentPane;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JTable getTblPedido() {
        return tblPedido;
    }

    public JButton getBtnAgregarItem() {
        return btnAgregarItem;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnQuitarItem() {
        return btnQuitarItem;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JComboBox<String> getCboMesa() {
        return cboMesa;
    }

    public JComboBox<String> getCboMetPago() {
        return cboMetPago;
    }

    public JSpinner getSpCantidad() {
        return spCantidad;
    }

    public JLabel getLblSubTotal() {
        return lblsubTotal;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }
}