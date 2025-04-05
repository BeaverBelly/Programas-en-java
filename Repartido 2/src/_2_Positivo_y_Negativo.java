import java.util.Scanner;

public class _2_Positivo_y_Negativo {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Ingrese un número: ");
        int numerito = entrada.nextInt();

        if(numerito > 0){
            System.out.println("El número: "+ numerito +" es positivo.");
        }
        else if(numerito < 0){
            System.out.println("El número: " + numerito + " es negativo.");
        }
        else{
            System.out.println("El número: " + numerito + " es neutro.");
        }
    }
}
