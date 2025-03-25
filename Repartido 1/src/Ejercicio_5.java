/*Pedir una temperatura en Celsius y mostrarla en Farenheit*/

import java.util.Scanner;

public class Ejercicio_5 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        System.out.println("Dame la temperatura en celsius y te la devolvere en Farenheit: ");
        double celsius = leer.nextDouble();

        System.out.println("La temperatura en Farenheit es: "+ ((celsius*9/5) + 35) + "C");
    }
}
