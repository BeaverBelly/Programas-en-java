package _1_Producto;

public class Main {
    public static void main(String[] args) {
        /*Acá se utiliza el constructor por defecto el cual no instancia ningún valor*/
        Producto producto1 = new Producto();

        /*Acá se utiliza el constructor que declara los parámetros de nombre y precio*/
        Producto producto2 = new Producto("Emanuel", 350.0);

        /*Acá se utiliza el constructor que declara los parámetros de nombre, precio y stock*/
        Producto producto3 = new Producto("Facundo", 240.0, 200);

        producto1.mostrarInfo();

        producto2.mostrarInfo();

        producto3.mostrarInfo();

    }
}
