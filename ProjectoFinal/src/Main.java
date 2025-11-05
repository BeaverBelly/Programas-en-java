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

            // Registrar pantallas en el navegador
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

            // === PEDIDOS Y PAGOS ===
            menu.getBtnPedidoPago().addActionListener(e -> {
                pedidosPagos.cargarCarta(); // <--- Recarga productos antes de mostrar
                nav.irA("pedidosPagos");
            });

            // === REPORTE BÁSICO ===
            menu.getBtnReporteBasico().addActionListener(e -> {
                reporteBasico.recargarReporte(); // <--- ACTUALIZA los datos antes de entrar
                nav.irA("reporteBasico");
            });

            // Mostrar interfaz inicial
            nav.mostrar();
            nav.irA("menu");
        });
    }
}
