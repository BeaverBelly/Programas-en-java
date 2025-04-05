import java.util.Scanner;

public class _3_Cifras {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese un número en el rango de 0 y 999: ");
        int numerito = entrada.nextInt();

        if(numerito >= 0 && numerito <= 9){
            System.out.println("El número: " + numerito + " tiene 1 cifra.");
        }
        else if(numerito >= 10 && numerito <= 99){
            System.out.println("El número: " + numerito + " tiene 2 cifras.");
        }
        else if(numerito >= 100 && numerito <= 999){
            System.out.println("El número: " + numerito + " tiene 3 cifras.");
        }
        else{
            System.out.println("El número que ingresaste no se encuentra dentro del rango solicitado.");
        }
    }
}
