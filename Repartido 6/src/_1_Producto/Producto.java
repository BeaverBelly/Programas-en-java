package _1_Producto;

public class Producto {
    private String nombre;
    private double precio;
    private int stock;

    public Producto(){
        nombre = "sin nombre";
        precio = 0.0;
        stock = 0;
    }

    public Producto(String nombre, double precio){
        this.nombre = nombre;
        this.precio = precio;
        stock = 0;
    }

    public Producto(String nombre, double precio, int stock){
        this.nombre = nombre;
        this.precio = precio;
        stock = 0;
    }

    public void mostrarInfo(){
        System.out.println(nombre + ", " + precio + ", " + stock);
    }

}
