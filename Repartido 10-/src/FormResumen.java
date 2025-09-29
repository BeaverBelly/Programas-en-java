import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormResumen extends JFrame {

    private JTable tblResumen;
    private JLabel lblTotal;
    private DefaultTableModel tablaModel;

    public FormResumen(List<DetalleCompra> lista) {
        setTitle("Resumen de Compra");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tblResumen = new JTable();
        tablaModel = new DefaultTableModel();
        tablaModel.addColumn("Producto");
        tablaModel.addColumn("Precio");
        tablaModel.addColumn("Cantidad");
        tablaModel.addColumn("Subtotal");
        tblResumen.setModel(tablaModel);

        lblTotal = new JLabel("Total: $0.00");

        JScrollPane scroll = new JScrollPane(tblResumen);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(scroll);
        getContentPane().add(lblTotal);

        // Llenar tabla
        double total = 0.0;
        for (DetalleCompra d : lista) {
            tablaModel.addRow(new Object[]{
                    d.getProducto(),
                    d.getPrecio(),
                    d.getCantidad(),
                    d.getSubtotal()
            });
            total += d.getSubtotal();
        }
        lblTotal.setText(String.format("Total: $%.2f", total));

        setVisible(true);
    }
}
