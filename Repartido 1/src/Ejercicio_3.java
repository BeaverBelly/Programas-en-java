/*Pedir el año actual y el año de nacimiento del usuario, calcular y mostrar la edad.*/
import  java.util.Scanner;

public class Ejercicio_3 {
    public static void main(String[] args) {
        Scanner anio = new Scanner(System.in);

        System.out.println("Dame el año actual");
        short anio_actual = anio.nextShort();
        System.out.println("Dame tu año de nacimiento");
        short anio_nacimiento = anio.nextShort();

        System.out.println("Tu edad actual es: " + (anio_actual - anio_nacimiento));


    }
}
