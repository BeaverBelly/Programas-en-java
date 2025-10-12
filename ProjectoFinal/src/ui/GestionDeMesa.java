package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla de UI para la <b>Gestión de Mesas</b> del restaurante.
 * <p>
 * Responsabilidades de esta clase (solo capa visual):
 * <ul>
 *   <li>Definir y configurar los componentes de interfaz (tabla, campos, combos, botones).</li>
 *   <li>Proveer un {@link JPanel} raíz mediante {@link #getContentPane()} para ser registrado en el Navegador (CardLayout).</li>
 *   <li>Exponer el botón "Volver" mediante {@link #getbtnVolverMenu()} para que el Main/Navegador pueda navegar a "menu".</li>
 *   <li>Proveer utilidades de la vista (p. ej. {@link #getIdSeleccionado()}) para uso posterior por los services.</li>
 * </ul>
 *
 * <p><b>Qué NO hace:</b> no contiene lógica de negocio ni persistencia. La integración real
 * (crear/eliminar/asignar/cambiar estado) deberá implementarse en un {@code MesaService}
 * y conectarse reemplazando los listeners de ejemplo agregados en el constructor.
 *
 * <p><b>Uso esperado con Navegador (CardLayout):</b>
 * <pre>{@code
 * var nav  = new Navegador("Restó");
 * var menu = new Menu_Principal();
 * var mesa = new GestionDeMesa();
 *
 * nav.registrar("menu", menu.getContentPane());
 * nav.registrar("mesa", mesa.getContentPane());
 * mesa.getbtnVolverMenu().addActionListener(e -> nav.irA("menu"));
 * }}</pre>
 *
 * <p><b>Columnas de la tabla:</b> ID | Mozo | Mesa | Estado (no editables).
 *
 * @author Emanuel
 * @since 1.0
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
    private JButton btnVolverAlMenu;

    /** Campos de entrada. */
    private JTextField txtMesa;
    private JTextField txtMozo;

    /** Etiquetas informativas (solo UI). */
    private JLabel lblMozo;
    private JLabel lblMesa;
    private JLabel lblEstadoMesa;
    private JLabel lblGestionMesa;

    /**
     * Constructor: configura el modelo de la tabla, el combo de estados y
     * listeners de ejemplo (solo visuales) para validar interacción de la UI.
     * <p>
     * IMPORTANTE: Los listeners actuales agregan/modifican datos en el modelo de la tabla
     * con fines demostrativos. Al integrar la lógica real, reemplazar por llamadas a
     * {@code MesaService} y recargar la tabla desde el service (ej.: {@code cargarTabla()}).
     */
    public GestionDeMesa() {
        // Modelo no editable con los encabezados pedidos
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Mozo", "Mesa", "Estado"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaRestaurante.setModel(model);

        // Selección de una sola fila (útil para acciones de editar/eliminar)
        tablaRestaurante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Cargar los estados posibles en el combo (coinciden con el enum EstadoMesa)
        comboEstadoMesa.removeAllItems();
        comboEstadoMesa.addItem("LIBRE");
        comboEstadoMesa.addItem("OCUPADA");
        comboEstadoMesa.addItem("RESERVADA");

        // ----------------- Listeners de demostración (solo UI) -----------------
        // Al integrar Service: reemplazar por service.crear(...), service.eliminar(...), etc.

        btnAgregarMesa.addActionListener(e -> {
            String mozo = txtMozo.getText().trim();
            String mesa = txtMesa.getText().trim();
            String estado = (String) comboEstadoMesa.getSelectedItem();

            if (mesa.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Debe ingresar el nombre de la mesa.");
                return;
            }

            // UI demo: ID "0" temporal. Con Service, venir con ID real/autoincremental.
            model.addRow(new Object[]{0, mozo.isEmpty() ? "—" : mozo, mesa, estado});
            txtMesa.setText("");
            txtMozo.setText("");
        });

        btnEliminarMesa.addActionListener(e -> {
            int row = tablaRestaurante.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para eliminar.");
                return;
            }
            model.removeRow(row);
        });

        btnCambiarEstado.addActionListener(e -> {
            int row = tablaRestaurante.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para cambiar el estado.");
                return;
            }
            String nuevoEstado = (String) comboEstadoMesa.getSelectedItem();
            model.setValueAt(nuevoEstado, row, 3);
        });

        btnAsignarMozo.addActionListener(e -> {
            int row = tablaRestaurante.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para asignar mozo.");
                return;
            }
            String mozo = txtMozo.getText().trim();
            model.setValueAt(mozo.isEmpty() ? "—" : mozo, row, 1);
            txtMozo.setText("");
        });
        // ----------------------------------------------------------------------
    }

    /**
     * Devuelve el ID (columna 0) de la fila actualmente seleccionada en la tabla.
     * <p>
     * Útil para que el Service opere por ID (eliminación, cambios de estado, etc.).
     *
     * @return {@code Integer} con el ID seleccionado, o {@code null} si no hay selección.
     * @throws NumberFormatException si la celda 0 no puede parsearse a Integer.
     */
    private Integer getIdSeleccionado() {
        int row = tablaRestaurante.getSelectedRow();
        if (row == -1) return null;
        Object val = tablaRestaurante.getModel().getValueAt(row, 0);
        return (val instanceof Integer) ? (Integer) val : Integer.valueOf(val.toString());
    }

    /**
     * Panel raíz de la vista. Este panel es el que se registra en el Navegador
     * bajo una clave (por ej. "mesa") para poder navegar con CardLayout.
     *
     * @return panel principal de la pantalla.
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    /**
     * Getter del botón "Volver al menú".
     * <p>
     * El {@code Main} (o quien maneje el Navegador) debe conectar este botón a:
     * {@code nav.irA("menu")}.
     *
     * @return botón que dispara la navegación de retorno al menú.
     */
    public JButton getbtnVolverMenu() {
        // Sugerencia de nombre estándar JavaBean: getBtnVolverAlMenu()
        return btnVolverAlMenu;
    }
}
