public class _2_Intercambio_De_Elementos_En_Array {
    public static void main(String[] args){
        int[] vec1={2, 6, 4, 5, 8, 9, 6, 4};
        int[] vec2={10, 2, 5, 10, 4, 7, 5, 6};
        int aux;

        aux = vec1[1];
        vec1[1] = vec2[1];
        vec2[1] = aux;

        aux = vec1[3];
        vec1[3] = vec2[3];
        vec2[3] = aux;

        aux = vec1[5];
        vec1[5] = vec2[5];
        vec2[5] = aux;

        aux = vec1[7];
        vec1[7] = vec2[7];
        vec2[7] = aux;

        for (int Vec1 : vec1){
            System.out.print(Vec1 + ", " );
        }

        System.out.println();

        for (int Vec2 : vec2){
            System.out.print(Vec2 + ", ");
        }
    }
}
