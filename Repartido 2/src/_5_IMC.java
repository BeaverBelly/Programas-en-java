import java.util.Scanner;

public class _5_IMC {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        double IMC, kg, altura;

        System.out.println("Ingrese el peso en kilogramos: ");
        kg = entrada.nextInt();

        System.out.println("Ingrese la altura  en centimetros: ");
        altura = entrada.nextInt() / 100.0;

        IMC = kg / (altura * altura);

        if(IMC < 18.5){
            System.out.printf("El IMC es: %.2f. Usted tiene bajo peso.", IMC);
        }
        else if(IMC >= 18.5 && IMC < 25){
            System.out.printf("El IMC es: %.2f. Usted tiene un peso normal.", IMC);
        }
        else if(IMC >= 25 && IMC < 30){
            System.out.printf("El IMC es: %.2f. Usted tiene sobrepeso.", IMC);
        }
        else if(IMC >= 30 && IMC < 35){
            System.out.printf("El IMC es: %.2f. Usted tiene obesidad grado I.", IMC);
        }
        else if(IMC >= 35 && IMC < 40){
            System.out.printf("El IMC es: %.2f. Usted tiene obesidad grado II.", IMC);
        }
        else{
            System.out.printf("El IMC es: %.2f. Usted tiene obesidad grado III.", IMC);
        }
    }
}
