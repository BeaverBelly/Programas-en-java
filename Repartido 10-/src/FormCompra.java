import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class FormCompra extends JFrame {
    private JPanel contentPane;
    private JComboBox comboBoxCategoria;
    private JSpinner spinnerCantidad;
    private JList listProductos;
    private JTable tblDetalleCompra;
    private JButton btnAgregar;
    private JButton btnGuardar;
    private JButton btnCargar;
    private JButton btnResumen;
    private JLabel lblValor;

    DefaultComboBoxModel<String> categoriasModel = new DefaultComboBoxModel<>();
    DefaultListModel<Producto> listaModel = new DefaultListModel<>();
    SpinnerNumberModel cantidadModel = new SpinnerNumberModel(1, 1, 100, 1);
    DefaultTableModel tablaModel = new DefaultTableModel();

    private final Map<String, List<Producto>> productosPorCategoria = new LinkedHashMap<>();

    private static class Producto {
        final String nombre; final double precio;
        Producto(String n, double p){ nombre=n; precio=p; }
        @Override public String toString() {
            return nombre + " — $" + String.format("%.2f", precio);
        }
    }

    public FormCompra() {
        // Guardar compra
        btnGuardar.addActionListener(e -> {
            List<DetalleCompra> lista = obtenerListaDesdeTabla();
            ArchivoCompras.guardar(lista);
        });

// Cargar compra
        btnCargar.addActionListener(e -> {
            List<DetalleCompra> lista = ArchivoCompras.leer();
            poblarTablaDesdeLista(lista);
        });

        btnResumen.addActionListener(e -> {
            List<DetalleCompra> lista = obtenerListaDesdeTabla(); // obtiene los productos cargados
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos para mostrar.");
                return;
            }
            new FormResumen(lista);
        });

        //---------------Modelo del ComboBox-------------------

        comboBoxCategoria.setModel(categoriasModel);

        categoriasModel.addElement("Bebidas");
        categoriasModel.addElement("Alimentos");
        categoriasModel.addElement("Limpieza");

        productosPorCategoria.put("Bebidas", Arrays.asList(
                new Producto("Agua", 40.0),
                new Producto("Jugo", 120.0),
                new Producto("Coca-Cola", 180.0)
        ));
        productosPorCategoria.put("Alimentos", Arrays.asList(
                new Producto("Pan", 50.0),
                new Producto("Leche", 70.0),
                new Producto("Queso", 150.0)
        ));
        productosPorCategoria.put("Limpieza", Arrays.asList(
                new Producto("Detergente", 85.0),
                new Producto("Lavandina", 70.0),
                new Producto("Jabón", 60.0)
        ));

        //---------------Modelo del ComboBox-------------------

        //---------------Modelo del JList -------------------
        listProductos.setModel(listaModel);


        comboBoxCategoria.addActionListener(e -> {
            String cat = (String) comboBoxCategoria.getSelectedItem();
            filtrarProductosPorCategoria(cat);
        });

        if (categoriasModel.getSize() > 0) {
            comboBoxCategoria.setSelectedIndex(0);
            filtrarProductosPorCategoria((String) categoriasModel.getElementAt(0));
        }

        listProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //---------------Modelo del JList -------------------

        //---------------Modelo del JSpinner -------------------

        spinnerCantidad.setModel(cantidadModel);
        //---------------Modelo del JSpinner -------------------

        //---------------Modelo del Table -------------------

        tblDetalleCompra.setFillsViewportHeight(true);
        tblDetalleCompra.setShowGrid(true);

        tablaModel.addColumn("Producto");
        tablaModel.addColumn("Categoria");
        tablaModel.addColumn("Precio");
        tablaModel.addColumn("Cantidad");
        tablaModel.addColumn("Subtotal");

        tblDetalleCompra.setModel(tablaModel);

        btnAgregar.addActionListener(e -> {
            Producto sel = (Producto) listProductos.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this,"Elegí un producto"); return; }

            int cantidad = (Integer) spinnerCantidad.getValue();

            if (cantidad < 1) { JOptionPane.showMessageDialog(this,"Cantidad inválida"); return; }

            String categoria = (String) comboBoxCategoria.getSelectedItem();

            String producto = sel.nombre;
            double precio   = sel.precio;
            double subtotal = precio * cantidad;

            tablaModel.addRow(new Object[]{
                    producto, categoria, precio, cantidad, subtotal
            });
            spinnerCantidad.setValue(1);
            recalcularTotal();
        });

        //---------------Modelo del Table -------------------

        setContentPane(contentPane);
        setBounds(0, 0, 500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void filtrarProductosPorCategoria(String categoria) {
        listaModel.clear();
        for (Producto p : productosPorCategoria.getOrDefault(categoria, Collections.emptyList())) {
            listaModel.addElement(p);
        }
        if (!listaModel.isEmpty()) listProductos.setSelectedIndex(0);
    }

    private Double precioDe(String nombreProducto, String categoria) {
        for (Producto p : productosPorCategoria.getOrDefault(categoria, Collections.emptyList())) {
            if (p.nombre.equals(nombreProducto)) return p.precio;
        }
        return null;
    }

    private void recalcularTotal() {
        double total = 0.0;
        for (int i = 0; i < tablaModel.getRowCount(); i++) {
            double precio = ((Number) tablaModel.getValueAt(i, 2)).doubleValue();
            int cant     = ((Number) tablaModel.getValueAt(i, 3)).intValue();
            double sub   = precio * cant;
            tablaModel.setValueAt(sub, i, 4);
            total += sub;
        }
        lblValor.setText(String.format("Total: $%.2f", total));
    }

    private List<DetalleCompra> obtenerListaDesdeTabla() {
        List<DetalleCompra> lista = new ArrayList<>();
        for (int i = 0; i < tblDetalleCompra.getRowCount(); i++) {
            String producto = tblDetalleCompra.getValueAt(i, 0).toString();
            double precio   = ((Number) tblDetalleCompra.getValueAt(i, 2)).doubleValue();
            int cantidad    = ((Number) tblDetalleCompra.getValueAt(i, 3)).intValue();
            lista.add(new DetalleCompra(producto, precio, cantidad));
        }
        return lista;
    }



    // Llena JTable con los datos leídos
private void poblarTablaDesdeLista(List<DetalleCompra> lista) {
    DefaultTableModel modelo = (DefaultTableModel) tblDetalleCompra.getModel();
    modelo.setRowCount(0); // limpiar
    for (DetalleCompra d : lista) {
        modelo.addRow(new Object[]{
            d.getProducto(),
            d.getPrecio(),
            d.getCantidad(),
            d.getSubtotal()
        });
    }
    recalcularTotal();
}


    public static void main(String[] args) {
        FormCompra formcompra = new FormCompra();
    }



}