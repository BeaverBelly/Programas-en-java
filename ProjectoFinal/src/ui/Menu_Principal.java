package ui;

import javax.swing.*;

/**
 * Pantalla principal del sistema de gestión de restaurante.
 * <p>
 * Esta clase representa el menú inicial de la aplicación, desde donde
 * el usuario puede acceder a las distintas secciones del sistema:
 * <ul>
 *     <li>Gestión de Mesas</li>
 *     <li>Carta / Menú</li>
 *     <li>Pedidos & Pagos</li>
 *     <li>Reporte Básico de Ventas</li>
 * </ul>
 *
 * <p>El menú no contiene lógica interna; simplemente expone los botones
 * para que el {@link util.Navegador} o el {@code Main} configuren las
 * acciones de navegación correspondientes.</p>
 *
 * <h2>Ejemplo de integración:</h2>
 * <pre>{@code
 * var nav  = new Navegador("Restaurante Los Panchos del Guille");
 * var menu = new Menu_Principal();
 * var mesa = new GestionDeMesa(nav);
 *
 * nav.registrar("menu", menu.getContentPane());
 * nav.registrar("mesa", mesa.getContentPane());
 *
 * // Navegación: desde el menú hacia las demás pantallas
 * menu.getBtnGestiondeMesa().addActionListener(e -> nav.irA("mesa"));
 * menu.getBtnCarta().addActionListener(e -> JOptionPane.showMessageDialog(null, "Ir a Carta / Menú"));
 * menu.getBtnPedidoPago().addActionListener(e -> JOptionPane.showMessageDialog(null, "Ir a Pedido & Pago"));
 * menu.getBtnReporteBasico().addActionListener(e -> JOptionPane.showMessageDialog(null, "Ir a Reporte del Día"));
 *
 * nav.mostrar();
 * nav.irA("menu");
 * }</pre>
 *
 * <p><b>Notas:</b></p>
 * <ul>
 *     <li>Los listeners reales de cada botón deben definirse en el {@code Main.java}.</li>
 *     <li>Esta clase solo define la estructura visual del menú principal.</li>
 *     <li>El panel raíz se obtiene mediante {@link #getContentPane()} para registrarlo en el Navegador.</li>
 * </ul>
 *
 * @author Emanuel
 * @version 1.0
 */
public class Menu_Principal {
    /** Panel raíz de la pantalla (registrado en el Navegador). */
    private JPanel contentPane;

    /** Botón para acceder a la gestión de mesas. */
    private JButton btnGestiondeMesa;

    /** Botón para acceder a la sección de pedidos y pagos. */
    private JButton btnPedido_Pago;

    /** Botón para acceder a los reportes básicos de ventas. */
    private JButton btnReporteBasico;

    /** Botón para acceder a la carta o menú del restaurante. */
    private JButton btnCarta;

    /**
     * Devuelve el panel raíz del menú principal.
     * Este panel se registra en el {@link util.Navegador}.
     *
     * @return panel raíz del menú principal.
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    /**
     * @return botón "Gestión de Mesas" (debe vincularse a la pantalla de mesas).
     */
    public JButton getBtnGestiondeMesa() {
        return btnGestiondeMesa;
    }

    /**
     * @return botón "Carta / Menú" (debe vincularse a la pantalla de productos).
     */
    public JButton getBtnCarta() {
        return btnCarta;
    }

    /**
     * @return botón "Pedido & Pago" (debe vincularse a la pantalla de pedidos).
     */
    public JButton getBtnPedidoPago() {
        return btnPedido_Pago;
    }

    /**
     * @return botón "Reporte del Día" (debe vincularse a la pantalla de reportes).
     */
    public JButton getBtnReporteBasico() {
        return btnReporteBasico;
    }
}
