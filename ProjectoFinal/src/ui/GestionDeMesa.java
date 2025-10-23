package ui;

import service.MesaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.mesa; // Importamos la clase Mesa

/**
 * Pantalla de UI para la <b>Gestión de Mesas</b> del restaurante.
 * <p>
 * Responsabilidades: Interfaz visual y manejo de eventos.
 * Se comunica con {@link MesaService} para la lógica y datos.
 *
 * <p><b>Archivo asociado:</b> <code>~/.resto/mesas/mesas.dat</code></p>
 *
 * @author Emanuel
 * @version 1.2 (Adaptado a modelo Mesa)
 */
public class GestionDeMesa {

    /** Panel raíz que se agrega al Navegador (CardLayout). */
    private JPanel contentPane;

    /** Tabla principal con el listado de mesas. */
    private JTable tablaRestaurante;

    /** Combo con los estados posibles de la mesa (LIBRE/OCUPADA/RESERVADA). */
    private JComboBox comboEstadoMesa;

    /** Botones de acción de la pantalla. */
    private JButton btnAgregarMesa;
    private JButton btnEliminarMesa;
    private JButton btnAsignarMozo;
    private JButton btnCambiarEstado;
    private JButton btnVolverAlMenu; // <-- Este es el botón que necesita el Main

    /** Campos de entrada. */
    private JTextField txtMesa;
    private JTextField txtMozo;

    /** Etiquetas informativas (solo UI). */
    private JLabel lblMozo;
    private JLabel lblMesa;
    private JLabel lblEstadoMesa;
    private JLabel lblGestionMesa;

    /**
     * Constructor: Configura el Service y llama a setupUI().
     */
    public GestionDeMesa() {
        // La inicialización visual ocurre antes de que el constructor termine.
        setupUI();
    }

    /**
     * Configura el modelo de la tabla, el combo de estados y
     * los listeners, asegurando que los componentes ya están inicializados.
     */
    private void setupUI() {
        // Service: maneja toda la lógica y persistencia
        MesaService service = new MesaService();

        // Modelo de tabla no editable
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Mozo", "Mesa", "Estado"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaRestaurante.setModel(model);

        // Selección de una sola fila (útil para editar o eliminar)
        tablaRestaurante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estados posibles de la mesa
        comboEstadoMesa.removeAllItems();
        comboEstadoMesa.addItem("LIBRE");
        comboEstadoMesa.addItem("OCUPADA");
        comboEstadoMesa.addItem("RESERVADA");

        // Cargar los datos iniciales desde el archivo mesas.dat
        recargarTabla(service, model);

        // -------------------- LISTENERS --------------------

        // Agregar Mesa
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

        // Eliminar Mesa
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

        // Cambiar estado de mesa
        btnCambiarEstado.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para cambiar el estado.");
                return;
            }
            String nuevoEstado = (String) comboEstadoMesa.getSelectedItem();
            service.cambiarEstado(id, nuevoEstado);
            recargarTabla(service, model);
        });

        // Asignar mozo
        btnAsignarMozo.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para asignar mozo.");
                return;
            }
            String mozo = txtMozo.getText().trim();
            String mozoFinal = mozo.isEmpty() ? "—" : mozo;

            service.asignarMozo(id, mozoFinal);
            txtMozo.setText("");
            recargarTabla(service, model);
        });

        // Listener para seleccionar fila y rellenar campos
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

    // =========================================================================
    // MÉTODOS AUXILIARES DE UI
    // =========================================================================

    /**
     * Recarga la tabla con los datos actualizados del {@link MesaService}.
     * Convierte List<Mesa> a Object[] para la tabla.
     */
    private void recargarTabla(MesaService service, DefaultTableModel model) {
        model.setRowCount(0);

        for (mesa mesa : service.listar()) {
            // Convierte Mesa a Object[] para agregar la fila
            Object[] fila = new Object[]{
                    mesa.getId(),
                    mesa.getMozo(),
                    mesa.getNombre(),
                    mesa.getEstado()
            };
            model.addRow(fila);
        }
    }

    /**
     * Devuelve el ID (columna 0) de la fila actualmente seleccionada en la tabla.
     */
    private Integer getIdSeleccionado() {
        int row = tablaRestaurante.getSelectedRow();
        if (row == -1) return null;
        Object val = tablaRestaurante.getModel().getValueAt(row, 0);
        return (Integer) val;
    }

    // =========================================================================
    // MÉTODOS PÚBLICOS NECESARIOS PARA EL MAIN/NAVEGADOR
    // =========================================================================

    /**
     * Panel raíz de la vista. NECESARIO para el Navegador (CardLayout).
     *
     * @return panel principal de la pantalla.
     */
    public JPanel getContentPane() { // <-- MÉTODO QUE FALTABA
        return contentPane;
    }

    /**
     * Getter del botón "Volver al menú". NECESARIO para el Main/Navegador.
     *
     * @return botón que dispara la navegación de retorno al menú.
     */
    public JButton getbtnVolverMenu() { // <-- MÉTODO QUE FALTABA
        return btnVolverAlMenu;
    }
}