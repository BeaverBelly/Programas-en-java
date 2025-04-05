import java.util.Scanner;

public class _8_Sumar_N_numeros_positivos_ingresados_por_el_usurio {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int solicitud = 0, resultado = 0, numero = 0, i=0;
        boolean banderita = true;

        while(banderita){
            System.out.println("Ingrese cuantos números desea sumar (positivos): ");
            solicitud = entrada.nextInt();
            if(solicitud>= 0){
                banderita = false;
            }
            else{
                System.out.println("Seleccione un número dentro del rango de los positivos");
            }
        }

        while(i<solicitud){
            System.out.print("Ingrese el número "+ (i+1) +": ");
            numero = entrada.nextInt();

            if(numero >= 0){
                resultado = numero + resultado;
            }
            else{
                System.out.println("ingrese un número positivo");
                i--;
            }
            i++;
        }
        System.out.println("El resultado de la suma es:" + resultado);
    }
}
