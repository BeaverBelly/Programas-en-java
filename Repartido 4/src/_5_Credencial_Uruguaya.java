import java.util.Scanner;

public class _5_Credencial_Uruguaya {
    public static void main(String[] args) {
        String Credencial;
        boolean resultado;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese una cedula que contenga 8 digitos: ");
        Credencial = entrada.nextLine();

        System.out.println("¿Es valida? " + (resultado = validarCedula(Credencial)));
    }

    public static boolean validarCedula(String cedula) {
        int num1, num2, num3, num4, num5, num6, num7, subresultado, resto, resultado;

        num1 = 2 * Character.getNumericValue(cedula.charAt(0));
        num2 = 9 * Character.getNumericValue(cedula.charAt(1));
        num3 = 8 * Character.getNumericValue(cedula.charAt(2));
        num4 = 7 * Character.getNumericValue(cedula.charAt(3));
        num5 = 6 * Character.getNumericValue(cedula.charAt(4));
        num6 = 3 * Character.getNumericValue(cedula.charAt(5));
        num7 = 4 * Character.getNumericValue(cedula.charAt(6));

        subresultado = num1 + num2 + num3 + num4 + num5 + num6 + num7;

        resto = subresultado % 10;

        resultado = 10 - resto;

        return resultado == Character.getNumericValue(cedula.charAt(7));
    }
}
