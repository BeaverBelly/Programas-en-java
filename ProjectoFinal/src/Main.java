import javax.swing.*;
import ui.Menu_Principal;
import ui.GestionDeMesa;
import util.Navegador;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var nav = new Navegador("Restaurante LosPanchosDelGuille");
            Menu_Principal menu = new Menu_Principal();
            GestionDeMesa mesa = new GestionDeMesa();


            nav.registrar("menu", menu.getContentPane());

            nav.registrar("mesa", mesa.getContentPane());
            mesa.getbtnVolverMenu().addActionListener(e -> nav.irA("menu"));

            menu.getBtnGestiondeMesa().addActionListener(e -> nav.irA("mesa"));
            menu.getBtnCarta().addActionListener(e ->JOptionPane.showMessageDialog(null, "Ir a Carta / Menú"));
            menu.getBtnPedidoPago().addActionListener(e -> JOptionPane.showMessageDialog(null, "Ir a Pedido & Pago"));
            menu.getBtnReporteBasico().addActionListener(e -> JOptionPane.showMessageDialog(null, "Ir a Reporte del Día"));

            nav.mostrar();
            nav.irA("menu");
        });
    }
}
