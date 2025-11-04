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
    private JLabel lblPrecio;

    public ReporteBasico() {
        setupUI();
        recargarReporte(); // se carga la primera vez
    }

    private void setupUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        tblReporte = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"VentaID", "Mesa", "Fecha/Hora", "Total", "Método de Pago"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                switch (col) {
                    case 0: return Integer.class;
                    case 3: return Double.class;
                    default: return String.class;
                }
            }
        };
        tblReporte.setModel(model);
        JScrollPane scrollPane = new JScrollPane(tblReporte);
        contentPane.add(scrollPane);

        JPanel bottomPanel = new JPanel();
        lblPrecio = new JLabel("Total del día: $ 0.00");
        btnExportar = new JButton("Exportar");
        btnVolver = new JButton("Volver");

        bottomPanel.add(lblPrecio);
        bottomPanel.add(btnExportar);
        bottomPanel.add(btnVolver);
        contentPane.add(bottomPanel);

        btnExportar.addActionListener(e ->
                JOptionPane.showMessageDialog(contentPane, "Exportar aún no implementado."));
    }

    /** Recarga la tabla desde el service */
    public void recargarReporte() {
        try {
            ReporteService service = new ReporteService();
            List<Object[]> filas = service.obtenerVentasParaTabla();
            setVentasData(filas);
            setTotalDia(service.calcularTotalVentasDia());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(contentPane,
                    "Error al cargar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setVentasData(List<Object[]> filas) {
        DefaultTableModel m = (DefaultTableModel) tblReporte.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    public void setTotalDia(double total) {
        lblPrecio.setText(String.format("Total del día: $ %.2f", total));
    }

    // Getters
    public JPanel getContentPane() { return contentPane; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnExportar() { return btnExportar; }
    public JTable getTblReporte() { return tblReporte; }
    public JLabel getLblPrecio() { return lblPrecio; }
}
