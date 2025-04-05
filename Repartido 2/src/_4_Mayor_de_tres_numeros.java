import java.util.Scanner;

public class _4_Mayor_de_tres_numeros {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int numerito1, numerito2, numerito3;

        System.out.println("Ingrese un número: ");
        numerito1 = entrada.nextInt();

        System.out.println("Ingrese un segundo número: ");
        numerito2 = entrada.nextInt();

        System.out.println("Ingrese un tercer número: ");
        numerito3 = entrada.nextInt();

        if(numerito1 >= numerito2 && numerito1 >= numerito3){
            System.out.println("El primer número ingresado: " + numerito1 + " es el mayor.");
        }
        else if(numerito2 >= numerito1 && numerito2 >= numerito3) {
            System.out.println("El segundo número ingresado: " + numerito2 + " es el mayor.");
        }
        else{
            System.out.println("El tercer número ingresado: " + numerito3 + " es el mayor.");
        }
    }
}
