/*Pedir al usuario su nombre y mostrar un saludo personalizado*/
import java.util.Scanner;

public class Ejercicio_1 {
    public static void main(String[] args) {

        System.out.print("Ingrese el nombre: ");
        String nombre = new Scanner(System.in).nextLine();

        System.out.println("Bienvenido: "+ nombre);
    }
}