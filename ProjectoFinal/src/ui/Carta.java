package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.List;
import model.Producto;
import service.ProductoService;

/**
 * Pantalla de UI para la Gestión de Productos (Carta) del restaurante.
 * Completa con lógica de UI, servicio, y todos los Getters necesarios.
 */
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

    // Declaración de Labels (asegurada)
    private JLabel lblNombre;
    private JLabel lblCategoria;
    private JLabel lblPrecio;
    private JLabel lblStock;


    private ProductoService service;
    private DefaultTableModel tableModel;


    public Carta(){
        // Inicialización del Servicio
        this.service = new ProductoService();

        // Configuración del Modelo de Tabla
        this.tableModel = new DefaultTableModel(
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
        tableProducto.setModel(tableModel);
        tableProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configuración del Combo
        if (cboCategoria != null) {
            cboCategoria.removeAllItems();
            cboCategoria.addItem("COMIDA");
            cboCategoria.addItem("BEBIDA");
            cboCategoria.addItem("POSTRE");
            cboCategoria.setSelectedIndex(0);
        }

        // Configuración del Formato de Precio
        NumberFormatter currency = new NumberFormatter(NumberFormat.getNumberInstance());
        currency.setAllowsInvalid(false);
        currency.setMinimum(0.0);
        txtPrecio.setFormatterFactory(new DefaultFormatterFactory(currency));

        // Cargar datos iniciales
        recargarTabla();
        actualizarDesdeArchivo();

        tableProducto.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    rellenarFormularioDesdeTabla();
                }
            }
        });

        // Botón Limpiar
        btnLimpiar.addActionListener(e -> {
            clearForm();
            txtBuscador.setText(""); // Limpia el buscador
            recargarTabla();         // Vuelve a mostrar todos los productos
        });


        // Botón Agregar
        btnAgregar.addActionListener(e -> {
            if (!validarFormulario()) return;

            String nombre = txtNombre.getText().trim();
            String categoria = (String) cboCategoria.getSelectedItem();
            double precio = getPrecioFromFormattedField();
            int stock = Integer.parseInt(txtStock.getText().trim());
            boolean activo = chboxActividad.isSelected();

            service.agregar(nombre, categoria, precio, stock, activo);
            recargarTabla();
            clearForm();
        });

        // Botón Modificar
        btnModificar.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!validarFormulario()) return;

            String nombre = txtNombre.getText().trim();
            String categoria = (String) cboCategoria.getSelectedItem();
            double precio = getPrecioFromFormattedField();
            int stock = Integer.parseInt(txtStock.getText().trim());
            boolean activo = chboxActividad.isSelected();

            service.modificar(id, nombre, categoria, precio, stock, activo);
            recargarTabla();
        });

        // Botón Eliminar
        btnEliminar.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(contentPane,
                    "¿Está seguro de eliminar el producto ID " + id + "?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                service.eliminar(id);
                recargarTabla();
                clearForm();
            }
        });

        // Botón Buscar
        btnBuscar.addActionListener(e -> {
            String texto = txtBuscador.getText().trim().toLowerCase();
            recargarTabla(texto);
        });
    }

    /**
     * Recarga la tabla con los datos del ProductoService, opcionalmente filtrando por nombre.
     */
    private void recargarTabla(String filtroNombre) {
        tableModel.setRowCount(0);
        List<Producto> productos = service.listar();

        for (Producto p : productos) {
            if (filtroNombre == null || p.getNombre().toLowerCase().contains(filtroNombre)) {
                // Conversión de Producto a Object[] para la tabla
                Object[] fila = new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getPrecio(),
                        p.getStock(),
                        p.isActivo()
                };
                tableModel.addRow(fila);
            }
        }
    }
    /**
     * Recarga los productos desde el archivo .dat y actualiza la tabla.
     * Se usa cada vez que se entra de nuevo a la pantalla.
     */
    public void actualizarDesdeArchivo() {
        // Vuelve a crear el servicio para forzar lectura desde el archivo
        this.service = new service.ProductoService();
        recargarTabla(); // Usa el método existente
    }

    private void recargarTabla() {
        recargarTabla(null);
    }

    private boolean validarFormulario() {
        String nombre = txtNombre.getText().trim();
        String stockTxt = txtStock.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "Debe ingresar un nombre de producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (getPrecioFromFormattedField() < 0) {
            JOptionPane.showMessageDialog(contentPane, "Debe ingresar un precio válido (número positivo).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (stockTxt.isEmpty() || !stockTxt.matches("\\d+")) {
            JOptionPane.showMessageDialog(contentPane, "Debe ingresar un stock válido (entero).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private double getPrecioFromFormattedField() {
        try {
            Object value = txtPrecio.getValue();
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            NumberFormat format = NumberFormat.getNumberInstance();
            return format.parse(txtPrecio.getText().trim()).doubleValue();
        } catch (Exception e) {
            return -1.0;
        }
    }

    private void rellenarFormularioDesdeTabla() {
        int row = tableProducto.getSelectedRow();
        if (row != -1) {
            txtNombre.setText((String) tableModel.getValueAt(row, 1));
            cboCategoria.setSelectedItem(tableModel.getValueAt(row, 2));

            Double precio = (Double) tableModel.getValueAt(row, 3);
            txtPrecio.setValue(precio);

            txtStock.setText(tableModel.getValueAt(row, 4).toString());
            chboxActividad.setSelected((Boolean) tableModel.getValueAt(row, 5));
        }
    }

    public Integer getIdSeleccionado() {
        int row = tableProducto.getSelectedRow();
        if (row == -1) return null;
        Object v = tableProducto.getModel().getValueAt(row, 0);
        return (v instanceof Integer) ? (Integer) v : Integer.valueOf(v.toString());
    }

    public void clearForm() {
        txtNombre.setText("");
        txtPrecio.setValue(0.0);
        txtStock.setText("");
        cboCategoria.setSelectedIndex(0);
        chboxActividad.setSelected(true);
        tableProducto.clearSelection();
        txtNombre.requestFocus();
    }


    public JPanel getContentPane() { return contentPane; }
    public JTable getTableProducto() { return tableProducto; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getbtnVolver() { return btnVolver; }
    public JButton getBtnModificar() { return btnModificar; }
    public JTextField getTxtBuscador() { return txtBuscador; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JComboBox<String> getCboCategoria() { return cboCategoria; }
    public JFormattedTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtStock() { return txtStock; }
    public JCheckBox getChboxActividad() { return chboxActividad; }

    // Getters de los JLabel (Asegurados)
    public JLabel getLblNombre() { return lblNombre; }
    public JLabel getLblCategoria() { return lblCategoria; }
    public JLabel getLblPrecio() { return lblPrecio; }
    public JLabel getLblStock() { return lblStock; }
}