import java.util.Scanner;
public class _3_Verificando_Nombres_En_Array {
    public static void main(String[] args){
        Scanner entrada = new Scanner(System.in);
        String nomPersona;
        boolean encontrado = false;
        String[] Nombre = {"Ana", "Luis", "Marta", "Pedro", "Juan"};

        System.out.println("Ingrese un nombre por favor: ");
        nomPersona = entrada.next();

        for (String nombre : Nombre){
            if(nombre.equals(nomPersona)){
                encontrado = true;
                break;
            }
        }

        if(encontrado){
            System.out.println("El nombre " + nomPersona + " se encuentra en el arreglo." );
        }
        else{
            System.out.println("El nombre " + nomPersona + " no se encuentra en el arreglo." );
        }

        entrada.close();
    }
}
