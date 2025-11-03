package ui;

import service.MesaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.mesa;

/**
 * Pantalla de UI para la <b>Gestión de Mesas</b> del restaurante.
 * Interfaz visual que se comunica con {@link MesaService}.
 */
public class GestionDeMesa {

    private JPanel contentPane;
    private JTable tablaRestaurante;
    private JComboBox comboEstadoMesa;
    private JButton btnAgregarMesa;
    private JButton btnEliminarMesa;
    private JButton btnAsignarMozo;
    private JButton btnCambiarEstado;
    private JButton btnVolverAlMenu;
    private JTextField txtMesa;
    private JTextField txtMozo;
    private JLabel lblMozo;
    private JLabel lblMesa;
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRestaurante.setModel(model);
        tablaRestaurante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estados posibles
        comboEstadoMesa.removeAllItems();
        comboEstadoMesa.addItem("LIBRE");
        comboEstadoMesa.addItem("OCUPADA");
        comboEstadoMesa.addItem("RESERVADA");

        // Carga inicial
        recargarTabla(service, model);

        btnAgregarMesa.addActionListener(e -> {
            String mozo = txtMozo.getText().trim();
            String mesa = txtMesa.getText().trim();
            String estado = (String) comboEstadoMesa.getSelectedItem();
            String mozoFinal = mozo.isEmpty() ? "—" : mozo;

            if (mesa.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Debe ingresar el nombre de la mesa.");
                return;
            }

            service.agregar(mozoFinal, mesa, estado);
            recargarTabla(service, model);

            txtMesa.setText("");
            txtMozo.setText("");
        });

        btnEliminarMesa.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para eliminar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(contentPane,
                    "¿Está seguro de eliminar la mesa ID " + id + "?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                service.eliminar(id);
                recargarTabla(service, model);
            }
        });

        btnCambiarEstado.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para cambiar el estado.");
                return;
            }

            String nuevoEstado = (String) comboEstadoMesa.getSelectedItem();
            int row = tablaRestaurante.getSelectedRow();
            String mozo = (String) tablaRestaurante.getValueAt(row, 1);

            // Validación: no se puede marcar "OCUPADA" si no tiene mozo
            if (nuevoEstado.equalsIgnoreCase("OCUPADA") && (mozo.equals("—") || mozo.trim().isEmpty())) {
                JOptionPane.showMessageDialog(contentPane,
                        "No se puede marcar la mesa como 'OCUPADA' si no tiene un mozo asignado.",
                        "Acción no permitida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            service.cambiarEstado(id, nuevoEstado);
            recargarTabla(service, model);
        });

        btnAsignarMozo.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para asignar mozo.");
                return;
            }

            String mozo = txtMozo.getText().trim();
            String mozoFinal = mozo.isEmpty() ? "—" : mozo;
            int row = tablaRestaurante.getSelectedRow();
            String estadoMesa = (String) tablaRestaurante.getValueAt(row, 3);

            // Validación: solo se puede asignar mozo a mesa LIBRE
            if (!estadoMesa.equalsIgnoreCase("LIBRE")) {
                JOptionPane.showMessageDialog(contentPane,
                        "La mesa no está disponible para asignar mozo.\nEstado actual: " + estadoMesa,
                        "Mesa no disponible",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                service.asignarMozo(id, mozoFinal);
                txtMozo.setText("");
                recargarTabla(service, model);
                JOptionPane.showMessageDialog(contentPane,
                        "Mozo asignado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(contentPane,
                        "Ocurrió un error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Listener para selección de fila
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
        for (mesa mesa : service.listar()) {
            Object[] fila = new Object[]{
                    mesa.getId(),
                    mesa.getMozo(),
                    mesa.getNombre(),
                    mesa.getEstado()
            };
            model.addRow(fila);
        }
    }

    private Integer getIdSeleccionado() {
        int row = tablaRestaurante.getSelectedRow();
        if (row == -1) return null;
        Object val = tablaRestaurante.getModel().getValueAt(row, 0);
        return (Integer) val;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getbtnVolverMenu() {
        return btnVolverAlMenu;
    }
}
