package ui;

import service.MesaService;
import exceptions.MesaNoDisponibleException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.mesa;

/**
 * Gestión de Mesas del restaurante.
 * Usa MesaNoDisponibleException para manejar todas las validaciones personalizadas.
 */
public class GestionDeMesa {

    // ------------------ COMPONENTES DEL FORM ------------------
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

    // JLabel predefinidos en el .form (mantener nombres exactos)
    private JLabel lblMesa;
    private JLabel lblMozo;
    private JLabel lblEstadoMesa;
    private JLabel lblGestionMesa;

    public GestionDeMesa() {
        setupUI();
    }

    private void setupUI() {
        // SOLO inicializar los componentes que no están en el .form
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

        // ------------------ BOTÓN AGREGAR ------------------
        btnAgregarMesa.addActionListener(e -> {
            String mozo = txtMozo.getText().trim();
            String mesa = txtMesa.getText().trim();
            String estado = (String) comboEstadoMesa.getSelectedItem();
            if (mozo.isEmpty()) mozo = "—";

            if (!MesaNoDisponibleException.validarNombreMesa(mesa) ||
                    !MesaNoDisponibleException.validarNombreMozo(mozo)) return;

            service.agregar(mozo, mesa, estado);
            recargarTabla(service, model);

            txtMesa.setText("");
            txtMozo.setText("");
        });

        // ------------------ BOTÓN ELIMINAR ------------------
        btnEliminarMesa.addActionListener(e -> {
            int row = tablaRestaurante.getSelectedRow();
            if (!MesaNoDisponibleException.validarFilaSeleccionada(row, "eliminar")) return;

            int id = (int) model.getValueAt(row, 0);
            if (!MesaNoDisponibleException.validarEstadoMesa(
                    (String) model.getValueAt(row, 3),
                    null, "eliminar")) return;

            service.eliminar(id);
            recargarTabla(service, model);
        });

        // ------------------ BOTÓN CAMBIAR ESTADO ------------------
        btnCambiarEstado.addActionListener(e -> {
            int row = tablaRestaurante.getSelectedRow();
            if (!MesaNoDisponibleException.validarFilaSeleccionada(row, "cambiar estado")) return;

            int id = (int) model.getValueAt(row, 0);
            String nuevoEstado = (String) comboEstadoMesa.getSelectedItem();

            if (!MesaNoDisponibleException.validarEstadoMesa(
                    (String) model.getValueAt(row, 3),
                    nuevoEstado, "cambiar estado")) return;

            service.cambiarEstado(id, nuevoEstado);
            recargarTabla(service, model);
        });

        // ------------------ BOTÓN ASIGNAR MOZO ------------------
        btnAsignarMozo.addActionListener(e -> {
            int row = tablaRestaurante.getSelectedRow();
            if (!MesaNoDisponibleException.validarFilaSeleccionada(row, "asignar mozo")) return;

            int id = (int) model.getValueAt(row, 0);
            String mozo = txtMozo.getText().trim();
            if (!MesaNoDisponibleException.validarNombreMozo(mozo)) return;

            if (!MesaNoDisponibleException.validarEstadoMesa(
                    (String) model.getValueAt(row, 3), null, "asignar mozo")) return;

            service.asignarMozo(id, mozo);
            recargarTabla(service, model);
            txtMozo.setText("");
        });

        // ------------------ SELECCIÓN DE FILA ------------------
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
