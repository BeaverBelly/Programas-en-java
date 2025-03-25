/*Leer precios de tres productos, calcular total y aplicar el IVA 22%. Mostrar el total*/
import java.util.Scanner;

public class Ejercicio_6 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        double suma_precio = 0;
        final double IVA= 0.22;

        for (int i=0; i<3; i++){
            System.out.print("Ingrese el precio del producto: ");
                suma_precio += leer.nextDouble();
        }

        double subtotal = suma_precio * IVA;

        double total = suma_precio + subtotal;

        System.out.println(total);
    }
}
