package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class Carta {
    private JPanel contentPane;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JTable tableProducto;
    private JTextField txtNombre;
    private JComboBox<String> cboCategoria;
    private JFormattedTextField txtPrecio;
    private JTextField txtStock;
    private JCheckBox chboxActividad;
    private JButton btnEliminar;
    private JButton btnAgregar;
    private JButton btnVolver;
    private JButton btnModificar;
    private JTextField txtBuscador;
    private JLabel lblNombre;
    private JLabel lblCategoria;
    private JLabel lblPrecio;
    private JLabel lblStock;

    public Carta(){
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Categoría", "Precio", "Stock", "Activo"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                return switch (c) {
                    case 0, 4 -> Integer.class; // ID, Stock
                    case 3     -> Double.class;  // Precio
                    case 5     -> Boolean.class; // Activo
                    default    -> String.class;  // Nombre, Categoría
                };
            }
        };
        tableProducto.setModel(model);
        tableProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (cboCategoria != null) {
            cboCategoria.removeAllItems();
            cboCategoria.addItem("COMIDA");
            cboCategoria.addItem("BEBIDA");
            cboCategoria.addItem("POSTRE");
        }

        NumberFormatter currency = new NumberFormatter(NumberFormat.getNumberInstance());
        currency.setAllowsInvalid(false);
        currency.setMinimum(0.0);
        txtPrecio.setFormatterFactory(new DefaultFormatterFactory(currency));

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String precioTxt = txtPrecio.getText().trim();
            String stockTxt = txtStock.getText().trim();

            // Validaciones simples de UI
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane,
                        "Debe ingresar un nombre de producto.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (precioTxt.isEmpty() || !precioTxt.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(contentPane,
                        "Debe ingresar un precio válido (número).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (stockTxt.isEmpty() || !stockTxt.matches("\\d+")) {
                JOptionPane.showMessageDialog(contentPane,
                        "Debe ingresar un stock válido (entero).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si to-do está correcto, continuar
            JOptionPane.showMessageDialog(contentPane,
                    "Producto válido, se puede enviar al servicio.",
                    "Validación exitosa", JOptionPane.INFORMATION_MESSAGE);
        });

    }

    public void setTableData(java.util.List<Object[]> filas) {
        var m = (DefaultTableModel) tableProducto.getModel();
        m.setRowCount(0);
        if (filas != null) for (Object[] f : filas) m.addRow(f);
    }

    public Integer getIdSeleccionado() {
        int row = tableProducto.getSelectedRow();
        if (row == -1) return null;
        Object v = tableProducto.getModel().getValueAt(row, 0);
        return (v instanceof Integer) ? (Integer) v : Integer.valueOf(v.toString());
    }

    public void clearForm() {
        txtNombre.setText("");
        txtPrecio.setValue("");
        txtStock.setText("");
        cboCategoria.setSelectedIndex(0);
        chboxActividad.setSelected(true);
        txtNombre.requestFocus();
    }

    //Getters
    public JPanel getContentPane() {
        return contentPane;
    }

    public JTable getTableProducto() {
        return tableProducto;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getbtnVolver() {
        return btnVolver;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JTextField getTxtBuscador() {
        return txtBuscador;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JComboBox<String> getCboCategoria() {
        return cboCategoria;
    }

    public JFormattedTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

    public JCheckBox getChboxActividad() {
        return chboxActividad;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JLabel getLblCategoria() {
        return lblCategoria;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public JLabel getLblStock() {
        return lblStock;
    }
}
