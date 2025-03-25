import java.util.Scanner;

public class Ejercicio_7 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        System.out.println("Ingrese el nombre: ");
        String nombre = leer.nextLine();

        System.out.println("ingrese la edad: ");
        int edad = leer.nextInt();
        leer.nextLine();

        System.out.println("Ingrese la ciudad donde vive: ");
        String ciudad = leer.nextLine();

        System.out.println("Hola " + nombre + ", tenés " + edad + " años y vivis en " + (ciudad) + ".");
    }
}
