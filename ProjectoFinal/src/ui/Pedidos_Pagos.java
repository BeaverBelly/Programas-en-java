package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

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
    private JTable tblProductos;
    private JTable tblPedido;
    private JLabel lblCatProd;
    private JLabel lblItemPedido;

    public Pedidos_Pagos(){
        DefaultTableModel modelProd = new DefaultTableModel(
                new Object[]{"ID","Nombre","Categoría","Precio","Stock","Activo"}, 0) {
            @Override public boolean isCellEditable(int row,int column){ return false; }
            @Override public Class<?> getColumnClass(int column){
                return switch (column){
                    case 0,4 -> Integer.class;   // ID, Stock
                    case 3   -> Double.class;    // Precio
                    case 5   -> Boolean.class;   // Activo
                    default  -> String.class;    // Nombre, Categoría
                };
            }
        };
        tblProductos.setModel(modelProd);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel modelItems = new DefaultTableModel(
                new Object[]{"ProdID","Nombre","Cant.","Precio","Subtotal"}, 0) {
            @Override public boolean isCellEditable(int row,int column){ return false; }
            @Override public Class<?> getColumnClass(int column){
                return switch (column){
                    case 0,2 -> Integer.class; // ProdID, Cantidad
                    case 3,4 -> Double.class;  // Precio, Subtotal
                    default  -> String.class;  // Nombre
                };
            }
        };
        tblPedido.setModel(modelItems);
        tblPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (spCantidad != null) spCantidad.setModel(new SpinnerNumberModel(1, 1, 1000, 1));

        if (cboMetPago != null) {
            cboMetPago.removeAllItems();
            cboMetPago.addItem("Efectivo");
            cboMetPago.addItem("Débito");
            cboMetPago.addItem("Crédito");
            cboMetPago.addItem("Transferencia");
        }

        // Totales iniciales
        setTotales(0.0, 0.0);

        btnAgregarItem.addActionListener(e -> {
            // TODO: validar selección y cantidad; luego controlador agregará item y refrescará setItemsData(...)
        });
        btnQuitarItem.addActionListener(e -> {
            // TODO: quitar item seleccionado vía service/controlador y refrescar setItemsData(...)
        });
        btnConfirmar.addActionListener(e -> {
            // TODO: confirmar pago vía service; limpiar carrito si corresponde
        });
    }
    // ===================== Helpers (contrato para el controlador) =====================

    // Cargar combos/listas desde la lógica
    public void setMesas(List<String> nombresMesas) {
        cboMesa.removeAllItems();
        if (nombresMesas != null) for (String m : nombresMesas) cboMesa.addItem(m);
    }

    public void setProductosData(List<Object[]> filas) {
        var m = (DefaultTableModel) tblProductos.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    public void setItemsData(List<Object[]> filas) {
        var m = (DefaultTableModel) tblPedido.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    public void setTotales(double subTotal, double total) {
        lblsubTotal.setText(String.format("SubTotal: $ %.2f", subTotal));
        lblTotal.setText(String.format("Total: $ %.2f", total));
    }

    // Lecturas/selecciones
    public Integer getProductoSeleccionadoId() {
        int r = tblProductos.getSelectedRow();
        if (r == -1) return null;
        Object v = tblProductos.getModel().getValueAt(r, 0);
        return (v instanceof Integer)? (Integer) v : Integer.valueOf(v.toString());
    }

    public Integer getItemSeleccionadoProdId() {
        int r = tblPedido.getSelectedRow();
        if (r == -1) return null;
        Object v = tblPedido.getModel().getValueAt(r, 0);
        return (v instanceof Integer)? (Integer) v : Integer.valueOf(v.toString());
    }

    public int getCantidad() {
        Object val = spCantidad.getValue();
        return (val instanceof Integer) ? (Integer) val : Integer.parseInt(val.toString());
    }

    public String getMesaSeleccionada() { return (String) cboMesa.getSelectedItem(); }
    public String getMetodoPagoSeleccionado() { return (String) cboMetPago.getSelectedItem(); }

    // Mensajería simple (para no acceder a Swing desde el controlador)
    public void showInfo(String msg)  { JOptionPane.showMessageDialog(contentPane, msg, "Info", JOptionPane.INFORMATION_MESSAGE); }
    public void showError(String msg) { JOptionPane.showMessageDialog(contentPane, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    public boolean confirm(String msg){ return JOptionPane.showConfirmDialog(contentPane, msg, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION; }





    public JPanel getContentPane() { return contentPane; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnConfirmar() { return btnConfirmar; }
    public JButton getBtnAgregarItem() { return btnAgregarItem; }
    public JButton getBtnQuitarItem() { return btnQuitarItem; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTable getTblProductos() { return tblProductos; }
    public JTable getTblPedido() { return tblPedido; }
    public JComboBox<String> getCboMesa() { return cboMesa; }
    public JComboBox<String> getCboMetPago() { return cboMetPago; }
    public JSpinner getSpCantidad() { return spCantidad; }
    public JLabel getLblSubTotal() { return lblsubTotal; }
    public JLabel getLblTotal() { return lblTotal; }
    public JTextField getTxtBuscar() { return txtBuscar; }
}


