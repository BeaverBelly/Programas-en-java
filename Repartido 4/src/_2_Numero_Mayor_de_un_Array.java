public class _2_Numero_Mayor_de_un_Array {
    public static void main(String[] args){
    int[] arraycito = {3,9,1,7,4};

    recibimientoArray(arraycito);

    }

    public static void recibimientoArray(int[] array){
        int num = 0;
        for(int i = 0; i<array.length; i++){
            if(num < array[i]) {
                num = array[i];
            }
        }
        System.out.println("El número mayor del array es el siguiente: " + num);
    }
}
