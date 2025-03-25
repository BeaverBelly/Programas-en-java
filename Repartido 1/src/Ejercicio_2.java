/*Leer dos números enteros y mostrar su suma*/

import java.util.Scanner;

public class Ejercicio_2 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        System.out.print("Ingrese el primer número: ");
        int num1 = leer.nextInt();

        System.out.print("Ingrese el segundo número: ");
        int num2 = leer.nextInt();

        System.out.println("La suma de los números es:" + (num1 + num2));
    }
}
