import java.util.Scanner;

public class _9_Validar_ingreso_de_contrasenia {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String contrasenia = "java123", regcontrasenia;

        do{
            System.out.print("Ingrese la contraseña: ");
            regcontrasenia = entrada.nextLine();

            if(contrasenia.equals(regcontrasenia)){
                System.out.println("¡Acceso correcto!");
            }
            else{
                System.out.println("Acceso denegado");
            }

        }while(!(regcontrasenia.equals(contrasenia)));
    }
}
