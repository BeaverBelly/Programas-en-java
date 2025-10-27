package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import service.ReporteService;

public class ReporteBasico {
    private JPanel contentPane;
    private JTable tblReporte;
    private JButton btnVolver;
    private JButton btnExportar;
    private JLabel lblPrecio; // Muestra el Total del día

    public ReporteBasico(){

        if (tblReporte == null) { this.tblReporte = new JTable(); }
        if (lblPrecio == null) { this.lblPrecio = new JLabel("Total del día: $ 0.00"); }

        // Inicialización de Modelo
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"VentaID", "Mesa", "Fecha/Hora", "Total", "Método de Pago"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                switch (col) {
                    case 0: return Integer.class;  // VentaID
                    case 3: return Double.class;   // Total
                    default: return String.class;  // Mesa, Fecha, MétodoPago
                }
            }
        };
        tblReporte.setModel(model);
        tblReporte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try {
            ReporteService service = new ReporteService();

            List<Object[]> filasVentas = service.obtenerVentasParaTabla();
            setVentasData(filasVentas);

            double totalDia = service.calcularTotalVentasDia();
            setTotalDia(totalDia);

            if (filasVentas.isEmpty()) {
                showInfo("No se encontraron pedidos guardados.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error grave al cargar el reporte de ventas: " + e.getMessage(), "Error de Reporte", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            setTotalDia(0.0);
        }

        // Action Listeners
        if (btnExportar != null) {
            btnExportar.addActionListener(e ->
                    JOptionPane.showMessageDialog(contentPane, "Exportar aún no implementado."));
        }
        // ... (Listener para btnVolver) ...
    }

   
    public void setVentasData(List<Object[]> filas) {
        var m = (DefaultTableModel) tblReporte.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    public void setTotalDia(double total) {
        if (lblPrecio != null) {
            lblPrecio.setText(String.format("Total del día: $ %.2f", total));
        }
    }

    public void showInfo(String msg)  { JOptionPane.showMessageDialog(contentPane, msg, "Info", JOptionPane.INFORMATION_MESSAGE); }

    public Integer getVentaSeleccionadaId() {
        int row = tblReporte.getSelectedRow();
        if (row == -1) return null;
        Object v = tblReporte.getModel().getValueAt(row, 0);
        return (v instanceof Integer) ? (Integer) v : Integer.valueOf(v.toString());
    }

    // Getters
    public JPanel getContentPane() { return contentPane; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnExportar() { return btnExportar; }
    public JTable getTblReporte() { return tblReporte; }
    public JLabel getLblPrecio() { return lblPrecio; }
}