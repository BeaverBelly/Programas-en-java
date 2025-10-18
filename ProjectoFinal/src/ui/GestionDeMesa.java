package ui;

import service.MesaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla de UI para la <b>Gestión de Mesas</b> del restaurante.
 * <p>
 * Responsabilidades de esta clase (solo capa visual):
 * <ul>
 *   <li>Definir y configurar los componentes de interfaz (tabla, campos, combos, botones).</li>
 *   <li>Proveer un {@link JPanel} raíz mediante {@link #getContentPane()} para ser registrado en el Navegador (CardLayout).</li>
 *   <li>Exponer el botón "Volver" mediante {@link #getbtnVolverMenu()} para que el Main/Navegador pueda navegar al menú principal.</li>
 *   <li>Comunicarse con {@link MesaService} para gestionar las operaciones y persistir datos en el archivo <code>mesas.dat</code>.</li>
 * </ul>
 *
 * <p><b>Qué NO hace:</b> no contiene lógica de negocio ni persistencia directa.
 * Todo el manejo de archivos se realiza en el Service/Repository.</p>
 *
 * <p><b>Columnas de la tabla:</b> ID | Mozo | Mesa | Estado (no editables).</p>
 *
 * <p><b>Archivo asociado:</b> <code>~/.resto/mesas/mesas.dat</code></p>
 *
 * <p><b>Uso esperado con Navegador (CardLayout):</b>
 * <pre>{@code
 * var nav  = new Navegador("Restó POS");
 * var menu = new Menu_Principal();
 * var mesa = new GestionDeMesa();
 *
 * nav.registrar("menu", menu.getContentPane());
 * nav.registrar("mesa", mesa.getContentPane());
 * mesa.getbtnVolverMenu().addActionListener(e -> nav.irA("menu"));
 * }}</pre>
 *
 * @author Emanuel
 * @version 1.1
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
     * los listeners conectados al {@link MesaService}.
     */
    public GestionDeMesa() {
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

        // ➕ Agregar mesa
        btnAgregarMesa.addActionListener(e -> {
            String mozo = txtMozo.getText().trim();
            String mesa = txtMesa.getText().trim();
            String estado = (String) comboEstadoMesa.getSelectedItem();

            if (mesa.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Debe ingresar el nombre de la mesa.");
                return;
            }

            service.agregar(mozo, mesa, estado);
            recargarTabla(service, model);

            txtMesa.setText("");
            txtMozo.setText("");
        });

        // ❌ Eliminar mesa
        btnEliminarMesa.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para eliminar.");
                return;
            }
            service.eliminar(id);
            recargarTabla(service, model);
        });

        // 🔄 Cambiar estado de mesa
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

        // 👨‍🍳 Asignar mozo
        btnAsignarMozo.addActionListener(e -> {
            Integer id = getIdSeleccionado();
            if (id == null) {
                JOptionPane.showMessageDialog(contentPane, "Seleccione una mesa para asignar mozo.");
                return;
            }
            String mozo = txtMozo.getText().trim();
            service.asignarMozo(id, mozo.isEmpty() ? "—" : mozo);
            txtMozo.setText("");
            recargarTabla(service, model);
        });
    }

    // =========================================================================
    // MÉTODOS AUXILIARES DE UI
    // =========================================================================

    /**
     * Recarga la tabla con los datos actualizados del {@link MesaService}.
     *
     * @param service servicio de mesas desde el cual se obtienen los datos.
     * @param model modelo de la tabla a refrescar.
     */
    private void recargarTabla(MesaService service, DefaultTableModel model) {
        model.setRowCount(0);
        for (Object[] fila : service.listar()) {
            model.addRow(fila);
        }
    }

    /**
     * Devuelve el ID (columna 0) de la fila actualmente seleccionada en la tabla.
     *
     * @return {@code Integer} con el ID seleccionado, o {@code null} si no hay selección.
     */
    private Integer getIdSeleccionado() {
        int row = tablaRestaurante.getSelectedRow();
        if (row == -1) return null;
        Object val = tablaRestaurante.getModel().getValueAt(row, 0);
        return (Integer) val;
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
        return btnVolverAlMenu;
    }
}
