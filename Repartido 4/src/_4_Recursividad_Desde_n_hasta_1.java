public class _4_Recursividad_Desde_n_hasta_1 {
    public static void main(String[] args){
        int numero = 4;

        volviendoHaciaAtras(numero);

    }

    public static int volviendoHaciaAtras(int numero){
        if(numero == 0){
            return 0;
        }
        else{
            System.out.print(numero + " ");
            return volviendoHaciaAtras(numero - 1);
        }
    }
}
