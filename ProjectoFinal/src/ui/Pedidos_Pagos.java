package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import service.MesaService;
import service.ProductoService;
import repository.PedidoRepository;
import model.Pedido;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//Imports añadidos para la validación y excepción reales
import exceptions.ProductoValidator;
import exceptions.StockInsuficienteException;

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
    private JSpinner spCantidad;
    private JButton btnAgregarItem;
    private JButton btnQuitarItem;
    private JTable tblProductos;
    private JTable tblPedido;
    private JLabel lblMetPago;
    private JLabel lblCantidad;
    private JLabel lblCatProd;
    private JLabel lblItemPedido;

    // --- NUEVO: temporizador para actualizar mesas ---
    private Timer timerActualizarMesas;

    // --- NUEVO: mantener referencia al servicio de productos ---
    private final ProductoService productoService = new ProductoService();

    public Pedidos_Pagos() {

        if (tblProductos == null) tblProductos = new JTable();
        if (tblPedido == null) tblPedido = new JTable();
        if (spCantidad == null) spCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        if (cboMetPago == null) cboMetPago = new JComboBox<>();
        if (lblsubTotal == null) lblsubTotal = new JLabel("SubTotal: $0.00");
        if (lblTotal == null) lblTotal = new JLabel("Total: $0.00");
        if (txtBuscar == null) txtBuscar = new JTextField();

        // Configuración tabla Productos
        DefaultTableModel modelProd = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Categoría", "Precio", "Stock", "Activo"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                switch (col) {
                    case 0: case 4: return Integer.class;
                    case 3: return Double.class;
                    case 5: return Boolean.class;
                    default: return String.class;
                }
            }
        };
        tblProductos.setModel(modelProd);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configuración tabla Pedido
        DefaultTableModel modelPedido = new DefaultTableModel(
                new Object[]{"ProdID", "Nombre", "Cant.", "Precio", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                switch (col) {
                    case 0: case 2: return Integer.class;
                    case 3: case 4: return Double.class;
                    default: return String.class;
                }
            }
        };
        tblPedido.setModel(modelPedido);
        tblPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Métodos de pago
        cboMetPago.removeAllItems();
        cboMetPago.addItem("Efectivo");
        cboMetPago.addItem("Débito");
        cboMetPago.addItem("Crédito");
        cboMetPago.addItem("Transferencia");

        cargarDatosIniciales();

        // --- MODIFICADO: Agregar Item con reducción de stock ---
        btnAgregarItem.addActionListener(e -> {
            int fila = tblProductos.getSelectedRow();
            int cantidad = getCantidad();

            if (fila == -1) { showError("Seleccione un producto."); return; }

            try {
                DefaultTableModel prodModel = (DefaultTableModel) tblProductos.getModel();
                int prodId = (Integer) prodModel.getValueAt(fila, 0);
                String nombre = prodModel.getValueAt(fila, 1).toString();
                double precio = (Double) prodModel.getValueAt(fila, 3);
                int stock = (Integer) prodModel.getValueAt(fila, 4);

                ProductoValidator.validarCantidad(cantidad);
                ProductoValidator.validarStock(stock, cantidad, nombre);

                // 🔹 Reducir stock en el archivo productos.dat
                productoService.reducirStock(prodId, cantidad);

                // Agregar al pedido
                double subtotal = precio * cantidad;
                modelPedido.addRow(new Object[]{prodId, nombre, cantidad, precio, subtotal});

                // Actualizar interfaz
                recalcularTotalesUI();
                cargarCarta(); // Refrescar tabla de productos
                spCantidad.setValue(1);
                tblProductos.clearSelection();

            } catch (StockInsuficienteException sie) {
                showError(sie.getMessage());
            } catch (Exception ex) {
                showError("Error al agregar ítem: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Quitar ítem
        btnQuitarItem.addActionListener(e -> {
            int fila = tblPedido.getSelectedRow();
            if (fila == -1) { showError("Seleccione un ítem del pedido."); return; }
            if (confirm("¿Desea quitar el ítem seleccionado?")) {
                modelPedido.removeRow(fila);
                recalcularTotalesUI();
            }
        });

        // Confirmar pedido
        btnConfirmar.addActionListener(e -> {
            if (modelPedido.getRowCount() == 0) { showError("No hay ítems en el pedido."); return; }

            String mesa = (String) cboMesa.getSelectedItem();
            String metodoPago = (String) cboMetPago.getSelectedItem();
            double total = obtenerTotalTabla();

            if (!confirm(String.format("Confirmar pago de $%.2f para mesa %s con método %s?", total, mesa, metodoPago)))
                return;

            Pedido pedido = new Pedido(mesa, total, metodoPago);
            for (int i = 0; i < modelPedido.getRowCount(); i++) {
                String nombre = modelPedido.getValueAt(i, 1).toString();
                int cant = (Integer) modelPedido.getValueAt(i, 2);
                double subtotal = (Double) modelPedido.getValueAt(i, 4);
                pedido.addItem(nombre, cant, subtotal);
            }

            try {
                PedidoRepository repo = new PedidoRepository();
                repo.guardarPedido(pedido);
                showInfo("Pedido confirmado!");
                modelPedido.setRowCount(0);
                recalcularTotalesUI();
                cargarCarta();
                spCantidad.setValue(1);
            } catch (Exception ex) {
                showError("Error al guardar pedido: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        iniciarActualizacionAutomaticaMesas();
    }

    // --- resto igual que antes (cargarDatosIniciales, cargarCarta, etc.) ---
    public void cargarDatosIniciales() {
        try {
            MesaService mesaService = new MesaService();
            cboMesa.removeAllItems();
            List<String> mesas = mesaService.obtenerNombresDeMesas();
            if (mesas != null) for (String m : mesas) cboMesa.addItem(m);
            if (!mesas.isEmpty()) cboMesa.setSelectedIndex(0);
            cargarCarta();
        } catch (Exception e) {
            showError("Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    public void cargarCarta() {
        try {
            List<Object[]> filas = productoService.obtenerProductosParaTabla();
            DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
            model.setRowCount(0);
            if (filas != null) for (Object[] f : filas) model.addRow(f);
        } catch (Exception e) {
            showError("Error al cargar carta: " + e.getMessage());
        }
    }

    private void iniciarActualizacionAutomaticaMesas() {
        if (timerActualizarMesas != null) timerActualizarMesas.cancel();
        timerActualizarMesas = new Timer(true);
        timerActualizarMesas.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                SwingUtilities.invokeLater(() -> actualizarListaDeMesas());
            }
        }, 0, 5000);
    }

    private void actualizarListaDeMesas() {
        try {
            MesaService mesaService = new MesaService();
            List<String> mesasActuales = mesaService.obtenerNombresDeMesas();
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            for (String m : mesasActuales) modelo.addElement(m);
            cboMesa.setModel(modelo);
        } catch (Exception e) {
            System.err.println("⚠ No se pudo actualizar mesas: " + e.getMessage());
        }
    }

    private void recalcularTotalesUI() {
        DefaultTableModel model = (DefaultTableModel) tblPedido.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += (Double) model.getValueAt(i, 4);
        }
        lblsubTotal.setText(String.format("SubTotal: $ %.2f", total));
        lblTotal.setText(String.format("Total: $ %.2f", total));
    }

    private double obtenerTotalTabla() {
        DefaultTableModel model = (DefaultTableModel) tblPedido.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += (Double) model.getValueAt(i, 4);
        }
        return total;
    }

    public int getCantidad() {
        Object val = spCantidad.getValue();
        return (val instanceof Integer) ? (Integer) val : Integer.parseInt(val.toString());
    }

    public void showInfo(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(contentPane, msg, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public JPanel getContentPane() { return contentPane; }
    public JComboBox<String> getCboMesa() { return cboMesa; }
    public JButton getBtnVolver() { return btnVolver; }
}
