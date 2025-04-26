public class _1_Par_O_Impar_En_Array {
    public static void main(String[] args) {
        int[] array = {10, 4, 5, 7, 3, 5, 7, 9, 4, 2, 5, 7, -56};
        int contPar = 0, contImpar = 0;

        for(int i : array){
            if(i % 2 == 0){
                contPar++;
            }
            else{
                contImpar++;
            }
        }
        System.out.println("La cantidad de numeros pares son: " + contPar);
        System.out.println("La cantidad de numeros impares son: " + contImpar);
    }
}