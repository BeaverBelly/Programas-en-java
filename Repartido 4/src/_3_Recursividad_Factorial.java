public class _3_Recursividad_Factorial {
    public static void main(String[] args){
     int numero = 5;
     System.out.println("Factorial de " + numero + " es " + factoriales(numero));
    }

    public static int factoriales(int numero){
        if(numero == 0){
            return 1;
        }
        else{
            return numero * factoriales(numero - 1);
        }
    }
}
