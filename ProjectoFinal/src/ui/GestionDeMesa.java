package ui;

import service.MesaService;
import exceptions.MesaNoDisponibleException;
import exceptions.MesaValidator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.mesa;

/**
 * Gestión de Mesas del restaurante.
 * Maneja validaciones mediante excepciones (MesaNoDisponibleException).
 */
public class GestionDeMesa {

    private JPanel contentPane;         // Panel raíz del form
    private JTable tablaRestaurante;    // Tabla de mesas
    private JComboBox<String> comboEstadoMesa; // Combo de estados
    private JButton btnAgregarMesa;
    private JButton btnEliminarMesa;
    private JButton btnAsignarMozo;
    private JButton btnCambiarEstado;
    private JButton btnVolverAlMenu;
    private JTextField txtMesa;
    private JTextField txtMozo;

    private JLabel lblMesa;
    private JLabel lblMozo;
    private JLabel lblEstadoMesa;
    private JLabel lblGestionMesa;

    public GestionDeMesa() {
        setupUI();
    }

    private void setupUI() {
        MesaService service = new MesaService();

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Mozo", "Mesa", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaRestaurante.setModel(model);
        tablaRestaurante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estados posibles
        comboEstadoMesa.removeAllItems();
        comboEstadoMesa.addItem("LIBRE");
        comboEstadoMesa.addItem("OCUPADA");
        comboEstadoMesa.addItem("RESERVADA");

        recargarTabla(service, model);

        // ===================== AGREGAR =====================
        btnAgregarMesa.addActionListener(e -> {
            try {
                String mozo = txtMozo.getText().trim();
                String mesa = txtMesa.getText().trim();
                String estado = (String) comboEstadoMesa.getSelectedItem();
                if (mozo.isEmpty()) mozo = "—";

                MesaValidator.validarNombreMesa(mesa);
                MesaValidator.validarNombreMozo(mozo);

                service.agregar(mozo, mesa, estado);
                recargarTabla(service, model);

                txtMesa.setText("");
                txtMozo.setText("");
            } catch (MesaNoDisponibleException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===================== ELIMINAR =====================
        btnEliminarMesa.addActionListener(e -> {
            try {
                int row = tablaRestaurante.getSelectedRow();
                MesaValidator.validarFilaSeleccionada(row, "eliminar");

                int id = (int) model.getValueAt(row, 0);
                String estadoActual = (String) model.getValueAt(row, 3);

                MesaValidator.validarEstadoMesa(estadoActual, null, "eliminar");

                service.eliminar(id);
                recargarTabla(service, model);
            } catch (MesaNoDisponibleException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===================== CAMBIAR ESTADO =====================
        btnCambiarEstado.addActionListener(e -> {
            try {
                int row = tablaRestaurante.getSelectedRow();
                MesaValidator.validarFilaSeleccionada(row, "cambiar estado");

                int id = (int) model.getValueAt(row, 0);
                String estadoActual = (String) model.getValueAt(row, 3);
                String nuevoEstado = (String) comboEstadoMesa.getSelectedItem();

                MesaValidator.validarEstadoMesa(estadoActual, nuevoEstado, "cambiar estado");

                service.cambiarEstado(id, nuevoEstado);
                recargarTabla(service, model);
            } catch (MesaNoDisponibleException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===================== ASIGNAR MOZO =====================
        btnAsignarMozo.addActionListener(e -> {
            try {
                int row = tablaRestaurante.getSelectedRow();
                MesaValidator.validarFilaSeleccionada(row, "asignar mozo");

                int id = (int) model.getValueAt(row, 0);
                String mozo = txtMozo.getText().trim();

                MesaValidator.validarNombreMozo(mozo);
                String estadoActual = (String) model.getValueAt(row, 3);
                MesaValidator.validarEstadoMesa(estadoActual, null, "asignar mozo");

                service.asignarMozo(id, mozo);
                recargarTabla(service, model);
                txtMozo.setText("");
            } catch (MesaNoDisponibleException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===================== SELECCIÓN DE TABLA =====================
        tablaRestaurante.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaRestaurante.getSelectedRow();
                if (row != -1) {
                    txtMozo.setText((String) tablaRestaurante.getValueAt(row, 1));
                    comboEstadoMesa.setSelectedItem(tablaRestaurante.getValueAt(row, 3));
                }
            }
        });
    }

    private void recargarTabla(MesaService service, DefaultTableModel model) {
        model.setRowCount(0);
        for (mesa m : service.listar()) {
            model.addRow(new Object[]{
                    m.getId(),
                    m.getMozo(),
                    m.getNombre(),
                    m.getEstado()
            });
        }
    }

    public JPanel getContentPane() { return contentPane; }
    public JButton getbtnVolverMenu() { return btnVolverAlMenu; }
}
