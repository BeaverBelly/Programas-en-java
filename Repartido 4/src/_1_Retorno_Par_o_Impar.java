import java.util.Scanner;

public class _1_Retorno_Par_o_Impar {
    public static void main(String[] args) {
        int num;
        Scanner entrada = new Scanner(System.in);

        System.out.print("Ingrese un número: ");

        boolean resultado = numparimpar(num = entrada.nextInt());

        System.out.println("¿Es par? " + resultado);

    }

    public static boolean numparimpar (int numero){
        return numero % 2 == 0;
    }

}
