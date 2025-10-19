package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReporteBasico {
    private JPanel contentPane;
    private JTable tblReporte;
    private JButton btnVolver;
    private JButton btnExportar;
    private JLabel lblPrecio;

    public ReporteBasico(){
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"VentaID", "Mesa", "Fecha/Hora", "Total", "Método de Pago"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                return switch (col) {
                    case 0 -> Integer.class;  // VentaID
                    case 3 -> Double.class;   // Total
                    default -> String.class;  // Mesa, Fecha, MétodoPago
                };
            }
        };
        tblReporte.setModel(model);
        tblReporte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Texto inicial del total
        setTotalDia(0.0);

        // placeholder de exportar
        btnExportar.addActionListener(e ->
                JOptionPane.showMessageDialog(contentPane, "Exportar aún no implementado."));
    }

    /** Carga filas de ventas en la tabla. */
    public void setVentasData(List<Object[]> filas) {
        var m = (DefaultTableModel) tblReporte.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    /** Muestra el total del día en el label. */
    public void setTotalDia(double total) {
        lblPrecio.setText(String.format("Total del día: $ %.2f", total));
    }

    /** Devuelve el ID de la venta seleccionada (o null si no hay selección). */
    public Integer getVentaSeleccionadaId() {
        int row = tblReporte.getSelectedRow();
        if (row == -1) return null;
        Object v = tblReporte.getModel().getValueAt(row, 0);
        return (v instanceof Integer) ? (Integer) v : Integer.valueOf(v.toString());
    }

    // Getters para el Navegador
    public JPanel getContentPane() { return contentPane; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnExportar() { return btnExportar; }
    public JTable getTblReporte() { return tblReporte; }
    public JLabel getLblPrecio() { return lblPrecio; }
}
