package Ejercicio4;

public class Main {
    public static void main(String[] args) {
        CatalogoProductos catalogo = new CatalogoProductos();

        Producto p1 = new Producto("P001", "Laptop", 50200.50);
        Producto p2 = new Producto("P002", "Smartphone", 34000.00);
        Producto p3 = new Producto("P003", "Auriculares", 850.75);

        catalogo.agregarProducto(p1);
        catalogo.agregarProducto(p2);
        catalogo.agregarProducto(p3);

        catalogo.mostrarProductos();

        System.out.println("\nBuscando producto con ID 'P002':");
        Producto buscado = catalogo.obtenerProductoPorId("P002");
        if (buscado != null) {
            System.out.println("Encontrado: " + buscado);
        } else {
            System.out.println("No existe producto con ese ID.");
        }

        System.out.println("\nEliminando producto con ID 'P003'...");
        catalogo.eliminarProducto("P003");

        catalogo.mostrarProductos();
    }
}
