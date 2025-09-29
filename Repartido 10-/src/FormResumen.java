import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormResumen extends JFrame {

    private JTextArea areaResumen;

    public FormResumen(List<DetalleCompra> listaCompra) {
        setTitle("Resumen de Compra");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        areaResumen = new JTextArea(20, 40);
        areaResumen.setEditable(false);

        mostrarResumen(listaCompra);

        JScrollPane scrollPane = new JScrollPane(areaResumen);

        // Contenedor principal
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JLabel("Detalles Finales de la Compra", SwingConstants.CENTER), BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        pack(); // Ajusta el tamaño de la ventana a los componentes
        setLocationRelativeTo(null); // Centrar en pantalla
    }

    private void mostrarResumen(List<DetalleCompra> lista) {
        if (lista.isEmpty()) {
            areaResumen.setText("No hay productos en la compra.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double totalGlobal = 0.0;

        sb.append("--- Detalle de Compra ---\n");
        sb.append(String.format("%-20s %8s %8s %10s\n", "Producto", "Precio", "Cant.", "Subtotal"));
        sb.append("----------------------------------------------------\n");

        for (DetalleCompra d : lista) {
            double subtotal = d.getSubtotal();
            totalGlobal += subtotal;
            sb.append(String.format("%-20s %8.2f %8d %10.2f\n",
                    d.getProducto(), d.getPrecio(), d.getCantidad(), subtotal));
        }

        sb.append("----------------------------------------------------\n");
        sb.append(String.format("%-40s %10.2f\n", "TOTAL FINAL:", totalGlobal));
        sb.append("----------------------------------------------------\n");

        areaResumen.setText(sb.toString());
    }
}