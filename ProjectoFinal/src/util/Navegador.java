package util;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria que gestiona la navegación entre pantallas (vistas)
 * dentro de una única ventana principal de la aplicación.
 *
 * <p>Implementa el patrón de diseño <b>CardLayout</b>, permitiendo cambiar
 * entre distintos paneles ({@link JPanel}) sin necesidad de abrir nuevas ventanas.</p>
 *
 * <p>Su objetivo es centralizar el control de las vistas, mantener
 * una sola instancia del {@link JFrame} principal y facilitar la transición
 * fluida entre pantallas como el menú, gestión de mesas, carta, pedidos, etc.</p>
 *
 * <h2>Ejemplo de uso:</h2>
 * <pre>{@code
 * // Crear el navegador principal
 * Navegador nav = new Navegador("Restaurante Los Panchos del Guille");
 *
 * // Crear las vistas
 * Menu_Principal menu = new Menu_Principal();
 * GestionDeMesa mesas = new GestionDeMesa(nav);
 *
 * // Registrar las vistas con nombres únicos
 * nav.registrar("menu", menu.getContentPane());
 * nav.registrar("mesa", mesas.getContentPane());
 *
 * // Configurar la navegación desde el menú
 * menu.getBtnGestiondeMesa().addActionListener(e -> nav.irA("mesa"));
 *
 * // Mostrar la aplicación
 * nav.mostrar();
 * nav.irA("menu");
 * }</pre>
 *
 * <p><b>Ventajas de usar Navegador:</b></p>
 * <ul>
 *   <li>Evita abrir múltiples ventanas o JFrames.</li>
 *   <li>Permite mantener una interfaz consistente y fluida.</li>
 *   <li>Facilita la separación entre capas (UI / Lógica / Servicios).</li>
 * </ul>
 *
 * @author Emanuel
 * @version 1.0
 */
public class Navegador {

    /** Ventana principal de la aplicación. */
    private final JFrame frame;

    /** Panel raíz que contiene todas las vistas. */
    private final JPanel root;

    /** Layout utilizado para intercambiar vistas. */
    private final CardLayout cards;

    /** Diccionario que asocia nombres únicos con paneles registrados. */
    private final Map<String, JPanel> vistas = new HashMap<>();

    /**
     * Constructor del Navegador.
     *
     * @param titulo Título de la ventana principal (se muestra en la barra del JFrame).
     */
    public Navegador(String titulo) {
        frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null); // Centra la ventana en pantalla

        cards = new CardLayout();
        root = new JPanel(cards);
        frame.setContentPane(root);
    }

    /**
     * Registra una nueva vista dentro del navegador con un nombre único.
     *
     * @param nombre Identificador textual de la vista (por ejemplo, "menu", "mesa", "carta").
     * @param vista  Panel ({@link JPanel}) que representa la vista a registrar.
     */
    public void registrar(String nombre, JPanel vista) {
        vistas.put(nombre, vista);
        root.add(vista, nombre);
    }

    /**
     * Cambia la vista actualmente visible por la registrada con el nombre especificado.
     *
     * @param nombre Nombre de la vista registrada a mostrar.
     * @throws IllegalArgumentException Si el nombre no corresponde a ninguna vista registrada.
     */
    public void irA(String nombre) {
        if (!vistas.containsKey(nombre)) {
            throw new IllegalArgumentException("Vista no registrada: " + nombre);
        }
        cards.show(root, nombre);
        root.revalidate();
        root.repaint();
    }

    /**
     * Muestra la ventana principal del programa.
     * Debe llamarse una vez que todas las vistas estén registradas.
     */
    public void mostrar() {
        frame.setVisible(true);
    }

    /**
     * Devuelve la instancia del {@link JFrame} principal.
     * <p>Se puede usar para mostrar diálogos modales o configuraciones
     * avanzadas sobre la misma ventana principal.</p>
     *
     * @return JFrame principal de la aplicación.
     */
    public JFrame getFrame() {
        return frame;
    }
}
