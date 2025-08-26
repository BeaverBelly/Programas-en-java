
package Ejercicio5;

import java.util.Scanner;

public class Main {
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.print("Ingrese el primer número: ");
        String entrada1 = scanner.nextLine();

        System.out.print("Ingrese el segundo número: ");
        String entrada2 = scanner.nextLine();

        int numero1 = Integer.parseInt(entrada1);
        int numero2 = Integer.parseInt(entrada2);


        int resultado = numero1 / numero2;
        System.out.println("Resultado de la división: " + resultado);

    } catch (NumberFormatException e) {
        System.out.println("Error: Debe ingresar números enteros válidos.");
    } catch (ArithmeticException e) {
        System.out.println("Error: No se puede dividir por cero.");
    } finally {
        System.out.println("Programa finalizado (bloque finally ejecutado).");
        scanner.close();
    }
}
}
