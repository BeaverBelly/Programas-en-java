/*Leer base y altura de un rectangulo y mostrar el área*/
import java.util.Scanner;

public class Ejercicio_4 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        System.out.println("Ingrese la base del rectangulo: ");
        double base = leer.nextDouble();

        System.out.println("Ingrese la altura del rectangulo: ");
        double altura = leer.nextDouble();

        System.out.println("El área del rectangulo es: "+ (base*altura) +"cm2");

    }
}
