import javax.swing.*;

import ui.*;
import util.Navegador;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var nav = new Navegador("Restaurante el Guille");
            Menu_Principal menu = new Menu_Principal();
            GestionDeMesa mesa = new GestionDeMesa();
            Carta carta = new Carta();
            Pedidos_Pagos pedidosPagos = new Pedidos_Pagos(); // Instancia de Pedidos_Pagos
            ReporteBasico reporteBasico = new ReporteBasico();

            nav.registrar("menu", menu.getContentPane());

            nav.registrar("mesa", mesa.getContentPane());
            mesa.getbtnVolverMenu().addActionListener(e -> nav.irA("menu"));

            nav.registrar("carta", carta.getContentPane());
            carta.getbtnVolver().addActionListener(e -> nav.irA("menu"));

            nav.registrar("pedidosPagos", pedidosPagos.getContentPane());
            pedidosPagos.getBtnVolver().addActionListener(e -> nav.irA("menu"));

            nav.registrar("reporteBasico", reporteBasico.getContentPane());
            reporteBasico.getBtnVolver().addActionListener(e -> nav.irA("menu"));

            // --- LISTENERS DEL MENÚ PRINCIPAL ---
            menu.getBtnGestiondeMesa().addActionListener(e -> nav.irA("mesa"));
            menu.getBtnCarta().addActionListener(e -> nav.irA("carta"));

            // === MODIFICACIÓN CLAVE AQUÍ ===
            // 1. Al presionar el botón, se llama a cargarCarta() para actualizar la tabla.
            // 2. Luego se navega a la ventana.
            menu.getBtnPedidoPago().addActionListener(e -> {
                pedidosPagos.cargarCarta(); // <--- LLAMADA DE RECARGA
                nav.irA("pedidosPagos");
            });
            // ===================================

            menu.getBtnReporteBasico().addActionListener(e -> nav.irA("reporteBasico"));

            nav.mostrar();
            nav.irA("menu");
        });
    }
}