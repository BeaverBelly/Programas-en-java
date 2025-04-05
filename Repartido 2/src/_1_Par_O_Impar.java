import java.util.Scanner;

public class _1_Par_O_Impar {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Digite um número: ");

        int numerito = entrada.nextInt();

        if(numerito % 2 == 0){
            System.out.println("El número: " + numerito + " es par.");
        }
        else{
            System.out.println("El número: " + numerito + " es impar");
        }

    }
}
